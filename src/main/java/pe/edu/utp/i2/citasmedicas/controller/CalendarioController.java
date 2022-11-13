package pe.edu.utp.i2.citasmedicas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.edu.utp.i2.citasmedicas.service.api.MedicoServiceAPI;

@Controller
public class CalendarioController {

	@Autowired
	private MedicoServiceAPI medicoServiceAPI;

	@RequestMapping("/calendario")
	public String showHistorial(Model model) {
		model.addAttribute("medicos", medicoServiceAPI.getAll());
		return "calendario";
	}
}
