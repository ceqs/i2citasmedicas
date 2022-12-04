package pe.edu.utp.i2.citasmedicas.controller;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.edu.utp.i2.citasmedicas.model.Paciente;
import pe.edu.utp.i2.citasmedicas.service.api.EspecialidadServiceAPI;
import pe.edu.utp.i2.citasmedicas.service.api.PacienteServiceAPI;
import pe.edu.utp.i2.citasmedicas.service.api.MedicoServiceAPI;
import pe.edu.utp.i2.citasmedicas.service.api.HorarioServiceAPI;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;

@Controller
public class ReservaController {

	@Autowired
	private PacienteServiceAPI pacienteServiceAPI;

	@Autowired
	private EspecialidadServiceAPI especialidadServiceAPI;

	@Autowired
	private MedicoServiceAPI medicoServiceAPI;

	@Autowired
	private HorarioServiceAPI horarioServiceAPI;

	@RequestMapping("/reserva")
	public String index(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			Paciente paciente = pacienteServiceAPI.findByUsuario(auth.getName());
			model.addAttribute("paciente", paciente.getNombres() + " " + paciente.getApePaterno() + " " + paciente.getApeMaterno());
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		model.addAttribute("today", LocalDate.now().format(formatter));
		List<Paciente> pacientes = pacienteServiceAPI.getAll();
		pacientes.stream().forEach((h) -> {
			h.setNombres(h.getNombres() + " " + h.getApePaterno() + " " + h.getApeMaterno());
		});
		model.addAttribute("pacientes", pacientes);
		model.addAttribute("especialidades", especialidadServiceAPI.getAll());
		model.addAttribute("medicos", medicoServiceAPI.getAll());
		model.addAttribute("horarios", horarioServiceAPI.getAll());
		return "reserva";
	}
}
