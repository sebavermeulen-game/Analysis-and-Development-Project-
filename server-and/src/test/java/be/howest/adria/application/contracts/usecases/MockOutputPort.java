package be.howest.adria.application.contracts.usecases;

public class MockOutputPort<D> implements OutputPort<D> {
    private D output;
    @Override
    public void present(D data) {
        this.output = data;
    }

    public D getOutput() {
        return output;
    }
}
