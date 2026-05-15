package be.howest.adria.infrastructure.webapi.presenters;

import be.howest.adria.application.contracts.usecases.OutputPort;
import be.howest.adria.application.taskly.CreateOrderOutput;
import be.howest.adria.infrastructure.webapi.shared.ResponseHandler;

public class CreateOrderPresenter implements OutputPort<CreateOrderOutput> {

    private final ResponseHandler<String> responseHandler;

    public CreateOrderPresenter(ResponseHandler<String> responseHandler) {
        this.responseHandler = responseHandler;
    }

    @Override
    public void present(CreateOrderOutput data) {
        responseHandler.handle(data.getOrderId());
    }
}