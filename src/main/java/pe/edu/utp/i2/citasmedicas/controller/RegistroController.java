package pe.edu.utp.i2.citasmedicas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.edu.utp.i2.citasmedicas.commons.PasswordValidator;
import pe.edu.utp.i2.citasmedicas.model.Paciente;
import pe.edu.utp.i2.citasmedicas.model.Registro;
import pe.edu.utp.i2.citasmedicas.model.Rol;
import pe.edu.utp.i2.citasmedicas.model.Usuario;
import pe.edu.utp.i2.citasmedicas.service.api.PacienteServiceAPI;
import pe.edu.utp.i2.citasmedicas.service.api.UsuarioServiceAPI;
import pe.edu.utp.i2.citasmedicas.service.api.RolServiceAPI;

@Controller
public class RegistroController {

	@Autowired
	private PacienteServiceAPI pacienteServiceAPI;

	@Autowired
	private UsuarioServiceAPI usuarioServiceAPI;

	@Autowired
	private RolServiceAPI rolServiceAPI;

	@RequestMapping("/registro")
	public String index(Model model) {
		Registro registro = new Registro();
		registro.setPaciente(new Paciente());
		registro.setUsuario(new Usuario());
		model.addAttribute("registro", registro);
		return "registro";
	}

	@PostMapping("/registro/guardar")
	public String save(Registro registro, Model model) {
		try {
			Usuario utmp = usuarioServiceAPI.get(registro.getUsuario().getUsuario());
			if(utmp == null) {
				if(!PasswordValidator.isValid(registro.getUsuario().getPassword())) {
					throw new RuntimeException("La contraseña debe contener al menos un dígito [0-9], al menos un carácter en minúscula [a-z], al menos un carácter en mayúscula [A-Z], al menos un carácter especial y una longitud al menos 8 caracteres");
				}
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
				pacienteServiceAPI.save(registro.getPaciente());
				registro.setPaciente(new Paciente());
				registro.setUsuario(new Usuario());
				model.addAttribute("message", "OK");
			}
			else {
				throw new RuntimeException("Usuario ya existe.");
			}
		}
		catch(RuntimeException e) {
			model.addAttribute("error", e.getMessage());
		}
		catch(Exception e) {
			model.addAttribute("error", "No se pudo registrar el usuario.");
		}
		return "registro";
	}

	public static String capitalize(String str) {
		if(str == null || str.isEmpty()) {
			return str;
		}

		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
}
