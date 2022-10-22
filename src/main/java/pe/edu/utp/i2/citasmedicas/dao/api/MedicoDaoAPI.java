package pe.edu.utp.i2.citasmedicas.dao.api;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.i2.citasmedicas.model.Medico;

public interface MedicoDaoAPI extends CrudRepository<Medico, Integer> {

}
