package be.howest.adria.application.taskly;

import be.howest.adria.application.contracts.repositories.LocationsRepository;
import be.howest.adria.application.contracts.usecases.OutputPort;
import be.howest.adria.application.contracts.usecases.UseCase;

public class GetAllLocations implements UseCase<Void> {

    private final LocationsRepository locationsRepository;
    private final OutputPort<GetAllLocationsOutput> outputPort;

    public GetAllLocations(LocationsRepository locationsRepository, OutputPort<GetAllLocationsOutput> outputPort) {
        this.locationsRepository = locationsRepository;
        this.outputPort = outputPort;
    }

    @Override
    public void execute(Void input) {
        outputPort.present(new GetAllLocationsOutput(locationsRepository.getAllLocations()));
    }
}