package pe.edu.utp.i2.citasmedicas.dao.api;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.i2.citasmedicas.model.Horario;
import pe.edu.utp.i2.citasmedicas.model.Paciente;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface HorarioDaoAPI extends CrudRepository<Horario, Integer> {

    List<Horario> findByMedicoIdAndFechaBetween(Integer idMedico, LocalDate start, LocalDate end);

    Horario findByMedicoIdAndHoraInicioAndHoraFin(Integer idMedico, LocalDateTime horaInicio, LocalDateTime horaFin);
}
