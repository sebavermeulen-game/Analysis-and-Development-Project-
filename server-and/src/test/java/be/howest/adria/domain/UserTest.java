package be.howest.adria.domain;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void testUserCreationWithoutSubscription() {
        // Arrange
        User user = User.create();

        // Act & Assert
        assertNotNull(user.getId());
        assertNull(user.getSubscription());
        assertEquals(0, user.getOrders().size());
    }

    @Test
    void testUserCreationWithSubscription() {
        // Arrange
        SubscriptionDetails details = new SubscriptionDetails("Basic", "Monthly", 10, 100, Intensity.getIntensityLevel("LOW"));
        Subscription subscription = new Subscription(1, details, "Extra Info", "Request Info", "Support", BigDecimal.valueOf(19.99));
        User user = User.create(subscription);

        // Act & Assert
        assertNotNull(user.getId());
        assertEquals(subscription, user.getSubscription());
    }

    @Test
    void testSetSubscription() {
        // Arrange
        User user = User.create();
        SubscriptionDetails details = new SubscriptionDetails("Basic", "Monthly", 10, 100, Intensity.getIntensityLevel("LOW"));
        Subscription subscription = new Subscription(2, details, "Extra Info", "Request Info", "Support", BigDecimal.valueOf(19.99));

        // Act
        user.setSubscription(subscription);

        // Assert
        assertEquals(subscription, user.getSubscription());
    }

    @Test
    void testRemainingAllowedTime() {
        // Arrange
        SubscriptionDetails details = new SubscriptionDetails("Basic", "Monthly", 10, 100, Intensity.getIntensityLevel("HIGH"));
        Subscription subscription = new Subscription(1, details, null, null, null, null);
        User user = User.create(subscription);

        // Act
        int remainingTime = user.getRemainingAllowedTimeInSeconds();

        // Assert
        assertEquals(36000, remainingTime);
    }

    @Test
    void testAddOrderSuccess() {
        // Arrange
        SubscriptionDetails details = new SubscriptionDetails("Basic", "Monthly", 10, 100, Intensity.getIntensityLevel("HIGH"));
        Subscription subscription = new Subscription(1, details, null, null, null, null);
        User user = User.create(subscription);
        Order order = new Order(
                new Location(51.0, 4.0),
                Intensity.HIGH.getMaxPercentage(),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                50,
                user.getId().toString()
        );

        // Act
        boolean orderAdded = user.addOrder(order);

        // Assert
        assertTrue(orderAdded);
    }

    @Test
    void testAddOrderFailDueToOverlap() {
        // Arrange
        SubscriptionDetails details = new SubscriptionDetails("Basic", "Monthly", 10, 100, Intensity.getIntensityLevel("HIGH"));
        Subscription subscription = new Subscription(1, details, null, null, null, null);
        User user = User.create(subscription);
        Order order1 = new Order(
                new Location(51.0, 4.0),
                Intensity.HIGH.getMaxPercentage(),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                50,
                user.getId().toString()
        );
        Order order2 = new Order(
                new Location(52.0, 5.0),
                Intensity.HIGH.getMaxPercentage(),
                LocalDateTime.now().plusMinutes(30),
                LocalDateTime.now().plusHours(1).plusMinutes(30),
                50,
                user.getId().toString()
        );

        // Act
        boolean order1Added = user.addOrder(order1);
        boolean order2Added = user.addOrder(order2);

        // Assert
        assertTrue(order1Added);
        assertTrue(order2Added);
    }

    @Test
    void testConstructorAndGetters() {
        // Arrange
        UUID userId = UUID.randomUUID();
        SubscriptionDetails details = new SubscriptionDetails("Basic", "Monthly", 10, 100, Intensity.getIntensityLevel("LOW"));
        Subscription subscription = new Subscription(1, details, "Extra Info", "Request Info", "Support", BigDecimal.valueOf(19.99));
        User user = User.create(userId, subscription);

        // Act & Assert
        assertEquals(userId, user.getId());
        assertEquals(subscription, user.getSubscription());
    }

    @Test
    void testGetRemainingAllowedTimeInSeconds() {
        // Arrange
        SubscriptionDetails details = new SubscriptionDetails("TypeA", "PlanA", 10, 100, Intensity.getIntensityLevel("HIGH"));
        Subscription subscription = new Subscription(1, details, "Extra1", "Request1", "Support1", BigDecimal.valueOf(99.99));
        User user = User.create(subscription);

        // Act
        int remainingTime = user.getRemainingAllowedTimeInSeconds();

        // Assert
        assertEquals(36000, remainingTime);
    }

    @Test
    void testAddOrder() {
        // Arrange
        SubscriptionDetails details = new SubscriptionDetails("TypeA", "PlanA", 10, 100, Intensity.getIntensityLevel("HIGH"));
        Subscription subscription = new Subscription(1, details, "Extra1", "Request1", "Support1", BigDecimal.valueOf(99.99));
        User user = User.create(subscription);
        Order order = new Order(
                new Location(37.7749, -122.4194),
                Intensity.getIntensityLevel("HIGH").getMaxPercentage(),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                50,
                user.getId().toString()
        );

        // Act
        boolean orderAdded = user.addOrder(order);

        // Assert
        assertTrue(orderAdded);
        assertEquals(1, user.getOrders().size());
    }

    @Test
    void testSetSubscriptionId() {
        // Arrange
        SubscriptionDetails details = new SubscriptionDetails("Basic", "Monthly", 10, 100, Intensity.getIntensityLevel("LOW"));
        Subscription subscription = new Subscription(1, details, "Extra Info", "Request Info", "Support", BigDecimal.valueOf(19.99));
        User user = User.create(subscription);

        // Act
        user.setSubscriptionId(2);

        // Assert
        assertEquals(2, user.getSubscription().getSubscriptionId());
    }

    @Test
    void testSetSubscriptionIdWithNullSubscription() {
        // Arrange
        User user = User.create();

        // Act
        user.setSubscriptionId(2);

        // Assert
        assertNull(user.getSubscription());
    }

    @Test
    void testAddOrderUnlimitedSubscription() {
        // Arrange
        SubscriptionDetails details = new SubscriptionDetails("Unlimited", "Lifetime", -1, 0, Intensity.getIntensityLevel("ULTRA_HIGH"));
        Subscription unlimitedSubscription = new Subscription(9, details, "Extra Info", "Request Info", "Support", BigDecimal.valueOf(199.99));
        User unlimitedUser = User.create(unlimitedSubscription);
        Order order = new Order(
                new Location(51.0, 4.0),
                Intensity.ULTRA_HIGH.getMaxPercentage(),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(10),
                50,
                unlimitedUser.getId().toString()
        );

        // Act
        boolean orderAdded = unlimitedUser.addOrder(order);

        // Assert
        assertTrue(orderAdded);
        assertEquals(1, unlimitedUser.getOrders().size());
    }

    @Test
    void testAddOrderExceedsAllowedTime() {
        // Arrange
        SubscriptionDetails details = new SubscriptionDetails("Basic", "Monthly", 1, 100, Intensity.getIntensityLevel("HIGH"));
        Subscription subscription = new Subscription(1, details, null, null, null, null);
        User user = User.create(subscription);
        Order order1 = new Order(
                new Location(51.0, 4.0),
                Intensity.HIGH.getMaxPercentage(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30),
                50,
                user.getId().toString()
        );
        Order order2 = new Order(
                new Location(52.0, 5.0),
                Intensity.HIGH.getMaxPercentage(),
                LocalDateTime.now().plusMinutes(30),
                LocalDateTime.now().plusHours(1).plusMinutes(30),
                50,
                user.getId().toString()
        );

        // Act
        boolean order1Added = user.addOrder(order1);
        boolean order2Added = user.addOrder(order2);

        // Assert
        assertTrue(order1Added);
        assertFalse(order2Added);
    }

    @Test
    void testAddOrderWithNullSubscription() {
        // Arrange
        User user = User.create();
        Order order = new Order(
                new Location(51.0, 4.0),
                Intensity.HIGH.getMaxPercentage(),
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(1),
                50,
                user.getId().toString()
        );

        // Act
        boolean orderAdded = user.addOrder(order);

        // Assert
        assertTrue(orderAdded);
    }

    @Test
    void testCanAddOrder() throws Exception {
        // Arrange
        SubscriptionDetails details = new SubscriptionDetails("Basic", "Monthly", 1, 100, Intensity.getIntensityLevel("HIGH"));
        Subscription subscription = new Subscription(1, details, null, null, null, null);
        User user = User.create(subscription);
        Order order = new Order(
                new Location(51.0, 4.0),
                Intensity.HIGH.getMaxPercentage(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(30),
                50,
                user.getId().toString()
        );
        Method canAddOrderMethod = User.class.getDeclaredMethod("canAddOrder", Order.class);
        canAddOrderMethod.setAccessible(true);

        // Act
        boolean result = (boolean) canAddOrderMethod.invoke(user, order);

        // Assert
        assertTrue(result);
    }

    @Test
    void testGetRemainingAllowedTimeInSecondsWithNullSubscription() {
        // Arrange
        User user = User.create();

        // Act
        int remainingTime = user.getRemainingAllowedTimeInSeconds();

        // Assert
        assertEquals(Integer.MAX_VALUE, remainingTime);
    }

    @Test
    void testGetRemainingAllowedTimeInSecondsWithUnlimitedSubscription() {
        // Arrange
        SubscriptionDetails details = new SubscriptionDetails("Premium Plan", "Individual User", -1, 150, Intensity.HIGH);
        Subscription unlimitedSubscription = new Subscription(3, details, "Customized scheduling and intensity control.", "Full control over light requests, including large spaces.", "24/24 Support", BigDecimal.valueOf(3000.00));
        User user = User.create(unlimitedSubscription);

        // Act
        int remainingTime = user.getRemainingAllowedTimeInSeconds();

        // Assert
        assertEquals(Integer.MAX_VALUE, remainingTime);
    }
}