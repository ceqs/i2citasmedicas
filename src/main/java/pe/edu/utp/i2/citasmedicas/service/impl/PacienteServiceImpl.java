package pe.edu.utp.i2.citasmedicas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import pe.edu.utp.i2.citasmedicas.commons.GenericServiceImpl;
import pe.edu.utp.i2.citasmedicas.dao.api.PacienteDaoAPI;
import pe.edu.utp.i2.citasmedicas.model.Paciente;
import pe.edu.utp.i2.citasmedicas.service.api.PacienteServiceAPI;

@Service
public class PacienteServiceImpl extends GenericServiceImpl<Paciente, Long> implements PacienteServiceAPI {

	@Autowired
	private PacienteDaoAPI pacienteDaoAPI;
	
	@Override
	public CrudRepository<Paciente, Long> getDao() {
		return pacienteDaoAPI;
	}

}
