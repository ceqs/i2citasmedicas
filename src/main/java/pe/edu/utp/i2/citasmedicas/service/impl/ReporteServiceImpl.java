package pe.edu.utp.i2.citasmedicas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import pe.edu.utp.i2.citasmedicas.commons.GenericServiceImpl;
import pe.edu.utp.i2.citasmedicas.dao.api.ReservaDaoAPI;
import pe.edu.utp.i2.citasmedicas.model.Reserva;
import pe.edu.utp.i2.citasmedicas.service.api.ReporteServiceAPI;
import pe.edu.utp.i2.citasmedicas.service.api.ReservaServiceAPI;

import java.util.List;

@Service
public class ReporteServiceImpl extends GenericServiceImpl<Reserva, Integer> implements ReporteServiceAPI {

	@Autowired
	private ReservaDaoAPI reservaDaoAPI;
	
	@Override
	public CrudRepository<Reserva, Integer> getDao() {
		return reservaDaoAPI;
	}

	@Override
	public List<Reserva> findReservasByFechasEspMed(String start, String end, String cesp, Integer cmed) {
		return reservaDaoAPI.findReservasByFechasEspMed(start, end, cesp, cmed);
	}
}
