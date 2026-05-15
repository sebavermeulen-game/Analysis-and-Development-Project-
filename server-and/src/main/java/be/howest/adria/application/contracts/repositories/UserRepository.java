package be.howest.adria.application.contracts.repositories;

import be.howest.adria.domain.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> byId(UUID userId);
    void save(User user);
    boolean userExists(UUID userId);
    void updateSubscription(UUID userId, int subscriptionId);
}