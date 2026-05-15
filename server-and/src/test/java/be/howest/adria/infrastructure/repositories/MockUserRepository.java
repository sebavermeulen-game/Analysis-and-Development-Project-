package be.howest.adria.infrastructure.repositories;

import be.howest.adria.application.contracts.repositories.UserRepository;
import be.howest.adria.domain.Subscription;
import be.howest.adria.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class MockUserRepository implements UserRepository {
    private final Map<UUID, User> users = new HashMap<>();

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
            Subscription subscription = new Subscription(subscriptionId);
            user.setSubscription(subscription);
        }
    }

    public void addUser(UUID id, Subscription subscription) {
        User user = User.create(id, subscription);
        users.put(user.getId(), user);
    }

    public User getUserById(UUID userId) {
        return users.get(userId);
    }
}