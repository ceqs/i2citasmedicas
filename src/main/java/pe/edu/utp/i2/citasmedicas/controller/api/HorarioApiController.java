package pe.edu.utp.i2.citasmedicas.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.i2.citasmedicas.model.*;
import pe.edu.utp.i2.citasmedicas.service.api.HorarioServiceAPI;
import pe.edu.utp.i2.citasmedicas.service.api.ReservaServiceAPI;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

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

	@PostMapping(value = "/horarios/group")
	public ResponseEntity<List<Horario>> saveGroup(@RequestBody Programacion programacion) {
		List<Horario> horarios = new ArrayList<>();
		LocalDateTime start = programacion.getStart();
		System.out.println(start);
		LocalDateTime end = programacion.getEnd();
		System.out.println(end);
		LocalDateTime next = start.plus(15, ChronoUnit.MINUTES);
		do {
			Horario horario = new Horario();
			horario.setMedico(programacion.getMedico());
			horario.setFecha(start.toLocalDate());
			horario.setEstado(EstadoCita.DISPONIBLE.toString());
			horario.setHoraInicio(start);
			horario.setHoraFin(next);
			start = next;
			next = start.plus(15, ChronoUnit.MINUTES);

			Horario programado = horarioServiceAPI.searchHorarioByFechas(horario.getMedico().getId(), horario.getHoraInicio(), horario.getHoraFin());
			if(programado == null) {
				horarios.add(horario);
			}
			else {
				System.out.println("Se descarta el horario: " + horario);
			}
			System.out.println(end);
			System.out.println(next);
		} while(end.isAfter(start));

		horarios.stream().forEach((h) -> {
			horarioServiceAPI.save(h);
		});

		return new ResponseEntity<List<Horario>>(horarios, HttpStatus.OK);
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
	public Events searchEventos(@RequestParam Integer medico, @RequestParam String fechaInicio, @RequestParam Optional<String> fechaFin) {
		if(fechaInicio.isEmpty()) return new Events();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		if(fechaFin.isPresent()) {
			return searchHorariosAndEventos(medico, LocalDate.parse(fechaInicio, formatter), LocalDate.parse(fechaFin.get(), formatter));
		}
		else {

			LocalDate localDate = LocalDate.parse(fechaInicio, formatter);
			/*
			LocalDate monday = localDate;

			if(localDate.getDayOfWeek() != DayOfWeek.MONDAY) {
				monday = localDate.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
			}

			LocalDate sunday = monday.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));

			System.out.println(monday + " - " + sunday);

			return searchHorariosAndEventos(medico, monday, sunday);*/
			return searchHorariosAndEventos(medico, localDate, localDate.plusYears(1));
		}
	}

	private Events searchHorariosAndEventos(Integer medico, LocalDate start, LocalDate end) {
		List<Horario> lsHorarios = horarioServiceAPI.searchHorarios(medico, start, end);
		List<Reserva> lsReserva = reservaServiceAPI.searchReservasPorFechas(medico, start, end);

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Map<LocalDateTime, Event> hevents = new HashMap<>();
		for(Horario horario: lsHorarios) {
			Event event = new Event();
			event.setStart(horario.getHoraInicio());
			event.setEnd(horario.getHoraFin());
			event.getExtendedProps().setFecha(horario.getHoraInicio().format(dateFormatter));
			event.getExtendedProps().setStartStr(horario.getHoraInicio().format(dateTimeFormatter));
			event.getExtendedProps().setEndStr(horario.getHoraFin().format(dateTimeFormatter));
			event.getExtendedProps().setIdHorario(horario.getId());

			// Si el rol es paciente entonces el horario es disponible, sino el horario es el lo que indica el estado.
			if(getRole().equals(Rol.ADMIN)) {
				if(horario.getEstado().equals(EstadoCita.DISPONIBLE.toString())) {
					event.setBackgroundColor(EstadoCita.DISPONIBLE_COLOR.toString());
				}
				else {
					event.setBackgroundColor(EstadoCita.LIBERADO_COLOR.toString());
				}
				event.setTitle(horario.getEstado());
			}
			else {
				event.setTitle(EstadoCita.DISPONIBLE.toString());
				event.setBackgroundColor(EstadoCita.DISPONIBLE_COLOR.toString());
			}
			hevents.put(horario.getHoraInicio(), event);
		}

		for(Reserva reserva: lsReserva) {
			Event event = new Event();
			if(hevents.containsKey(reserva.getFhInicio())) {
				event = hevents.get(reserva.getFhInicio());

			}
			else {
				event.setStart(reserva.getFhInicio());
				event.setEnd(reserva.getFhFin());
				event.getExtendedProps().setFecha(reserva.getFhInicio().format(dateFormatter));
				event.getExtendedProps().setStartStr(reserva.getFhInicio().format(dateTimeFormatter));
				event.getExtendedProps().setEndStr(reserva.getFhFin().format(dateTimeFormatter));
			}

			if(getRole().equals(Rol.ADMIN)) {
				if(reserva.getEstado().equals(EstadoCita.RESERVADO.toString())) {
					event.setBackgroundColor(EstadoCita.RESERVADO_COLOR.toString());
				}
				if(reserva.getEstado().equals(EstadoCita.ATENDIDO.toString())) {
					event.setBackgroundColor(EstadoCita.ATENDIDO_COLOR.toString());
				}
				if(reserva.getEstado().equals(EstadoCita.INASISTENCIA.toString())) {
					event.setBackgroundColor(EstadoCita.INASISTENCIA_COLOR.toString());
				}
				event.setTitle(reserva.getEstado());
			}
			else {
				event.setBackgroundColor(EstadoCita.RESERVADO_COLOR.toString());
				event.setTitle(EstadoCita.RESERVADO.toString());
			}

			event.getExtendedProps().setIdCita(reserva.getId());
			hevents.put(reserva.getFhInicio(), event);
		}

		List<Event> events = new ArrayList<>(hevents.values());
		Events obj = new Events();
		obj.setEvents(events);

		return obj;
	}

	private String getRole() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			List<GrantedAuthority> authorities = (List<GrantedAuthority>) auth.getAuthorities();
			return String.valueOf(authorities.get(0));
		}
		return null;
	}
}
