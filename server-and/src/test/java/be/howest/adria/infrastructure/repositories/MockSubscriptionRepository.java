package be.howest.adria.infrastructure.repositories;

import be.howest.adria.application.contracts.repositories.SubscriptionRepository;
import be.howest.adria.domain.Subscription;

import java.util.ArrayList;
import java.util.List;

public class MockSubscriptionRepository implements SubscriptionRepository {
    private final List<Subscription> subscriptions = new ArrayList<>();

    @Override
    public List<Subscription> findAll() {
        return new ArrayList<>(subscriptions);
    }

    @Override
    public void saveAll(List<Subscription> subscriptions) {
        this.subscriptions.addAll(subscriptions);
    }

    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }
}