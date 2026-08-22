package uy.edualex.hardcoresounds.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class CooldownServiceTest {
    @Test
    void appliesSenderAndGlobalCooldownsWithoutConsumingRejectedAttempt() {
        MutableTimeSource clock = new MutableTimeSource(1_000);
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
        CooldownService service = new CooldownService(new MutableTimeSource(0));
        UUID sender = UUID.randomUUID();
        assertEquals(0, service.tryAcquire(sender, false, 500, 250));
        assertEquals(0, service.tryAcquire(sender, false, 500, 250));
    }

    @Test
    void monotonicTimePreventsWallClockChangesFromExtendingCooldowns() {
        MutableTimeSource timeSource = new MutableTimeSource(1_000);
        CooldownService service = new CooldownService(timeSource);
        UUID sender = UUID.randomUUID();

        assertEquals(0, service.tryAcquire(sender, true, 500, 0));
        timeSource.millis = 1_500;
        assertEquals(0, service.tryAcquire(sender, true, 500, 0));
    }

    private static final class MutableTimeSource implements java.util.function.LongSupplier {
        private long millis;
        private MutableTimeSource(long millis) { this.millis = millis; }
        @Override public long getAsLong() { return millis; }
    }
}
