package be.howest.adria.application.taskly;

import be.howest.adria.infrastructure.repositories.MockOrderRepository;
import be.howest.adria.application.contracts.usecases.MockOutputPort;
import be.howest.adria.domain.Intensity;
import be.howest.adria.domain.Subscription;
import be.howest.adria.domain.SubscriptionDetails;
import be.howest.adria.infrastructure.shared.utils.IntensityException;
import be.howest.adria.infrastructure.shared.utils.OrderCreationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CreateOrderTest {

    private MockOrderRepository mockOrderRepository;
    private MockOutputPort<CreateOrderOutput> outputPort;
    private CreateOrder createOrder;

    @BeforeEach
    void setup() {
        mockOrderRepository = new MockOrderRepository();
        outputPort = new MockOutputPort<>();
        createOrder = new CreateOrder(mockOrderRepository, outputPort);
    }

    @Test
    void testExecuteWithoutSubscription() {
        UUID userId = UUID.randomUUID();
        CreateOrderInput input = new CreateOrderInput(
                userId,
                37.7749,
                -122.4194,
                10,
                LocalDateTime.now(),
                20,
                3600
        );

        Exception exception = assertThrows(OrderCreationException.class, () -> createOrder.execute(input));
        assertEquals("User " + userId + " does not have a valid subscription", exception.getMessage());
    }

    @Test
    void testExecuteIntensityExceedsSubscriptionLevel() {
        UUID userId = UUID.randomUUID();
        SubscriptionDetails details = new SubscriptionDetails("Individual User", "Standard Plan", 100, 100, Intensity.MEDIUM);
        Subscription subscription = new Subscription(2, details, "Adjustable intensity.", "Accept/Refuse light requests sent by others.", "Premium Support", BigDecimal.valueOf(1500.00));
        mockOrderRepository.addUserSubscription(userId, subscription);

        CreateOrderInput input = new CreateOrderInput(
                userId,
                37.7749,
                -122.4194,
                99,
                LocalDateTime.now(),
                50,
                3600
        );

        Exception exception = assertThrows(IntensityException.class, () -> createOrder.execute(input));
        assertEquals("Intensity value 99 exceeds the maximum of 60", exception.getMessage());
    }

    @Test
    void testExecuteFailedToSaveOrder() {
        UUID userId = UUID.randomUUID();
        SubscriptionDetails details = new SubscriptionDetails("Individual User", "Standard Plan", 100, 100, Intensity.MEDIUM);
        Subscription subscription = new Subscription(2, details, "Adjustable intensity.", "Accept/Refuse light requests sent by others.", "Premium Support", BigDecimal.valueOf(1500.00));
        mockOrderRepository.addUserSubscription(userId, subscription);
        mockOrderRepository.setFailOnSave(true);

        CreateOrderInput input = new CreateOrderInput(
                userId,
                37.7749,
                -122.4194,
                50,
                LocalDateTime.now(),
                50,
                3600
        );

        Exception exception = assertThrows(OrderCreationException.class, () -> createOrder.execute(input));
        assertEquals("Failed to save order for user ID: " + userId, exception.getMessage());
    }
    @Test
    void testExecuteOrderSuccessfullyCreated() {
        UUID userId = UUID.randomUUID();
        SubscriptionDetails details = new SubscriptionDetails("Individual User", "Standard Plan", 100, 100, Intensity.MEDIUM);
        Subscription subscription = new Subscription(2, details, "Adjustable intensity.", "Accept/Refuse light requests sent by others.", "Premium Support", BigDecimal.valueOf(1500.00));
        mockOrderRepository.addUserSubscription(userId, subscription);

        CreateOrderInput input = new CreateOrderInput(
                userId,
                37.7749,
                -122.4194,
                50,
                LocalDateTime.now(),
                50,
                3600
        );

        createOrder.execute(input);
        assertEquals("1", outputPort.getOutput().getOrderId());
    }
    @Test
    void testExecuteInvalidIntensity() {
        UUID userId = UUID.randomUUID();
        SubscriptionDetails details = new SubscriptionDetails("Individual User", "Standard Plan", 100, 100, Intensity.MEDIUM);
        Subscription subscription = new Subscription(2, details, "Adjustable intensity.", "Accept/Refuse light requests sent by others.", "Premium Support", BigDecimal.valueOf(1500.00));
        mockOrderRepository.addUserSubscription(userId, subscription);

        CreateOrderInput input = new CreateOrderInput(
                userId,
                37.7749,
                -122.4194,
                -1,
                LocalDateTime.now(),
                50,
                3600
        );

        Exception exception = assertThrows(IntensityException.class, () -> createOrder.execute(input));
        assertEquals("Invalid intensity value: -1", exception.getMessage());
    }

    @Test
    void testExecuteRangeExceedsSubscriptionLevel() {
        UUID userId = UUID.randomUUID();
        SubscriptionDetails details = new SubscriptionDetails("Individual User", "Standard Plan", 100, 100, Intensity.MEDIUM);
        Subscription subscription = new Subscription(2, details, "Adjustable intensity.", "Accept/Refuse light requests sent by others.", "Premium Support", BigDecimal.valueOf(1500.00));
        mockOrderRepository.addUserSubscription(userId, subscription);

        CreateOrderInput input = new CreateOrderInput(
                userId,
                37.7749,
                -122.4194,
                50,
                LocalDateTime.now(),
                200,
                3600
        );

        Exception exception = assertThrows(OrderCreationException.class, () -> createOrder.execute(input));
        assertEquals("Range value 200 exceeds the maximum of 100", exception.getMessage());
    }

    @Test
    void testExecuteDurationExceedsSubscriptionLevel() {
        UUID userId = UUID.randomUUID();
        SubscriptionDetails details = new SubscriptionDetails("Individual User", "Standard Plan", 100, 100, Intensity.MEDIUM);
        Subscription subscription = new Subscription(2, details, "Adjustable intensity.", "Accept/Refuse light requests sent by others.", "Premium Support", BigDecimal.valueOf(1500.00));
        mockOrderRepository.addUserSubscription(userId, subscription);

        CreateOrderInput input = new CreateOrderInput(
                userId,
                37.7749,
                -122.4194,
                50,
                LocalDateTime.now(),
                50,
                999999999
        );

        Exception exception = assertThrows(OrderCreationException.class, () -> createOrder.execute(input));
        assertEquals("Duration value 999999999 exceeds the maximum of 360000 seconds", exception.getMessage());
    }

    @Test
    void testExecuteUnexpectedError() {
        UUID userId = UUID.randomUUID();
        SubscriptionDetails details = new SubscriptionDetails("Individual User", "Standard Plan", 100, 100, Intensity.MEDIUM);
        Subscription subscription = new Subscription(2, details, "Adjustable intensity.", "Accept/Refuse light requests sent by others.", "Premium Support", BigDecimal.valueOf(1500.00));
        mockOrderRepository.addUserSubscription(userId, subscription);

        CreateOrderInput input = new CreateOrderInput(
                userId,
                37.7749,
                -122.4194,
                50,
                LocalDateTime.now(),
                50,
                3600
        );

        mockOrderRepository.setFailOnSave(true);

        Exception exception = assertThrows(OrderCreationException.class, () -> createOrder.execute(input));
        assertEquals("Failed to save order for user ID: " + userId, exception.getMessage());
    }

    @Test
    void testExecuteFailedToSaveOrderWithDifferentInput() {
        UUID userId = UUID.randomUUID();
        SubscriptionDetails details = new SubscriptionDetails("Individual User", "Standard Plan", 100, 100, Intensity.MEDIUM);
        Subscription subscription = new Subscription(2, details, "Adjustable intensity.", "Accept/Refuse light requests sent by others.", "Premium Support", BigDecimal.valueOf(1500.00));
        mockOrderRepository.addUserSubscription(userId, subscription);
        mockOrderRepository.setFailOnSave(true);

        CreateOrderInput input = new CreateOrderInput(
                userId,
                37.7749,
                -122.4194,
                60,
                LocalDateTime.now(),
                60,
                7200
        );

        Exception exception = assertThrows(OrderCreationException.class, () -> createOrder.execute(input));
        assertEquals("Failed to save order for user ID: " + userId, exception.getMessage());
    }

    @Test
    void testExecuteInvalidIntensityWithDifferentValue() {
        UUID userId = UUID.randomUUID();
        SubscriptionDetails details = new SubscriptionDetails("Individual User", "Standard Plan", 100, 100, Intensity.MEDIUM);
        Subscription subscription = new Subscription(2, details, "Adjustable intensity.", "Accept/Refuse light requests sent by others.", "Premium Support", BigDecimal.valueOf(1500.00));
        mockOrderRepository.addUserSubscription(userId, subscription);

        CreateOrderInput input = new CreateOrderInput(
                userId,
                37.7749,
                -122.4194,
                101,
                LocalDateTime.now(),
                50,
                3600
        );

        Exception exception = assertThrows(IntensityException.class, () -> createOrder.execute(input));
        assertEquals("Invalid intensity value: 101", exception.getMessage());
    }

    @Test
    void testExecuteInvalidIntensityNegativeValue() {
        UUID userId = UUID.randomUUID();
        SubscriptionDetails details = new SubscriptionDetails("Individual User", "Standard Plan", 100, 100, Intensity.MEDIUM);
        Subscription subscription = new Subscription(2, details, "Adjustable intensity.", "Accept/Refuse light requests sent by others.", "Premium Support", BigDecimal.valueOf(1500.00));
        mockOrderRepository.addUserSubscription(userId, subscription);

        CreateOrderInput input = new CreateOrderInput(
                userId,
                37.7749,
                -122.4194,
                -5,
                LocalDateTime.now(),
                50,
                3600
        );

        Exception exception = assertThrows(IntensityException.class, () -> createOrder.execute(input));
        assertEquals("Invalid intensity value: -5", exception.getMessage());
    }

    @Test
    void testExecuteUnlimitedSubscription() {
        // Arrange
        UUID userId = UUID.randomUUID();
        SubscriptionDetails details = new SubscriptionDetails("Individual User", "Unlimited Plan", 100, 100, Intensity.HIGH);
        Subscription subscription = new Subscription(
                Integer.MAX_VALUE,
                details,
                "Unlimited usage",
                "No restrictions",
                "Full support",
                BigDecimal.valueOf(3000.00)
        ) {
            @Override
            public boolean isUnlimited() {
                return true; // Simulate unlimited subscription
            }
        };
        mockOrderRepository.addUserSubscription(userId, subscription);

        CreateOrderInput input = new CreateOrderInput(
                userId,
                37.7749,
                -122.4194,
                50,
                LocalDateTime.now(),
                50,
                999999999
        );

        // Act & Assert
        assertDoesNotThrow(() -> createOrder.execute(input), "Unlimited subscriptions should bypass duration check");
        assertEquals("1", outputPort.getOutput().getOrderId(), "Order ID should be generated successfully");
    }

}
