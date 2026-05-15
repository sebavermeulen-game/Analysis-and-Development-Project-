package be.howest.adria.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubscriptionTest {
    @Test
    void testSubscriptionCreation() {
        SubscriptionDetails details = new SubscriptionDetails("Basic", "Monthly", 10, 100, Intensity.getIntensityLevel("High"));
        Subscription subscription = new Subscription(
                1, details, "Extra Info", "Request Info", "Support", BigDecimal.valueOf(19.99)
        );

        assertEquals(1, subscription.getSubscriptionId());
        assertEquals("Basic", subscription.getSubscriptionType());
        assertEquals(10, subscription.getHours());
    }

    @Test
    void testSetSubscriptionId() {
        Subscription subscription = new Subscription(1);
        subscription.setSubscriptionId(2);
        assertEquals(2, subscription.getSubscriptionId());
    }

    @Test
    void testGetSubscriptionPlan() {
        SubscriptionDetails details = new SubscriptionDetails("Basic", "Monthly", 10, 100, Intensity.getIntensityLevel("High"));
        Subscription subscription = new Subscription(1, details, "Extra Info", "Request Info", "Support", BigDecimal.valueOf(19.99));
        assertEquals("Monthly", subscription.getSubscriptionPlan());
    }

    @Test
    void testGetRange() {
        SubscriptionDetails details = new SubscriptionDetails("Basic", "Monthly", 10, 100, Intensity.getIntensityLevel("High"));
        Subscription subscription = new Subscription(1, details, "Extra Info", "Request Info", "Support", BigDecimal.valueOf(19.99));
        assertEquals(100, subscription.getRange());
    }

    @Test
    void testGetIntensity() {
        SubscriptionDetails details = new SubscriptionDetails("Basic", "Monthly", 10, 100, Intensity.getIntensityLevel("High"));
        Subscription subscription = new Subscription(1, details, "Extra Info", "Request Info", "Support", BigDecimal.valueOf(19.99));
        assertEquals(Intensity.getIntensityLevel("High"), subscription.getIntensity());
    }

    @Test
    void testGetExtraInformation() {
        SubscriptionDetails details = new SubscriptionDetails("Basic", "Monthly", 10, 100, Intensity.getIntensityLevel("High"));
        Subscription subscription = new Subscription(1, details, "Extra Info", "Request Info", "Support", BigDecimal.valueOf(19.99));
        assertEquals("Extra Info", subscription.getExtraInformation());
    }

    @Test
    void testGetRequestInformation() {
        SubscriptionDetails details = new SubscriptionDetails("Basic", "Monthly", 10, 100, Intensity.getIntensityLevel("High"));
        Subscription subscription = new Subscription(1, details, "Extra Info", "Request Info", "Support", BigDecimal.valueOf(19.99));
        assertEquals("Request Info", subscription.getRequestInformation());
    }

    @Test
    void testGetSubscriptionSupport() {
        SubscriptionDetails details = new SubscriptionDetails("Basic", "Monthly", 10, 100, Intensity.getIntensityLevel("High"));
        Subscription subscription = new Subscription(1, details, "Extra Info", "Request Info", "Support", BigDecimal.valueOf(19.99));
        assertEquals("Support", subscription.getSubscriptionSupport());
    }

    @Test
    void testGetPrice() {
        SubscriptionDetails details = new SubscriptionDetails("Basic", "Monthly", 10, 100, Intensity.getIntensityLevel("High"));
        Subscription subscription = new Subscription(1, details, "Extra Info", "Request Info", "Support", BigDecimal.valueOf(19.99));
        assertEquals(BigDecimal.valueOf(19.99), subscription.getPrice());
    }
}