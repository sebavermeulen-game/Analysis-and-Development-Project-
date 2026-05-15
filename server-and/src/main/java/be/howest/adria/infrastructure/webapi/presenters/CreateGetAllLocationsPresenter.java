package be.howest.adria.infrastructure.webapi.presenters;

import be.howest.adria.application.contracts.usecases.OutputPort;
import be.howest.adria.application.taskly.GetAllLocationsOutput;
import be.howest.adria.infrastructure.webapi.responsehandlers.CreateGetAllLocationsResponseHandler;

public class CreateGetAllLocationsPresenter implements OutputPort<GetAllLocationsOutput> {
    private final CreateGetAllLocationsResponseHandler responseHandler;

    public CreateGetAllLocationsPresenter(CreateGetAllLocationsResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
    }

    @Override
    public void present(GetAllLocationsOutput output) {
        responseHandler.handle(output.getLocations());
    }
}