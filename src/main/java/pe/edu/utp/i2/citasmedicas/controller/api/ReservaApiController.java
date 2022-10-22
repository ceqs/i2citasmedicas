package pe.edu.utp.i2.citasmedicas.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.i2.citasmedicas.model.Reserva;
import pe.edu.utp.i2.citasmedicas.service.api.ReservaServiceAPI;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin("*")
public class ReservaApiController {

	@Autowired
	private ReservaServiceAPI reservaServiceAPI;

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
		Reserva obj = reservaServiceAPI.save(reserva);
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

}
