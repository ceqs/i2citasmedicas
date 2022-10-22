package pe.edu.utp.i2.citasmedicas.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.i2.citasmedicas.model.Especialidad;
import pe.edu.utp.i2.citasmedicas.service.api.EspecialidadServiceAPI;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin("*")
public class EspecialidadApiController {

	@Autowired
	private EspecialidadServiceAPI especialidadServiceAPI;

	@GetMapping(value = "/especialidades")
	public List<Especialidad> getAll() {
		return especialidadServiceAPI.getAll();
	}
	
	@GetMapping(value = "/especialidades/{id}")
	public Especialidad getById(@PathVariable Integer id) {
		return especialidadServiceAPI.get(id);
	}

	@PostMapping(value = "/especialidades")
	public ResponseEntity<Especialidad> save(@RequestBody Especialidad especialidad) {
		Especialidad obj = especialidadServiceAPI.save(especialidad);
		return new ResponseEntity<Especialidad>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/especialidades/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Especialidad> delete(@PathVariable Integer id) {
		Especialidad especialidad = especialidadServiceAPI.get(id);
		if (especialidad != null) {
			especialidadServiceAPI.delete(id);
		} else {
			return new ResponseEntity<Especialidad>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Especialidad>(especialidad, HttpStatus.OK);
	}

}
