package be.howest.adria.application.taskly;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateUserSubscriptionInput {
    private final String userId;
    private final int subscriptionId;

    private static final Logger LOGGER = Logger.getLogger(CreateUserSubscriptionInput.class.getName());

    public CreateUserSubscriptionInput(String userId, int subscriptionId) {
        LOGGER.log(Level.INFO, "CreateUserSubscriptionInput constructor called with userId: {0}, subscriptionId: {1}", new Object[]{userId, subscriptionId});
        this.userId = userId;
        this.subscriptionId = subscriptionId;
        LOGGER.log(Level.INFO, "CreateUserSubscriptionInput object created with userId: {0}, subscriptionId: {1}", new Object[]{this.userId, this.subscriptionId});
    }

    public String getUserId() {
        return userId;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }
}