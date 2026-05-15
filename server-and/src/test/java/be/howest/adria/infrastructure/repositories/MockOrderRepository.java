package be.howest.adria.infrastructure.repositories;

import be.howest.adria.application.contracts.repositories.OrderRepository;
import be.howest.adria.domain.Order;
import be.howest.adria.domain.Subscription;

import java.util.*;

public class MockOrderRepository implements OrderRepository {
    private final Map<UUID, Subscription> userSubscriptions = new HashMap<>();
    private final List<Order> orders = new ArrayList<>();
    private final Map<Order, Integer> orderIds = new HashMap<>();
    private int nextOrderId = 1;
    private boolean failOnSave = false;

    @Override
    public Subscription findSubscriptionByUserId(UUID userId) {
        return userSubscriptions.get(userId);
    }

    @Override
    public void save(Order order) {
        if (failOnSave) {
            throw new RuntimeException("Failed to save order");
        }
        orders.add(order);
        orderIds.put(order, nextOrderId++);
    }

    @Override
    public int findOrderId(Order order) {
        return orderIds.getOrDefault(order, -1);
    }

    public void addUserSubscription(UUID userId, Subscription subscription) {
        userSubscriptions.put(userId, subscription);
    }

    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public void setFailOnSave(boolean failOnSave) {
        this.failOnSave = failOnSave;
    }
}