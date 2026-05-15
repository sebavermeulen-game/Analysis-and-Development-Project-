package be.howest.adria.application.taskly;

import be.howest.adria.infrastructure.repositories.MockCreateUserRepository;
import be.howest.adria.application.contracts.usecases.MockOutputPort;
import be.howest.adria.domain.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserTest {

    private MockCreateUserRepository mockCreateUserRepository;
    private MockOutputPort<CreateUserOutput> outputPort;
    private CreateUser createUser;

    @BeforeEach
    void setup() {
        mockCreateUserRepository = new MockCreateUserRepository();
        outputPort = new MockOutputPort<>();

        createUser = new CreateUser(mockCreateUserRepository, outputPort);
    }

    @Test
    void testExecute() {
        // Act
        createUser.execute(null);

        // Assert
        CreateUserOutput result = outputPort.getOutput();
        assertNotNull(result, "Output should not be null");

        UUID userId = result.getId();
        assertNotNull(userId, "User ID should not be null");

        User savedUser = mockCreateUserRepository.getUserById(userId);
        assertNotNull(savedUser, "User should be saved in the repository");
        assertEquals(userId, savedUser.getId(), "Saved user's ID should match the output");
    }

    @Test
    void testRepositoryIsUpdated() {
        // Act
        createUser.execute(null);

        // Assert
        assertEquals(1, mockCreateUserRepository.users.size(), "Repository should contain one user");
    }
}