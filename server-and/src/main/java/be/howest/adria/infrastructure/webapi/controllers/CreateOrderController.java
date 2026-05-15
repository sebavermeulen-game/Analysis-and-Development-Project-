package be.howest.adria.infrastructure.webapi.controllers;

import be.howest.adria.application.contracts.usecases.UseCase;
import be.howest.adria.application.taskly.CreateOrderInput;
import be.howest.adria.infrastructure.shared.contracts.Controller;
import be.howest.adria.infrastructure.shared.utils.OrderCreationException;
import be.howest.adria.infrastructure.webapi.shared.Request;
import io.vertx.core.json.JsonObject;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateOrderController implements Controller<Request> {
    private static final Logger LOGGER = Logger.getLogger(CreateOrderController.class.getName());
    private final UseCase<CreateOrderInput> createOrderUseCase;

    public CreateOrderController(UseCase<CreateOrderInput> createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }

    @Override
    public void handle(Request request) {
        LOGGER.log(Level.INFO, "Handling create order request");

        JsonObject params = request.body();
        LOGGER.log(Level.INFO, "Request JSON: {0}", params);

        if (params == null) {
            LOGGER.log(Level.SEVERE, "Request data is null");
            throw new IllegalArgumentException("Request data cannot be null");
        }

        double latitude = params.getJsonObject("location").getDouble("lat");
        double longitude = params.getJsonObject("location").getDouble("lng");
        int range = params.getInteger("range");
        int intensity = params.getInteger("intensity");
        UUID userId = UUID.fromString(params.getString("userId"));
        int duration = params.getInteger("duration");

        LOGGER.log(Level.INFO, "Extracted parameters: latitude={0}, longitude={1}, range={2}, intensity={3}, userId={4}, duration={5}",
                new Object[]{latitude, longitude, range, intensity, userId, duration});

        LocalDateTime startDateTime = LocalDateTime.now();

        CreateOrderInput input = new CreateOrderInput(userId, latitude, longitude, intensity, startDateTime, range, duration);
        try {
            createOrderUseCase.execute(input);
            LOGGER.log(Level.INFO, "Order created successfully");
        } catch (Exception e) {
            throw new OrderCreationException("Failed to create order", e);
        }
    }
}