package pe.edu.utp.i2.citasmedicas.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public class Programacion {

    private Medico medico;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime start;

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime end;

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
