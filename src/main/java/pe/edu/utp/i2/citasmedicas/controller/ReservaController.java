package pe.edu.utp.i2.citasmedicas.controller;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pe.edu.utp.i2.citasmedicas.service.api.EspecialidadServiceAPI;
import pe.edu.utp.i2.citasmedicas.service.api.PacienteServiceAPI;
import pe.edu.utp.i2.citasmedicas.service.api.MedicoServiceAPI;
import pe.edu.utp.i2.citasmedicas.service.api.HorarioServiceAPI;

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
		model.addAttribute("pacientes", pacienteServiceAPI.getAll());
		model.addAttribute("especialidades", especialidadServiceAPI.getAll());
		model.addAttribute("medicos", medicoServiceAPI.getAll());
		model.addAttribute("horarios", horarioServiceAPI.getAll());
		return "reserva";
	}


}
