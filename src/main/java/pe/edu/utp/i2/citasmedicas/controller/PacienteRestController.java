package pe.edu.utp.i2.citasmedicas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.utp.i2.citasmedicas.model.Paciente;
import pe.edu.utp.i2.citasmedicas.service.api.PacienteServiceAPI;

@RestController
@RequestMapping(value = "/api/v1/")
@CrossOrigin("*")
public class PacienteRestController {

	@Autowired
	private PacienteServiceAPI pacienteServiceAPI;

	@GetMapping(value = "/all")
	public List<Paciente> getAll() {
		return pacienteServiceAPI.getAll();
	}
	
	@GetMapping(value = "/find/{id}")
	public Paciente find(@PathVariable Long id) {
		return pacienteServiceAPI.get(id);
	}

	@PostMapping(value = "/save")
	public ResponseEntity<Paciente> save(@RequestBody Paciente persona) {
		Paciente obj = pacienteServiceAPI.save(persona);
		return new ResponseEntity<Paciente>(obj, HttpStatus.OK);
	}

	@GetMapping(value = "/delete/{id}")
	public ResponseEntity<Paciente> delete(@PathVariable Long id) {
		Paciente persona = pacienteServiceAPI.get(id);
		if (persona != null) {
			pacienteServiceAPI.delete(id);
		} else {
			return new ResponseEntity<Paciente>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Paciente>(persona, HttpStatus.OK);
	}

}
