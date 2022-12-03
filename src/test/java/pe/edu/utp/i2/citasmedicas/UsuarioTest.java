package pe.edu.utp.i2.citasmedicas;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class UsuarioTest {
	
	@Test
	public void generatePassword() {
		/*
		BCryptPasswordEncoder passGen = new BCryptPasswordEncoder();
		System.out.println(passGen.encode("admin"));
		 */

		//2022-12-05T08:00:00-05:00

		String strDateTime = "2022-12-05T08:00:00-05:00";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
		OffsetDateTime odt = OffsetDateTime.parse(strDateTime, dtf);
		System.out.println(odt);
	}

}
