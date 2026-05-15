package be.howest.adria.domain;

import be.howest.adria.infrastructure.shared.utils.IntensityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IntensityTest {

    private Subscription subscriptionLow;
    private Subscription subscriptionBasic;

    @BeforeEach
    void setUp() {
        SubscriptionDetails details = new SubscriptionDetails("Basic", "Monthly", 10, 100, Intensity.getIntensityLevel("LOW"));
        subscriptionLow = new Subscription(
                1, details, "Extra Info", "Request Info", "Support", BigDecimal.valueOf(19.99)
        );
        subscriptionBasic = new Subscription(
                1, details, "Extra Info", "Request Info", "Support", BigDecimal.valueOf(19.99)
        );
    }

    @Test
    void testGetMaxPercentageLow() {
        Intensity intensity = Intensity.LOW;
        int maxPercentage = intensity.getMaxPercentage();
        assertEquals(40, maxPercentage);
    }

    @Test
    void testGetMaxPercentageMedium() {
        Intensity intensity = Intensity.MEDIUM;
        int maxPercentage = intensity.getMaxPercentage();
        assertEquals(60, maxPercentage);
    }

    @Test
    void testGetMaxPercentageHigh() {
        Intensity intensity = Intensity.HIGH;
        int maxPercentage = intensity.getMaxPercentage();
        assertEquals(80, maxPercentage);
    }

    @Test
    void testGetMaxPercentageUltraHigh() {
        Intensity intensity = Intensity.ULTRA_HIGH;
        int maxPercentage = intensity.getMaxPercentage();
        assertEquals(100, maxPercentage);
    }

    @Test
    void testIsValidIntensityValid() {
        int intensity = 50;
        boolean isValid = Intensity.isValidIntensity(intensity);
        assertTrue(isValid);
    }

    @Test
    void testIsValidIntensityInvalid() {
        int intensity = 101;
        boolean isValid = Intensity.isValidIntensity(intensity);
        assertFalse(isValid);
    }

    @Test
    void testGetIntensityLevelLow() {
        String intensity = "LOW";
        Intensity level = Intensity.getIntensityLevel(intensity);
        assertEquals(Intensity.LOW, level);
    }

    @Test
    void testGetIntensityLevelMedium() {
        String intensity = "MEDIUM";
        Intensity level = Intensity.getIntensityLevel(intensity);
        assertEquals(Intensity.MEDIUM, level);
    }

    @Test
    void testGetIntensityLevelHigh() {
        String intensity = "HIGH";
        Intensity level = Intensity.getIntensityLevel(intensity);
        assertEquals(Intensity.HIGH, level);
    }

    @Test
    void testGetIntensityLevelUltraHigh() {
        String intensity = "ULTRA_HIGH";
        Intensity level = Intensity.getIntensityLevel(intensity);
        assertEquals(Intensity.ULTRA_HIGH, level);
    }

    @Test
    void testGetIntensityLevelInvalid() {
        String intensity = "INVALID";
        Exception exception = assertThrows(IntensityException.class, () -> Intensity.getIntensityLevel(intensity));
        assertEquals("Invalid subscription level: INVALID", exception.getMessage());
    }

    @Test
    void testIsWithinMaxIntensityTrue() {
        int intensity = 30;
        Intensity level = Intensity.LOW;
        boolean isWithin = Intensity.isWithinMaxIntensity(intensity, level);
        assertTrue(isWithin);
    }

    @Test
    void testIsWithinMaxIntensityFalse() {
        int intensity = 50;
        Intensity level = Intensity.LOW;
        boolean isWithin = Intensity.isWithinMaxIntensity(intensity, level);
        assertFalse(isWithin);
    }

    @Test
    void testIsWithinMaxIntensityForSubscriptionTrue() {
        int intensity = 30;
        boolean isWithin = Intensity.isWithinMaxIntensityForSubscription(intensity, subscriptionLow);
        assertTrue(isWithin);
    }

    @Test
    void testIsWithinMaxIntensityForSubscriptionFalse() {
        int intensity = 50;
        boolean isWithin = Intensity.isWithinMaxIntensityForSubscription(intensity, subscriptionBasic);
        assertFalse(isWithin);
    }
}