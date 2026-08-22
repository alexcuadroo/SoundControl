package uy.edualex.hardcoresounds.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ValidationTest {
    @Test
    void validatesSha1() {
        assertTrue(Validation.isSha1("0123456789abcdef0123456789ABCDEF01234567"));
        assertFalse(Validation.isSha1(""));
        assertFalse(Validation.isSha1("z123456789abcdef0123456789abcdef01234567"));
    }

    @Test
    void normalizesSafeIds() {
        assertEquals("muerte_2", Validation.normalizeId("MUERTE_2").orElseThrow());
        assertTrue(Validation.normalizeId("mal id").isEmpty());
    }

    @Test
    void enforcesSoundRanges() {
        assertTrue(Validation.validVolume(0));
        assertFalse(Validation.validVolume(-0.01));
        assertFalse(Validation.validVolume(Double.NaN));
        assertTrue(Validation.validPitch(0.5));
        assertTrue(Validation.validPitch(2.0));
        assertFalse(Validation.validPitch(2.01));
    }
}
