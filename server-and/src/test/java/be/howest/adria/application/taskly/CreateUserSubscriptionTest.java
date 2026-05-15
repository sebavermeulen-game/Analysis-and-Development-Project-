package be.howest.adria.application.taskly;

import be.howest.adria.domain.User;
import be.howest.adria.infrastructure.repositories.MockCreateUserSubscriptionRepository;
import be.howest.adria.application.contracts.usecases.MockOutputPort;
import be.howest.adria.infrastructure.repositories.MockUserRepository;
import be.howest.adria.domain.Subscription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserSubscriptionTest {

    private MockUserRepository mockUserRepository;
    private MockCreateUserSubscriptionRepository mockCreateUserSubscriptionRepository;
    private MockOutputPort<CreateUserSubscriptionOutput> outputPort;
    private UUID userId;
    private int subscriptionId;
    private CreateUserSubscriptionInput input;
    private CreateUserSubscription useCase;

    @BeforeEach
    void setup() {
        mockUserRepository = new MockUserRepository();
        mockCreateUserSubscriptionRepository = new MockCreateUserSubscriptionRepository();
        outputPort = new MockOutputPort<>();
        userId = UUID.randomUUID();
        subscriptionId = 1;
        input = new CreateUserSubscriptionInput(userId.toString(), subscriptionId);
        useCase = new CreateUserSubscription(
                mockUserRepository,
                mockCreateUserSubscriptionRepository,
                outputPort
        );
    }

    @Test
    void testCreateUserSubscriptionOutputNotNull() {
        // Arrange
        mockUserRepository.addUser(userId, null);
        mockCreateUserSubscriptionRepository.addSubscription(new Subscription(subscriptionId));

        // Act
        useCase.execute(input);
        CreateUserSubscriptionOutput result = outputPort.getOutput();

        // Assert
        assertNotNull(result, "Output should not be null");
    }

    @Test
    void testCreateUserSubscriptionIdMatches() {
        // Arrange
        mockUserRepository.addUser(userId, null);
        mockCreateUserSubscriptionRepository.addSubscription(new Subscription(subscriptionId));

        // Act
        useCase.execute(input);
        CreateUserSubscriptionOutput result = outputPort.getOutput();

        // Assert
        assertEquals(subscriptionId, result.getSubscriptionId(), "Subscription ID should match");
    }

    @Test
    void testCreateUserSubscriptionUserExists() {
        // Arrange
        mockUserRepository.addUser(userId, null);
        mockCreateUserSubscriptionRepository.addSubscription(new Subscription(subscriptionId));

        // Act
        useCase.execute(input);
        User updatedUser = mockUserRepository.getUserById(userId);

        // Assert
        assertNotNull(updatedUser, "User should exist in the repository");
    }

    @Test
    void testCreateUserSubscriptionUserSubscriptionUpdated() {
        // Arrange
        mockUserRepository.addUser(userId, null);
        mockCreateUserSubscriptionRepository.addSubscription(new Subscription(subscriptionId));

        // Act
        useCase.execute(input);
        User updatedUser = mockUserRepository.getUserById(userId);

        // Assert
        assertNotNull(updatedUser.getSubscription(), "User's subscription should be updated");
        assertEquals(subscriptionId, updatedUser.getSubscription().getSubscriptionId(), "Updated subscription ID should match");
    }

    @Test
    void testUserNotFound() {
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> useCase.execute(input));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testSubscriptionNotFound() {
        // Arrange
        mockUserRepository.addUser(userId, null);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> useCase.execute(input));
        assertEquals("Subscription not found", exception.getMessage());
    }
}