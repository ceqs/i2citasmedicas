package pe.edu.utp.i2.citasmedicas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistroController {
	
	@RequestMapping("/registro")
	public String index(Model model) {
		return "registro";
	}

}
