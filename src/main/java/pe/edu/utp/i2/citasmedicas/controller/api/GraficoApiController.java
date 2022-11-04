package pe.edu.utp.i2.citasmedicas.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.i2.citasmedicas.model.Grafico;
import pe.edu.utp.i2.citasmedicas.model.Reserva;
import pe.edu.utp.i2.citasmedicas.service.api.GraficoServiceAPI;

import java.util.List;

@RestController
@RequestMapping(value = "/v2")
@CrossOrigin("*")
public class GraficoApiController {

    @Autowired
    private GraficoServiceAPI graficoServiceAPI;

    @GetMapping(value = "/graficos")
    public List<Grafico> getByCountAnio(@RequestParam String anio) {
        System.out.println("entro al metodo");
        return graficoServiceAPI.findCountReservasByAnio(Integer.parseInt(anio));
    }

}
