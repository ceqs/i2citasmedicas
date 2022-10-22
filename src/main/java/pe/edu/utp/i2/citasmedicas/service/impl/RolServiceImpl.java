package pe.edu.utp.i2.citasmedicas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import pe.edu.utp.i2.citasmedicas.commons.GenericServiceImpl;
import pe.edu.utp.i2.citasmedicas.dao.api.RolDaoAPI;
import pe.edu.utp.i2.citasmedicas.model.Rol;
import pe.edu.utp.i2.citasmedicas.service.api.RolServiceAPI;

@Service
public class RolServiceImpl extends GenericServiceImpl<Rol, String> implements RolServiceAPI {

	@Autowired
	private RolDaoAPI rolDaoAPI;
	
	@Override
	public CrudRepository<Rol, String> getDao() {
		return rolDaoAPI;
	}

}
