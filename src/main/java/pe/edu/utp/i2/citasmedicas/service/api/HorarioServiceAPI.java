package pe.edu.utp.i2.citasmedicas.service.api;

import pe.edu.utp.i2.citasmedicas.commons.GenericServiceAPI;
import pe.edu.utp.i2.citasmedicas.model.Horario;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface HorarioServiceAPI extends GenericServiceAPI<Horario, Integer> {
    List<Horario> searchHorarios(Integer idMedico, LocalDate start, LocalDate end);

    Horario searchHorarioByFechas(Integer idMedico, LocalDateTime horaInicio, LocalDateTime horaFin);
}
