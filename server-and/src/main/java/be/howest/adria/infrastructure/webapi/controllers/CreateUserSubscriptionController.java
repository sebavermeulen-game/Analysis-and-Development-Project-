package be.howest.adria.infrastructure.webapi.controllers;

import be.howest.adria.application.taskly.CreateUserSubscription;
import be.howest.adria.application.taskly.CreateUserSubscriptionInput;
import be.howest.adria.infrastructure.shared.contracts.Controller;
import be.howest.adria.infrastructure.webapi.shared.Request;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateUserSubscriptionController implements Controller<Request> {

    private final CreateUserSubscription createSubscriptionUseCase;

    private static final Logger LOGGER = Logger.getLogger(CreateUserSubscriptionController.class.getName());

    public CreateUserSubscriptionController(CreateUserSubscription createSubscriptionUseCase) {
        this.createSubscriptionUseCase = createSubscriptionUseCase;
    }

    @Override
    public void handle(Request request) {
        LOGGER.log(Level.INFO, "Handling create user subscription request");
        LOGGER.log(Level.INFO, "Request body: {0}", request.body());
        String userIdString = request.body().getString("userId");
        int subscriptionId = request.body().getInteger("subscriptionId");

        LOGGER.log(Level.INFO, "userId: {0}", userIdString);
        LOGGER.log(Level.INFO, "subscriptionId: {0}", subscriptionId);

        if (userIdString == null || userIdString.isEmpty() || subscriptionId == 0) {
            LOGGER.log(Level.SEVERE, "Missing required body parameters: userId or subscriptionId");
            throw new IllegalArgumentException("Missing required body parameters: userId or subscriptionId");
        }

        UUID userId;
        LOGGER.log(Level.INFO, "Starting UUID and int parsing");
        try {
            userId = UUID.fromString(userIdString);
            LOGGER.log(Level.INFO, "UUID and int parsing completed successfully");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID or int string: " + e.getMessage());
        }

        LOGGER.log(Level.INFO, "Creating CreateUserSubscriptionInput with userId: {0} and subscriptionId: {1}", new Object[]{userId, subscriptionId});
        CreateUserSubscriptionInput input = new CreateUserSubscriptionInput(userId.toString(), subscriptionId);
        LOGGER.log(Level.INFO, "CreateUserSubscriptionInput created: {0}", input);

        LOGGER.log(Level.INFO, "Executing createSubscriptionUseCase with input: {0}", input);
        createSubscriptionUseCase.execute(input);
        LOGGER.log(Level.INFO, "createSubscriptionUseCase executed successfully");

        LOGGER.log(Level.INFO, "User subscription created successfully");
    }
}