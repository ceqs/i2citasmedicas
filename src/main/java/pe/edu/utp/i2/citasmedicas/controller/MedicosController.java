package pe.edu.utp.i2.citasmedicas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.edu.utp.i2.citasmedicas.service.api.EspecialidadServiceAPI;

@Controller
public class MedicosController {

	@Autowired
	private EspecialidadServiceAPI especialidadServiceAPI;
	
	@RequestMapping("/medicos")
	public String index(Model model) {
		model.addAttribute("especialidades", especialidadServiceAPI.getAll());
		return "medicos";
	}

}
