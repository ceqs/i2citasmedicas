package pe.edu.utp.i2.citasmedicas.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.i2.citasmedicas.commons.PasswordValidator;
import pe.edu.utp.i2.citasmedicas.model.Paciente;
import pe.edu.utp.i2.citasmedicas.model.Registro;
import pe.edu.utp.i2.citasmedicas.model.Rol;
import pe.edu.utp.i2.citasmedicas.model.Usuario;
import pe.edu.utp.i2.citasmedicas.service.api.PacienteServiceAPI;
import pe.edu.utp.i2.citasmedicas.service.api.RolServiceAPI;
import pe.edu.utp.i2.citasmedicas.service.api.UsuarioServiceAPI;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin("*")
public class RegistroApiController {

	@Autowired
	private PacienteServiceAPI pacienteServiceAPI;

	@Autowired
	private UsuarioServiceAPI usuarioServiceAPI;

	@Autowired
	private RolServiceAPI rolServiceAPI;

	@PostMapping(value = "/registros")
	public ResponseEntity<Registro> save(@RequestBody Registro registro) {
		if(!PasswordValidator.isValid(registro.getUsuario().getPassword())) {
			throw new RuntimeException("La contraseña debe contener al menos un dígito [0-9], al menos un carácter en minúscula [a-z], al menos un carácter en mayúscula [A-Z], al menos un carácter especial y una longitud al menos 8 caracteres");
		}

		try {
			Usuario utmp = usuarioServiceAPI.get(registro.getUsuario().getUsuario());
			if(utmp == null) {
				Rol rol = rolServiceAPI.get(Rol.PACIENTE);
				registro.getUsuario().setRol(rol);

				BCryptPasswordEncoder passGen = new BCryptPasswordEncoder();
				registro.getUsuario().setPassword(passGen.encode(registro.getUsuario().getPassword()));
				registro.getUsuario().setEnabled(true);

				Usuario usuario = usuarioServiceAPI.save(registro.getUsuario());
				registro.getPaciente().setUsuario(usuario);

				registro.getPaciente().setApePaterno(capitalize(registro.getPaciente().getApePaterno()));
				registro.getPaciente().setApeMaterno(capitalize(registro.getPaciente().getApeMaterno()));
				registro.getPaciente().setNombres(capitalize(registro.getPaciente().getNombres()));

				Paciente paciente = pacienteServiceAPI.save(registro.getPaciente());
				registro.setPaciente(paciente);

				return new ResponseEntity<Registro>(registro, HttpStatus.OK);
			}
			else {
				throw new RuntimeException("Usuario ya existe.");
			}
		}
		catch(RuntimeException e) {
			throw e;
		}
		catch(Exception e) {
			throw new RuntimeException("No se pudo registrar el usuario.");
		}
	}

	public static String capitalize(String str) {
		if(str == null || str.isEmpty()) {
			return str;
		}

		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
}
