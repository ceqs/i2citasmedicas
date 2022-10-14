package pe.edu.utp.i2.citasmedicas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pe.edu.utp.i2.citasmedicas.model.Paciente;
import pe.edu.utp.i2.citasmedicas.service.api.PacienteServiceAPI;

@Controller
@RequestMapping("/paciente")
public class PacienteController {

	@Autowired
	private PacienteServiceAPI pacienteServiceAPI;

	@RequestMapping("/grid")
	public String index(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			System.out.println(auth.getAuthorities());
			System.out.println(auth.getCredentials());
			System.out.println(auth.getDetails());
			System.out.println(auth.getPrincipal());
			System.out.println(auth.getName());
		}
		model.addAttribute("list", pacienteServiceAPI.getAll());
		return "gridpaciente";
	}

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("persona", new Paciente());
		return "save";
	}

	@GetMapping("/save/{id}")
	public String showSave(@PathVariable("id") Long id, Model model) {
		if (id != null && id != 0) {
			model.addAttribute("persona", pacienteServiceAPI.get(id));
		} else {
			model.addAttribute("persona", new Paciente());
		}
		return "save";
	}

	@PostMapping("/save")
	public String save(Paciente persona, Model model) {
		pacienteServiceAPI.save(persona);
		return "redirect:/home/";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id, Model model) {
		pacienteServiceAPI.delete(id);

		return "redirect:/home/";
	}

}
