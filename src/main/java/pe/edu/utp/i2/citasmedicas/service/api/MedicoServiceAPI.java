package pe.edu.utp.i2.citasmedicas.service.api;

import pe.edu.utp.i2.citasmedicas.commons.GenericServiceAPI;
import pe.edu.utp.i2.citasmedicas.model.Medico;

import java.util.List;

public interface MedicoServiceAPI extends GenericServiceAPI<Medico, Integer> {
	List<Medico> searchByEspecialidad(Integer idEspecialidad);
}
