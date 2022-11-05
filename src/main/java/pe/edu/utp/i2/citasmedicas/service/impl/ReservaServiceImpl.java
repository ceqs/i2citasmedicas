package pe.edu.utp.i2.citasmedicas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pe.edu.utp.i2.citasmedicas.commons.GenericServiceImpl;
import pe.edu.utp.i2.citasmedicas.dao.api.ReservaDaoAPI;
import pe.edu.utp.i2.citasmedicas.model.Reserva;
import pe.edu.utp.i2.citasmedicas.service.api.ReservaServiceAPI;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class ReservaServiceImpl extends GenericServiceImpl<Reserva, Integer> implements ReservaServiceAPI {

	@Autowired
	private ReservaDaoAPI reservaDaoAPI;
	
	@Override
	public CrudRepository<Reserva, Integer> getDao() {
		return reservaDaoAPI;
	}

	@Async
	public void sendEmail(String fecha, String horario, String medico, String receptor) {
		try {
			System.out.println("Receptor:"+receptor);
			String remitente = "utp.cgt.apps.tcm@gmail.com";

			Properties props = System.getProperties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.user", remitente);
			props.put("mail.smtp.clave", "seaprtlkasnxohsg");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", "587");

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
