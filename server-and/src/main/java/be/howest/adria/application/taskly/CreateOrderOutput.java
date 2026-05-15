package be.howest.adria.application.taskly;

public class CreateOrderOutput {

    private final String orderId;

    public CreateOrderOutput(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
