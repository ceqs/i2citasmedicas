package pe.edu.utp.i2.citasmedicas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
		Rol rol = rolServiceAPI.get(Rol.PACIENTE);
		registro.getUsuario().setRol(rol);
		BCryptPasswordEncoder passGen = new BCryptPasswordEncoder();
		registro.getUsuario().setPassword(passGen.encode(registro.getUsuario().getPassword()));
		Usuario usuario = usuarioServiceAPI.save(registro.getUsuario());
		registro.getPaciente().setUsuario(usuario);
		pacienteServiceAPI.save(registro.getPaciente());
		return "redirect:/home/";
	}
}
