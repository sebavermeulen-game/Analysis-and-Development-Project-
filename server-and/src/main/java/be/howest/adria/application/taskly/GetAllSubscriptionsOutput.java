package be.howest.adria.application.taskly;

import be.howest.adria.domain.Subscription;

import java.util.List;

public class GetAllSubscriptionsOutput {

    private final List<Subscription> subscriptions;

    public GetAllSubscriptionsOutput(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }
}
