package pe.edu.utp.i2.citasmedicas.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.i2.citasmedicas.model.Reserva;
import pe.edu.utp.i2.citasmedicas.service.api.ReporteServiceAPI;
import pe.edu.utp.i2.citasmedicas.service.api.ReservaServiceAPI;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin("*")
public class ReporteApiController {

	@Autowired
	private ReporteServiceAPI reporteServiceAPI;

	@GetMapping(value = "/reportes")
	public List<Reserva> getByFechasEspMed(@RequestParam String start, @RequestParam String end, @RequestParam String cesp, @RequestParam String cmed) {
		System.out.println("entro al metodo");
		return reporteServiceAPI.findReservasByFechasEspMed(start, end, cesp, Integer.parseInt(cmed));
	}
}
