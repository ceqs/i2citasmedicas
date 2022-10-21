package pe.edu.utp.i2.citasmedicas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import pe.edu.utp.i2.citasmedicas.commons.GenericServiceImpl;
import pe.edu.utp.i2.citasmedicas.dao.api.EspecialidadDaoAPI;
import pe.edu.utp.i2.citasmedicas.model.Especialidad;
import pe.edu.utp.i2.citasmedicas.service.api.EspecialidadServiceAPI;

@Service
public class EspecialidadServiceImpl extends GenericServiceImpl<Especialidad, Integer> implements EspecialidadServiceAPI {

	@Autowired
	private EspecialidadDaoAPI especialidadDaoAPI;
	
	@Override
	public CrudRepository<Especialidad, Integer> getDao() {
		return especialidadDaoAPI;
	}

}
