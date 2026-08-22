package uy.edualex.hardcoresounds.service;

import java.time.Clock;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class CooldownService {
    private final Clock clock;
    private final Map<UUID, Long> senderUses = new HashMap<>();
    private long globalUse = Long.MIN_VALUE;

    public CooldownService() {
        this(Clock.systemUTC());
    }

    CooldownService(Clock clock) {
        this.clock = clock;
    }

    public synchronized long tryAcquire(UUID sender, boolean enabled, long senderMillis, long globalMillis) {
        if (!enabled) return 0;
        long now = clock.millis();
        long senderRemaining = remaining(now, senderUses.getOrDefault(sender, Long.MIN_VALUE), senderMillis);
        long globalRemaining = remaining(now, globalUse, globalMillis);
        long remaining = Math.max(senderRemaining, globalRemaining);
        if (remaining > 0) return remaining;
        senderUses.put(sender, now);
        globalUse = now;
        return 0;
    }

    private static long remaining(long now, long previous, long duration) {
        if (previous == Long.MIN_VALUE || duration <= 0) return 0;
        return Math.max(0, duration - (now - previous));
    }

    public synchronized void remove(UUID sender) {
        senderUses.remove(sender);
    }

    public synchronized void clear() {
        senderUses.clear();
        globalUse = Long.MIN_VALUE;
    }
}
