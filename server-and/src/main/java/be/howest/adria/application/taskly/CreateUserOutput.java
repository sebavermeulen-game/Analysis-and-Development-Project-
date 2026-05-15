package be.howest.adria.application.taskly;

import java.util.UUID;

public class CreateUserOutput {
    private final UUID id;

    public CreateUserOutput(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
