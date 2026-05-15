package be.howest.adria.infrastructure.repositories;

import be.howest.adria.application.contracts.repositories.CreateUserSubscriptionRepository;
import be.howest.adria.domain.Subscription;
import be.howest.adria.domain.User;

import java.util.HashMap;
import java.util.Map;

public class MockCreateUserSubscriptionRepository implements CreateUserSubscriptionRepository {
    private final Map<Integer, Subscription> subscriptions = new HashMap<>();

    @Override
    public Subscription findById(int subscriptionId) {
        return subscriptions.get(subscriptionId);
    }

    @Override
    public void save(User user) {
        System.out.println("Mock save called for user: " + user.getId());
    }

    public void addSubscription(Subscription subscription) {
        subscriptions.put(subscription.getSubscriptionId(), subscription);
    }
}
