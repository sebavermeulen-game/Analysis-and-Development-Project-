package be.howest.adria.application.contracts.repositories;

import be.howest.adria.domain.Subscription;
import be.howest.adria.domain.User;

public interface CreateUserSubscriptionRepository {
    void save(User user);
    Subscription findById(int subscriptionId);
}