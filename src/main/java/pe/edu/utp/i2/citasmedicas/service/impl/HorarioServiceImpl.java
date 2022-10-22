package pe.edu.utp.i2.citasmedicas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import pe.edu.utp.i2.citasmedicas.commons.GenericServiceImpl;
import pe.edu.utp.i2.citasmedicas.dao.api.HorarioDaoAPI;
import pe.edu.utp.i2.citasmedicas.model.Horario;
import pe.edu.utp.i2.citasmedicas.service.api.HorarioServiceAPI;

@Service
public class HorarioServiceImpl extends GenericServiceImpl<Horario, Integer> implements HorarioServiceAPI {

	@Autowired
	private HorarioDaoAPI horarioDaoAPI;
	
	@Override
	public CrudRepository<Horario, Integer> getDao() {
		return horarioDaoAPI;
	}

}
