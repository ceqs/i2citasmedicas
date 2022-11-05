package pe.edu.utp.i2.citasmedicas.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.i2.citasmedicas.model.Medico;
import pe.edu.utp.i2.citasmedicas.model.Paciente;
import pe.edu.utp.i2.citasmedicas.model.Reserva;
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
		Instant lt = Instant.now();
		reserva.setFhFin(lt);
		reserva.setFhInicio(lt);
		reserva.setTipoCita("PRESENCIAL");
		reserva.setTipoSeguro("EPS");
		Reserva obj = reservaServiceAPI.save(reserva);
		Medico m = medicoServiceAPI.get(reserva.getMedico().getId());
		Paciente p = pacienteServiceAPI.get(reserva.getPaciente().getId());
		reservaServiceAPI.sendEmail(obj.getFechaCita().toString(), obj.getFhInicio().toString(), m.getApellidos(), p.getEmail());

		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/reservas/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Reserva> delete(@PathVariable Integer id) {
		Reserva reserva = reservaServiceAPI.get(id);
		if (reserva != null) {
			reservaServiceAPI.delete(id);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(reserva, HttpStatus.OK);
	}
}
