package conductor.connect.probate.Models;

public enum Status {
    PUBLISHED(0),
    PROCESSING(1),
    DOWNLOADING(2),
    DONE(3);

    private final int valor;

    Status(int valor) {
        this.valor = valor;
    }
}
