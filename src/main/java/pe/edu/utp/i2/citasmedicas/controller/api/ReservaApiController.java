package pe.edu.utp.i2.citasmedicas.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.i2.citasmedicas.model.Medico;
import pe.edu.utp.i2.citasmedicas.model.Paciente;
import pe.edu.utp.i2.citasmedicas.model.Reserva;
import pe.edu.utp.i2.citasmedicas.model.Horario;
import pe.edu.utp.i2.citasmedicas.service.api.HorarioServiceAPI;
import pe.edu.utp.i2.citasmedicas.service.api.MedicoServiceAPI;
import pe.edu.utp.i2.citasmedicas.service.api.PacienteServiceAPI;
import pe.edu.utp.i2.citasmedicas.service.api.ReservaServiceAPI;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin("*")
public class ReservaApiController {

	@Autowired
	private ReservaServiceAPI reservaServiceAPI;

	@Autowired
	private PacienteServiceAPI pacienteServiceAPI;

	@Autowired
	private MedicoServiceAPI medicoServiceAPI;

	@Autowired
	private HorarioServiceAPI horarioServiceAPI;

	@GetMapping(value = "/reservas")
	public List<Reserva> getAll() {
		return reservaServiceAPI.getAll();
	}
	
	@GetMapping(value = "/reservas/{id}")
	public Reserva getById(@PathVariable Integer id) {
		return reservaServiceAPI.get(id);
	}

	@PostMapping(value = "/reservas")
	public ResponseEntity<Reserva> save(@RequestBody Reserva reserva) {
		Reserva obj = reservaServiceAPI.save(reserva);
		Medico m = medicoServiceAPI.get(reserva.getMedico().getId());
		Paciente p = pacienteServiceAPI.get(reserva.getPaciente().getId());
		reservaServiceAPI.sendEmail(obj.getFechaCita().toString(), obj.getFhInicio().toString(), m.getApellidos(), p.getEmail());

		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@PutMapping(value = "/reservas")
	public ResponseEntity<Reserva> update(@RequestBody Reserva reserva) {
		Reserva reservaTmp = reservaServiceAPI.get(reserva.getId());
		if(reservaTmp != null) {
			reservaTmp.setEstado(reserva.getEstado());
			reservaTmp = reservaServiceAPI.save(reservaTmp);
		}
		return new ResponseEntity<>(reservaTmp, HttpStatus.OK);
	}

	@DeleteMapping(value = "/reservas/{id}")
	public ResponseEntity<Reserva> delete(@PathVariable Integer id) {
		Reserva reserva = reservaServiceAPI.get(id);
		if (reserva != null) {
			Horario horario = horarioServiceAPI.searchHorarioByFechas(reserva.getMedico().getId(), reserva.getFhInicio(), reserva.getFhFin());
			horario.setEstado("LIBERADO");
			horarioServiceAPI.save(horario);
			System.out.println(horario);
			reservaServiceAPI.delete(id);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(reserva, HttpStatus.OK);
	}
}
