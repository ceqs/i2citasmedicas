package pe.edu.utp.i2.citasmedicas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.edu.utp.i2.citasmedicas.service.api.RolServiceAPI;

@Controller
public class UsuariosController {

	@Autowired
	private RolServiceAPI rolServiceAPI;
	@RequestMapping("/usuarios")
	public String index(Model model) {
		model.addAttribute("roles", rolServiceAPI.getAll());
		return "usuarios";
	}

}
