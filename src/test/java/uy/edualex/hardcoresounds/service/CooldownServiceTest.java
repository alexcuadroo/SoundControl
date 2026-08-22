package uy.edualex.hardcoresounds.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CooldownServiceTest {
    @Test
    void appliesSenderAndGlobalCooldownsWithoutConsumingRejectedAttempt() {
        MutableClock clock = new MutableClock(1_000);
        CooldownService service = new CooldownService(clock);
        UUID first = UUID.randomUUID();
        UUID second = UUID.randomUUID();
        assertEquals(0, service.tryAcquire(first, true, 500, 250));
        assertEquals(250, service.tryAcquire(second, true, 500, 250));
        clock.millis = 1_250;
        assertEquals(0, service.tryAcquire(second, true, 500, 250));
        assertEquals(250, service.tryAcquire(first, true, 500, 250));
    }

    @Test
    void disabledLimiterAlwaysAllows() {
        CooldownService service = new CooldownService(new MutableClock(0));
        UUID sender = UUID.randomUUID();
        assertEquals(0, service.tryAcquire(sender, false, 500, 250));
        assertEquals(0, service.tryAcquire(sender, false, 500, 250));
    }

    private static final class MutableClock extends Clock {
        private long millis;
        private MutableClock(long millis) { this.millis = millis; }
        @Override public ZoneId getZone() { return ZoneId.of("UTC"); }
        @Override public Clock withZone(ZoneId zone) { return this; }
        @Override public Instant instant() { return Instant.ofEpochMilli(millis); }
        @Override public long millis() { return millis; }
    }
}
