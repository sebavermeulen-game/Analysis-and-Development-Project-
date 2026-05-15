package be.howest.adria.infrastructure.repositories;

import be.howest.adria.application.contracts.repositories.UserRepository;
import be.howest.adria.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class MockCreateUserRepository implements UserRepository {
    public final Map<UUID, User> users = new HashMap<>();

    @Override
    public Optional<User> byId(UUID userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public void save(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public boolean userExists(UUID userId) {
        return users.containsKey(userId);
    }

    @Override
    public void updateSubscription(UUID userId, int subscriptionId) {
        User user = users.get(userId);
        if (user != null) {
            user.setSubscriptionId(subscriptionId);
        }
    }

    public User getUserById(UUID userId) {
        return users.get(userId);
    }

}
