package pe.edu.utp.i2.citasmedicas.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.i2.citasmedicas.model.Especialidad;
import pe.edu.utp.i2.citasmedicas.model.Usuario;
import pe.edu.utp.i2.citasmedicas.service.api.UsuarioServiceAPI;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin("*")
public class UsuarioApiController {

	@Autowired
	private UsuarioServiceAPI usuarioServiceAPI;

	@GetMapping(value = "/usuarios")
	public List<Usuario> getAll() {
		return usuarioServiceAPI.getAll();
	}
	
	@GetMapping(value = "/usuarios/{id}")
	public Usuario getById(@PathVariable String username) {
		return usuarioServiceAPI.get(username);
	}

	@PostMapping(value = "/usuarios")
	public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
		Usuario obj = usuarioServiceAPI.save(usuario);
		return new ResponseEntity<Usuario>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/usuarios/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Usuario> delete(@PathVariable String username) {
		Usuario usuario = usuarioServiceAPI.get(username);
		if (usuario != null) {
			usuarioServiceAPI.delete(username);
		} else {
			return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}

}
