package pe.edu.utp.i2.citasmedicas.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.i2.citasmedicas.model.Event;
import pe.edu.utp.i2.citasmedicas.model.Events;
import pe.edu.utp.i2.citasmedicas.model.Horario;
import pe.edu.utp.i2.citasmedicas.model.Reserva;
import pe.edu.utp.i2.citasmedicas.service.api.HorarioServiceAPI;
import pe.edu.utp.i2.citasmedicas.service.api.ReservaServiceAPI;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin("*")
public class HorarioApiController {

	@Autowired
	private HorarioServiceAPI horarioServiceAPI;

	@Autowired
	private ReservaServiceAPI reservaServiceAPI;

	@GetMapping(value = "/horarios")
	public List<Horario> getAll() {
		return horarioServiceAPI.getAll();
	}
	
	@GetMapping(value = "/horarios/{id}")
	public Horario getById(@PathVariable Integer id) {
		return horarioServiceAPI.get(id);
	}

	@PostMapping(value = "/horarios")
	public ResponseEntity<Horario> save(@RequestBody Horario horario) {
		Horario obj = horarioServiceAPI.save(horario);
		return new ResponseEntity<Horario>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/horarios/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Horario> delete(@PathVariable Integer id) {
		Horario horario = horarioServiceAPI.get(id);
		if (horario != null) {
			horarioServiceAPI.delete(id);
		} else {
			return new ResponseEntity<Horario>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Horario>(horario, HttpStatus.OK);
	}

	@GetMapping(value = "/horarios/search")
	public Events getById(@RequestParam Integer medico, @RequestParam String fecha) {
		if(fecha.isEmpty()) return new Events();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(fecha, formatter);

		LocalDate monday = localDate;
		if(localDate.getDayOfWeek() != DayOfWeek.MONDAY) {
			monday = localDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
		}

		LocalDate sunday = monday.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
		System.out.println(monday);
		System.out.println(sunday);
		List<Horario> lsHorarios = horarioServiceAPI.searchHorarios(medico, monday, sunday);
		List<Reserva> lsReserva = reservaServiceAPI.searchReservasPorFechas(medico, monday, sunday);

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Map<LocalDateTime, Event> hevents = new HashMap<>();
		for(Horario horario: lsHorarios) {
			Event event = new Event();
			event.setStart(horario.getHoraInicio());
			event.getExtendedProps().setStartStr(horario.getHoraInicio().format(dateTimeFormatter));
			event.setEnd(horario.getHoraFin());
			event.getExtendedProps().setEndStr(horario.getHoraFin().format(dateTimeFormatter));
			event.setTitle("DISPONIBLE");
			event.setBackgroundColor("#32cd32");
			hevents.put(horario.getHoraInicio(), event);
		}

		for(Reserva reserva: lsReserva) {
			if(hevents.containsKey(reserva.getFhInicio())) {
				Event e = hevents.get(reserva.getFhInicio());
				e.setBackgroundColor("#cd5c5c");
				e.setTitle("RESERVADO");
				hevents.put(reserva.getFhInicio(), e);
			}
			else {
				Event event = new Event();
				event.setStart(reserva.getFhInicio());
				event.getExtendedProps().setStartStr(reserva.getFhInicio().format(dateTimeFormatter));
				event.setEnd(reserva.getFhFin());
				event.getExtendedProps().setEndStr(reserva.getFhFin().format(dateTimeFormatter));
				event.setTitle("RESERVADO");
				event.setBackgroundColor("#cd5c5c");
				hevents.put(reserva.getFhInicio(), event);
			}
		}
		List<Event> events = new ArrayList<>(hevents.values());
		Events obj = new Events();
		obj.setEvents(events);

		return obj;
	}
}
