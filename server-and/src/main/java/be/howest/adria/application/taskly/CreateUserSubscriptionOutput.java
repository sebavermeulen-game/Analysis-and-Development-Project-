package be.howest.adria.application.taskly;

public class CreateUserSubscriptionOutput {

    private final Integer subscriptionId;

    public CreateUserSubscriptionOutput(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Integer getSubscriptionId() {
        return subscriptionId;
    }
}
