package be.howest.adria.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {
    private final UUID id;
    private Subscription subscription;
    private final List<Order> orders;

    private User(UUID id, Subscription subscription) {
        this.id = id;
        this.subscription = subscription;
        this.orders = new ArrayList<>();
    }

    public static User create() {
        return new User(UUID.randomUUID(), null);
    }

    public static User create(Subscription subscription) {
        return new User(UUID.randomUUID(), subscription);
    }

    public static User create(UUID id, Subscription subscription) {
        return new User(id, subscription);
    }

    public UUID getId() {
        return id;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public void setSubscriptionId(int subscriptionId) {
        if (this.subscription != null) {
            this.subscription.setSubscriptionId(subscriptionId);
        }
    }

    public List<Order> getOrders() {
        return orders;
    }

    public int getRemainingAllowedTimeInSeconds() {
        if (subscription == null || subscription.isUnlimited()) {
            return Integer.MAX_VALUE; // Representing infinite time
        }
        return subscription.getHours() * 3600;
    }

    public boolean addOrder(Order order) {
        if (!canAddOrder(order)) {
            return false;
        }
        return orders.add(order);
    }

    private boolean canAddOrder(Order order) {
        if (subscription != null && !subscription.isUnlimited()) {
            long totalOrderTime = orders.stream().mapToLong(Order::getDurationSeconds).sum();
            return totalOrderTime + order.getDurationSeconds() <= getRemainingAllowedTimeInSeconds();
        }
        return true;
    }
}