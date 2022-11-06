package pe.edu.utp.i2.citasmedicas.service.api;

import pe.edu.utp.i2.citasmedicas.commons.GenericServiceAPI;
import pe.edu.utp.i2.citasmedicas.model.Reserva;

import java.time.LocalDate;
import java.util.List;

public interface ReservaServiceAPI extends GenericServiceAPI<Reserva, Integer> {
    void sendEmail(String fecha, String horario, String medico, String receptor);

    List<Reserva> searchReservasPorFechas(Integer idMedico, LocalDate start, LocalDate end);
}
