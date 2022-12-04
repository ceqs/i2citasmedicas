package pe.edu.utp.i2.citasmedicas.dao.api;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.i2.citasmedicas.model.Horario;
import pe.edu.utp.i2.citasmedicas.model.Medico;

import java.time.LocalDate;
import java.util.List;

public interface MedicoDaoAPI extends CrudRepository<Medico, Integer> {

    List<Medico> findByEspecialidadId(Integer idEspecialidad);
}
