package pe.edu.utp.i2.citasmedicas.service.api;

import pe.edu.utp.i2.citasmedicas.commons.GenericServiceAPI;
import pe.edu.utp.i2.citasmedicas.model.Reserva;

import java.util.List;

public interface ReporteServiceAPI extends GenericServiceAPI<Reserva, Integer> {

    public List<Reserva> findReservasByFechasEspMed(String start, String end, String cesp, Integer cmed);
}
