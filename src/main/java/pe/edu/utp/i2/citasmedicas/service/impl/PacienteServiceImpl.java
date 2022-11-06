package pe.edu.utp.i2.citasmedicas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import pe.edu.utp.i2.citasmedicas.commons.GenericServiceImpl;
import pe.edu.utp.i2.citasmedicas.dao.api.PacienteDaoAPI;
import pe.edu.utp.i2.citasmedicas.model.Paciente;
import pe.edu.utp.i2.citasmedicas.service.api.PacienteServiceAPI;

@Service
public class PacienteServiceImpl extends GenericServiceImpl<Paciente, Integer> implements PacienteServiceAPI {

	@Autowired
	private PacienteDaoAPI pacienteDaoAPI;
	
	@Override
	public CrudRepository<Paciente, Integer> getDao() {
		return pacienteDaoAPI;
	}


	@Override
	public Paciente findByUsuario(String usuario) {
		return pacienteDaoAPI.findByUsuarioUsuario(usuario);
	}
}
