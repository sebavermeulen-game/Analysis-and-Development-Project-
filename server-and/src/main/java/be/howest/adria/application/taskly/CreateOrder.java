package be.howest.adria.application.taskly;

import be.howest.adria.application.contracts.repositories.OrderRepository;
import be.howest.adria.application.contracts.usecases.OutputPort;
import be.howest.adria.application.contracts.usecases.UseCase;
import be.howest.adria.domain.Location;
import be.howest.adria.domain.Order;
import be.howest.adria.domain.Subscription;
import be.howest.adria.domain.Intensity;
import be.howest.adria.infrastructure.shared.utils.IntensityException;
import be.howest.adria.infrastructure.shared.utils.OrderCreationException;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateOrder implements UseCase<CreateOrderInput> {

    private static final Logger LOGGER = Logger.getLogger(CreateOrder.class.getName());

    private final OrderRepository orderRepository;
    private final OutputPort<CreateOrderOutput> outputPort;

    public CreateOrder(OrderRepository orderRepository, OutputPort<CreateOrderOutput> outputPort) {
        this.orderRepository = orderRepository;
        this.outputPort = outputPort;
    }

    @Override
    public void execute(CreateOrderInput input) {
        LOGGER.log(Level.INFO, "Executing CreateOrder with input: {0}", input);

        UUID userId = input.getUserId();
        LOGGER.log(Level.INFO, "User ID: {0}", userId);

        try {
            Subscription subscription = orderRepository.findSubscriptionByUserId(userId);
            if (subscription == null) {
                LOGGER.log(Level.SEVERE, "User does not have a valid subscription: {0}", userId);
                throw new OrderCreationException(String.format("User %s does not have a valid subscription", userId));
            }
            LOGGER.log(Level.INFO, "Subscription found: {0}", subscription);

            validateOrder(input, subscription);

            Order order = new Order(
                    new Location(input.getLatitude(), input.getLongitude()),
                    input.getIntensity(),
                    input.getStartTime(),
                    input.getEndTime(),
                    input.getRange(),
                    userId.toString()
            );
            LOGGER.log(Level.INFO, "Order created: {0}", order);

            orderRepository.save(order);
            LOGGER.log(Level.INFO, "Order saved to repository");

            int orderId = orderRepository.findOrderId(order);
            outputPort.present(new CreateOrderOutput(String.valueOf(orderId)));
            LOGGER.log(Level.INFO, "CreateOrder execution completed for user ID: {0}", userId);
        } catch (OrderCreationException | IntensityException e) {
            outputPort.present(new CreateOrderOutput("Error: " + e.getMessage()));
            throw e;
        } catch (RuntimeException e) {
            throw new OrderCreationException("Failed to save order for user ID: " + userId, e);
        }
    }

    private void validateIntensity(int intensity, Subscription subscription) {
        LOGGER.log(Level.INFO, "Validating intensity: {0}", intensity);
        if (!Intensity.isValidIntensity(intensity)) {
            LOGGER.log(Level.SEVERE, "Invalid intensity value: {0}", intensity);
            throw new IntensityException(String.format("Invalid intensity value: %d", intensity));
        }

        if (!Intensity.isWithinMaxIntensityForSubscription(intensity, subscription)) {
            LOGGER.log(Level.SEVERE, "Intensity value {0} exceeds the maximum of {1}", new Object[]{intensity, subscription.getIntensity().getMaxPercentage()});
            throw new IntensityException(String.format("Intensity value %d exceeds the maximum of %s", intensity, subscription.getIntensity().getMaxPercentage()));
        }
        LOGGER.log(Level.INFO, "Intensity value is valid for subscription level as: {0}", subscription.getIntensity().name());
    }

    private void validateOrder(CreateOrderInput input, Subscription subscription) {
        validateIntensity(input.getIntensity(), subscription);

        if (input.getRange() > subscription.getRange()) {
            LOGGER.log(Level.SEVERE, "Range value {0} exceeds the maximum of {1}", new Object[]{input.getRange(), subscription.getRange()});
            throw new OrderCreationException(String.format("Range value %d exceeds the maximum of %d", input.getRange(), subscription.getRange()));
        }

        if (!subscription.isUnlimited() && input.getDuration() > subscription.getHours() * 3600) {
            LOGGER.log(Level.SEVERE, "Duration value {0} exceeds the maximum of {1} seconds", new Object[]{input.getDuration(), subscription.getHours() * 3600});
            throw new OrderCreationException(String.format("Duration value %d exceeds the maximum of %d seconds", input.getDuration(), subscription.getHours() * 3600));
        }
    }
}