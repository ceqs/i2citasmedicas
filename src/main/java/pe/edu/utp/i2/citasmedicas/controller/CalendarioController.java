package pe.edu.utp.i2.citasmedicas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.edu.utp.i2.citasmedicas.service.api.MedicoServiceAPI;
import pe.edu.utp.i2.citasmedicas.model.Medico;

import java.util.List;

@Controller
public class CalendarioController {

	@Autowired
	private MedicoServiceAPI medicoServiceAPI;

	@RequestMapping("/calendario")
	public String showHistorial(Model model) {
		List<Medico> medicos =  medicoServiceAPI.getAll();
		medicos.stream().forEach((h) -> {
			h.setNombres(h.getNombres() + " " + h.getApellidos());
		});
		model.addAttribute("medicos", medicos);
		return "calendario";
	}
}
