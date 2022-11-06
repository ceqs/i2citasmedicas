package pe.edu.utp.i2.citasmedicas.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fhInicio;

    @Column(name = "fh_fin", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fhFin;

    @Column(name = "fecha_cita", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd")
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

    public LocalDateTime getFhFin() {
        return fhFin;
    }

    public void setFhFin(LocalDateTime fhFin) {
        this.fhFin = fhFin;
    }

    public LocalDateTime getFhInicio() {
        return fhInicio;
    }

    public void setFhInicio(LocalDateTime fhInicio) {
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

/*
    //MTC
    private int mes;
    private double total;
    private String nameM;

    public int getMes() { return mes; }
    public void setMes(int mes) { this.mes = mes; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public String getNameM() { return nameM; }
    public void setNameM(String nameM) { this.nameM = nameM; }

 */


}