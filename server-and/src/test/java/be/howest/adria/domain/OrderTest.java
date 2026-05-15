package be.howest.adria.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    private Location location;
    private int intensity;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        location = new Location(51.0, 4.0);
        intensity = Intensity.HIGH.getMaxPercentage();
        now = LocalDateTime.now();
    }

    @Test
    void testOrderCreation() {
        // Arrange
        Order order = new Order(location, intensity, now, now.plusHours(1), 50, "user123");

        // Act & Assert
        assertNotNull(order.getOrderId());
        assertEquals(location, order.getLocation());
        assertEquals(intensity, order.getIntensity());
        assertEquals(50, order.getRange());
        assertEquals("user123", order.getUserId());
    }

    @Test
    void testOrderDuration() {
        // Arrange
        Order order = new Order(location, intensity, now, now.plusHours(1), 50, "yaya19");

        // Act & Assert
        assertEquals(3600, order.getDurationSeconds());
    }

    @Test
    void testOrderOverlap() {
        // Arrange
        Order order1 = new Order(location, intensity, now, now.plusHours(1), 50, "yaya19");
        Order order2 = new Order(location, intensity, now.plusMinutes(30), now.plusHours(1).plusMinutes(30), 50, "yayab19");

        // Act & Assert
        assertTrue(order1.overlapsWith(order2));
    }

    @Test
    void testOrderNoOverlap() {
        // Arrange
        Order order1 = new Order(location, intensity, now, now.plusHours(1), 50, "yaya19");
        Order order2 = new Order(location, intensity, now.plusHours(1).plusMinutes(1), now.plusHours(2), 50, "yaya19");

        // Act & Assert
        assertFalse(order1.overlapsWith(order2));
    }

    @Test
    void testOrderOverlapSameStartEnd() {
        // Arrange
        Order order1 = new Order(location, intensity, now, now.plusHours(1), 50, "yaya19");
        Order order2 = new Order(location, intensity, now, now.plusHours(2), 50, "yayab19");

        // Act & Assert
        assertTrue(order1.overlapsWith(order2));
    }

    @Test
    void testOrderOverlapStartEqualsEnd() {
        // Arrange
        Order order1 = new Order(location, intensity, now, now.plusHours(1), 50, "yaya19");
        Order order2 = new Order(location, intensity, now.plusHours(1), now.plusHours(2), 50, "yayab19");

        // Act & Assert
        assertFalse(order1.overlapsWith(order2));
    }

    @Test
    void testOrderOverlapContainedOrder() {
        // Arrange
        Order order1 = new Order(location, intensity, now, now.plusHours(2), 50, "yaya19");
        Order order2 = new Order(location, intensity, now.plusMinutes(30), now.plusMinutes(90), 50, "yayab19");

        // Act & Assert
        assertTrue(order1.overlapsWith(order2));
    }

    @Test
    void testOrderOverlapExactMatch() {
        // Arrange
        Order order1 = new Order(location, intensity, now, now.plusHours(1), 50, "yaya19");
        Order order2 = new Order(location, intensity, now, now.plusHours(1), 50, "yayab19");

        // Act & Assert
        assertTrue(order1.overlapsWith(order2));
    }

    @Test
    void testGetOrderId() {
        // Arrange
        Order order = new Order(location, intensity, now, now.plusHours(1), 50, "user123");

        // Act & Assert
        assertNotNull(order.getOrderId());
    }

    @Test
    void testGetLocation() {
        // Arrange
        Order order = new Order(location, intensity, now, now.plusHours(1), 50, "user123");

        // Act & Assert
        assertEquals(location, order.getLocation());
    }

    @Test
    void testGetIntensity() {
        // Arrange
        Order order = new Order(location, intensity, now, now.plusHours(1), 50, "user123");

        // Act & Assert
        assertEquals(intensity, order.getIntensity());
    }

    @Test
    void testGetStartTime() {
        // Arrange
        Order order = new Order(location, intensity, now, now.plusHours(1), 50, "user123");

        // Act & Assert
        assertEquals(now, order.getStartTime());
    }

    @Test
    void testGetEndTime() {
        // Arrange
        Order order = new Order(location, intensity, now, now.plusHours(1), 50, "user123");

        // Act & Assert
        assertEquals(now.plusHours(1), order.getEndTime());
    }

    @Test
    void testGetRange() {
        // Arrange
        Order order = new Order(location, intensity, now, now.plusHours(1), 50, "user123");

        // Act & Assert
        assertEquals(50, order.getRange());
    }

    @Test
    void testGetUserId() {
        // Arrange
        Order order = new Order(location, intensity, now, now.plusHours(1), 50, "user123");

        // Act & Assert
        assertEquals("user123", order.getUserId());
    }

    @Test
    void testOrderOverlapFirstTrueSecondFalse() {
        // Arrange
        Order order1 = new Order(location, intensity, now, now.plusHours(1), 50, "yaya19");
        Order order2 = new Order(location, intensity, now.plusHours(1).plusMinutes(1), now.plusHours(2), 50, "yayab19");

        // Act & Assert
        assertFalse(order1.overlapsWith(order2));
    }

    @Test
    void testOrderOverlapFirstFalseSecondTrue() {
        // Arrange
        Order order1 = new Order(location, intensity, now.plusHours(1), now.plusHours(2), 50, "yaya19");
        Order order2 = new Order(location, intensity, now, now.plusHours(1).minusMinutes(1), 50, "yayab19");

        // Act & Assert
        assertFalse(order1.overlapsWith(order2));
    }

    @Test
    void testOrderOverlapBothTrue() {
        // Arrange
        Order order1 = new Order(location, intensity, now, now.plusHours(2), 50, "yaya19");
        Order order2 = new Order(location, intensity, now.plusMinutes(30), now.plusHours(1), 50, "yayab19");

        // Act & Assert
        assertTrue(order1.overlapsWith(order2));
    }

    @Test
    void testOrderOverlapBothFalse() {
        // Arrange
        Order order1 = new Order(location, intensity, now, now.plusHours(1), 50, "yaya19");
        Order order2 = new Order(location, intensity, now.plusHours(2), now.plusHours(3), 50, "yayab19");

        // Act & Assert
        assertFalse(order1.overlapsWith(order2));
    }
}