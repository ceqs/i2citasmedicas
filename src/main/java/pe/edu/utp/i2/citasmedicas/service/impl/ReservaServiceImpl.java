package pe.edu.utp.i2.citasmedicas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import pe.edu.utp.i2.citasmedicas.commons.GenericServiceImpl;
import pe.edu.utp.i2.citasmedicas.dao.api.ReservaDaoAPI;
import pe.edu.utp.i2.citasmedicas.model.Reserva;
import pe.edu.utp.i2.citasmedicas.service.api.ReservaServiceAPI;

@Service
public class ReservaServiceImpl extends GenericServiceImpl<Reserva, Integer> implements ReservaServiceAPI {

	@Autowired
	private ReservaDaoAPI reservaDaoAPI;
	
	@Override
	public CrudRepository<Reserva, Integer> getDao() {
		return reservaDaoAPI;
	}

}
