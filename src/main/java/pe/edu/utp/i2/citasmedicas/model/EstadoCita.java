package pe.edu.utp.i2.citasmedicas.model;

public enum EstadoCita {
    DISPONIBLE("DISPONIBLE"),
    RESERVADO("RESERVADO"),
    ATENDIDO("ATENDIDO"),
    INASISTENCIA("INASISTENCIA"),
    LIBERADO("LIBERADO"),
    DISPONIBLE_COLOR("#32cd32"),
    RESERVADO_COLOR("#cd5c5c"),
    ATENDIDO_COLOR("#107e05"),
    INASISTENCIA_COLOR("#dd36da"),
    LIBERADO_COLOR("#2683d0");

    private final String text;

    /**
     * @param text
     */
    EstadoCita(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
