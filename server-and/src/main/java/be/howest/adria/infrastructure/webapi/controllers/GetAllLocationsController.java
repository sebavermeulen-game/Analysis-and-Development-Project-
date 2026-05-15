package be.howest.adria.infrastructure.webapi.controllers;

import be.howest.adria.application.contracts.usecases.UseCase;
import be.howest.adria.infrastructure.shared.contracts.Controller;
import be.howest.adria.infrastructure.webapi.shared.Request;

public class GetAllLocationsController implements Controller<Request> {

    private final UseCase<Void> getAllLocations;

    public GetAllLocationsController(UseCase<Void> getAllLocations) {
        this.getAllLocations = getAllLocations;
    }

    @Override
    public void handle(Request request) {
        getAllLocations.execute(null);
    }
}