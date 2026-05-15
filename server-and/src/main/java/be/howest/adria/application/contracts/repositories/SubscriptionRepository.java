package be.howest.adria.application.contracts.repositories;

import be.howest.adria.domain.Subscription;

import java.util.List;

public interface SubscriptionRepository {
    List<Subscription> findAll();
    void saveAll(List<Subscription> subscriptions);
}