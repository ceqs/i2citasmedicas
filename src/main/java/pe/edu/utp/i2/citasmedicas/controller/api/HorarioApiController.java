package pe.edu.utp.i2.citasmedicas.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.i2.citasmedicas.model.Horario;
import pe.edu.utp.i2.citasmedicas.service.api.HorarioServiceAPI;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin("*")
public class HorarioApiController {

	@Autowired
	private HorarioServiceAPI horarioServiceAPI;

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

}
