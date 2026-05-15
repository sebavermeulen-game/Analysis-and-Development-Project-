package be.howest.adria.application.taskly;

import be.howest.adria.application.contracts.repositories.CreateUserSubscriptionRepository;
import be.howest.adria.application.contracts.repositories.UserRepository;
import be.howest.adria.application.contracts.usecases.OutputPort;
import be.howest.adria.application.contracts.usecases.UseCase;
import be.howest.adria.domain.Subscription;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateUserSubscription implements UseCase<CreateUserSubscriptionInput> {

    private static final Logger LOGGER = Logger.getLogger(CreateUserSubscription.class.getName());

    private final UserRepository userRepository;
    private final CreateUserSubscriptionRepository createUserSubscriptionRepository;
    private final OutputPort<CreateUserSubscriptionOutput> outputPort;

    public CreateUserSubscription(UserRepository userRepository, CreateUserSubscriptionRepository createUserSubscriptionRepository, OutputPort<CreateUserSubscriptionOutput> outputPort) {
        this.userRepository = userRepository;
        this.createUserSubscriptionRepository = createUserSubscriptionRepository;
        this.outputPort = outputPort;
    }

    @Override
    public void execute(CreateUserSubscriptionInput input) {
        LOGGER.log(Level.INFO, "Executing CreateUserSubscription with input: {0}", input);

        UUID userId = UUID.fromString(input.getUserId());
        if (!userRepository.userExists(userId)) {
            LOGGER.log(Level.SEVERE, "User not found for ID: {0}", input.getUserId());
            throw new IllegalArgumentException("User not found");
        }

        Subscription subscription = createUserSubscriptionRepository.findById(input.getSubscriptionId());
        if (subscription == null) {
            LOGGER.log(Level.SEVERE, "Subscription not found for ID: {0}", input.getSubscriptionId());
            throw new IllegalArgumentException("Subscription not found");
        }

        LOGGER.log(Level.INFO, "Subscription found: {0}", subscription);

        userRepository.updateSubscription(userId, subscription.getSubscriptionId());
        LOGGER.log(Level.INFO, "User subscription updated for user ID: {0} with subscription ID: {1}", new Object[]{userId, subscription.getSubscriptionId()});

        outputPort.present(new CreateUserSubscriptionOutput(subscription.getSubscriptionId()));
        LOGGER.log(Level.INFO, "CreateUserSubscription execution completed for user ID: {0}", userId);
    }
}