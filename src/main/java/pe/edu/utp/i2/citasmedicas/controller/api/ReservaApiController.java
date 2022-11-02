package pe.edu.utp.i2.citasmedicas.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.i2.citasmedicas.model.Paciente;
import pe.edu.utp.i2.citasmedicas.model.Medico;
import pe.edu.utp.i2.citasmedicas.model.Reserva;
import pe.edu.utp.i2.citasmedicas.service.api.MedicoServiceAPI;
import pe.edu.utp.i2.citasmedicas.service.api.PacienteServiceAPI;
import pe.edu.utp.i2.citasmedicas.service.api.ReservaServiceAPI;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.Instant;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin("*")
public class ReservaApiController {

	@Autowired
	private ReservaServiceAPI reservaServiceAPI;

	@Autowired
	private PacienteServiceAPI pacienteServiceAPI;

	@Autowired
	private MedicoServiceAPI medicoServiceAPI;

	@GetMapping(value = "/reservas")
	public List<Reserva> getAll() {
		return reservaServiceAPI.getAll();
	}
	
	@GetMapping(value = "/reservas/{id}")
	public Reserva getById(@PathVariable Integer id) {
		return reservaServiceAPI.get(id);
	}

	@PostMapping(value = "/reservas")
	public ResponseEntity<Reserva> save(@RequestBody Reserva reserva) {
		Instant lt = Instant.now();
		reserva.setFhFin(lt);
		reserva.setFhInicio(lt);
		reserva.setTipoCita("PRESENCIAL");
		reserva.setTipoSeguro("EPS");
		Reserva obj = reservaServiceAPI.save(reserva);
		Medico m = medicoServiceAPI.get(reserva.getMedico().getId());
		Paciente p = pacienteServiceAPI.get(reserva.getPaciente().getId());
		try {
			sendEmail(obj.getFechaCita().toString(), obj.getFhInicio().toString(), m.getApellidos(), p.getEmail());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<Reserva>(obj, HttpStatus.OK);
	}

	@RequestMapping(value = "/reservas/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Reserva> delete(@PathVariable Integer id) {
		Reserva reserva = reservaServiceAPI.get(id);
		if (reserva != null) {
			reservaServiceAPI.delete(id);
		} else {
			return new ResponseEntity<Reserva>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Reserva>(reserva, HttpStatus.OK);
	}

	private void sendEmail(String fecha, String horario, String medico, String receptor) {
		System.out.println("Receptor:"+receptor);
		String remitente = "utp.cgt.apps.tcm@gmail.com";

		Properties props = System.getProperties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.user", remitente);
		props.put("mail.smtp.clave", "seaprtlkasnxohsg");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "587");

		try {
			Session session = Session.getDefaultInstance(props);
			MimeMessage message = new MimeMessage(session);


			message.setFrom(new InternetAddress(remitente));
			message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receptor));
			message.setSubject("Su cita ha sido registrada");
			message.setText("Para el " + fecha + " en el horario de : " + horario + ".\n" +
					"Con el medico: " + medico + "\n" +
					"\n" +
					"IMPORTANTE: No olvide llegar 30 minutos antes de su cita.\n");
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", remitente, "seaprtlkasnxohsg");
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		}
		catch (MessagingException me) {
			me.printStackTrace();
		}
	}

}
