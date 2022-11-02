package pe.edu.utp.i2.citasmedicas.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cita", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;

    @Column(name = "fh_inicio", nullable = false)
    private Instant fhInicio;

    @Column(name = "fh_fin", nullable = false)
    private Instant fhFin;

    @Column(name = "fecha_cita", nullable = false)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaCita;

    @Column(name = "tipo_cita", nullable = false, length = 50)
    private String tipoCita;

    @Column(name = "tipo_seguro", nullable = false, length = 50)
    private String tipoSeguro;

    public String getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(String tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public String getTipoCita() {
        return tipoCita;
    }

    public void setTipoCita(String tipoCita) {
        this.tipoCita = tipoCita;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    public Instant getFhFin() {
        return fhFin;
    }

    public void setFhFin(Instant fhFin) {
        this.fhFin = fhFin;
    }

    public Instant getFhInicio() {
        return fhInicio;
    }

    public void setFhInicio(Instant fhInicio) {
        this.fhInicio = fhInicio;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}