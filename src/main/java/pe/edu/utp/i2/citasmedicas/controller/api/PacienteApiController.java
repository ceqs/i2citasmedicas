package pe.edu.utp.i2.citasmedicas.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.i2.citasmedicas.model.Paciente;
import pe.edu.utp.i2.citasmedicas.service.api.PacienteServiceAPI;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin("*")
public class PacienteApiController {

	@Autowired
	private PacienteServiceAPI pacienteServiceAPI;

	@GetMapping(value = "/pacientes")
	public List<Paciente> getAll() {
		return pacienteServiceAPI.getAll();
	}
	
	@GetMapping(value = "/pacientes/{id}")
	public Paciente getById(@PathVariable Integer id) {
		return pacienteServiceAPI.get(id);
	}

	@PostMapping(value = "/pacientes")
	public ResponseEntity<Paciente> save(@RequestBody Paciente paciente) {
		Paciente obj = pacienteServiceAPI.save(paciente);
		return new ResponseEntity<Paciente>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/pacientes/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Paciente> delete(@PathVariable Integer id) {
		Paciente paciente = pacienteServiceAPI.get(id);
		if (paciente != null) {
			pacienteServiceAPI.delete(id);
		} else {
			return new ResponseEntity<Paciente>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Paciente>(paciente, HttpStatus.OK);
	}

}
