package be.howest.adria.infrastructure.persistence.shared.utils;

import java.util.ArrayDeque;
import java.util.Deque;

public class DoubleEndedQueue<T> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private final Deque<T> deque;

    public DoubleEndedQueue(int initialCapacity) {
        this.deque = new ArrayDeque<>(initialCapacity);
    }

    public DoubleEndedQueue() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public void addBothSides(T first, T second) {
        deque.addFirst(first);
        deque.addLast(second);
    }

    public Deque<T> getDeque() {
        return deque;
    }
}