package pe.edu.utp.i2.citasmedicas.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.i2.citasmedicas.model.Medico;
import pe.edu.utp.i2.citasmedicas.service.api.MedicoServiceAPI;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin("*")
public class MedicoApiController {

	@Autowired
	private MedicoServiceAPI medicoServiceAPI;

	@GetMapping(value = "/medicos")
	public List<Medico> getAll() {
		return medicoServiceAPI.getAll();
	}
	
	@GetMapping(value = "/medicos/{id}")
	public Medico getById(@PathVariable Integer id) {
		return medicoServiceAPI.get(id);
	}

	@PostMapping(value = "/medicos")
	public ResponseEntity<Medico> save(@RequestBody Medico medico) {
		Medico obj = medicoServiceAPI.save(medico);
		return new ResponseEntity<Medico>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/medicos/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Medico> delete(@PathVariable Integer id) {
		Medico medico = medicoServiceAPI.get(id);
		if (medico != null) {
			medicoServiceAPI.delete(id);
		} else {
			return new ResponseEntity<Medico>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Medico>(medico, HttpStatus.OK);
	}

	@GetMapping(value = "/medicos/search")
	public List<Medico> serachByEspecialidad(@RequestParam Integer especialidad) {
		return medicoServiceAPI.searchByEspecialidad(especialidad);
	}
}
