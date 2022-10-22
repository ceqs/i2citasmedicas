package pe.edu.utp.i2.citasmedicas.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.i2.citasmedicas.model.Rol;
import pe.edu.utp.i2.citasmedicas.service.api.RolServiceAPI;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin("*")
public class RolApiController {

	@Autowired
	private RolServiceAPI rolServiceAPI;

	@GetMapping(value = "/roles")
	public List<Rol> getAll() {
		return rolServiceAPI.getAll();
	}
	
	@GetMapping(value = "/roles/{id}")
	public Rol getById(@PathVariable String rolename) {
		return rolServiceAPI.get(rolename);
	}

	@PostMapping(value = "/roles")
	public ResponseEntity<Rol> save(@RequestBody Rol rol) {
		Rol obj = rolServiceAPI.save(rol);
		return new ResponseEntity<Rol>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/roles/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Rol> delete(@PathVariable String rolename) {
		Rol rol = rolServiceAPI.get(rolename);
		if (rol != null) {
			rolServiceAPI.delete(rolename);
		} else {
			return new ResponseEntity<Rol>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Rol>(rol, HttpStatus.OK);
	}

}
