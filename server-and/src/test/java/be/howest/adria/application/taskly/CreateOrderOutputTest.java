package be.howest.adria.application.taskly;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateOrderOutputTest {

    @Test
    void testGetOrderId() {
        String orderId = "12345";
        CreateOrderOutput output = new CreateOrderOutput(orderId);

        assertEquals(orderId, output.getOrderId());
    }
}