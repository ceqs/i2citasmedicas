package pe.edu.utp.i2.citasmedicas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import pe.edu.utp.i2.citasmedicas.commons.GenericServiceImpl;
import pe.edu.utp.i2.citasmedicas.dao.api.HorarioDaoAPI;
import pe.edu.utp.i2.citasmedicas.model.Horario;
import pe.edu.utp.i2.citasmedicas.service.api.HorarioServiceAPI;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HorarioServiceImpl extends GenericServiceImpl<Horario, Integer> implements HorarioServiceAPI {

	@Autowired
	private HorarioDaoAPI horarioDaoAPI;
	
	@Override
	public CrudRepository<Horario, Integer> getDao() {
		return horarioDaoAPI;
	}

	@Override
	public List<Horario> searchHorarios(Integer idMedico, LocalDate start, LocalDate end) {
		return horarioDaoAPI.findByMedicoIdAndFechaBetween(idMedico, start, end);
	}

	@Override
	public Horario searchHorarioByFechas(Integer idMedico, LocalDateTime horaInicio, LocalDateTime horaFin) {
		return horarioDaoAPI.findByMedicoIdAndHoraInicioAndHoraFin(idMedico, horaInicio, horaFin);
	}
}
