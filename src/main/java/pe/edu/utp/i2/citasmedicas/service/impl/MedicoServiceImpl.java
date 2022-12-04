package pe.edu.utp.i2.citasmedicas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import pe.edu.utp.i2.citasmedicas.commons.GenericServiceImpl;
import pe.edu.utp.i2.citasmedicas.dao.api.MedicoDaoAPI;
import pe.edu.utp.i2.citasmedicas.model.Medico;
import pe.edu.utp.i2.citasmedicas.service.api.MedicoServiceAPI;

import java.util.List;

@Service
public class MedicoServiceImpl extends GenericServiceImpl<Medico, Integer> implements MedicoServiceAPI {

	@Autowired
	private MedicoDaoAPI medicoDaoAPI;
	
	@Override
	public CrudRepository<Medico, Integer> getDao() {
		return medicoDaoAPI;
	}

	@Override
	public List<Medico> searchByEspecialidad(Integer idEspecialidad) {
		return medicoDaoAPI.findByEspecialidadId(idEspecialidad);
	}
}
