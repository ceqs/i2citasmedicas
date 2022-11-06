package pe.edu.utp.i2.citasmedicas.service.api;

import pe.edu.utp.i2.citasmedicas.commons.GenericServiceAPI;
import pe.edu.utp.i2.citasmedicas.model.Grafico;
import pe.edu.utp.i2.citasmedicas.model.Reserva;

import java.util.List;

public interface GraficoServiceAPI extends GenericServiceAPI<Reserva, Integer> {
    public List<Grafico> findCountReservasByAnio(Integer anio);

    public List<Grafico> findCountReservasByAnioByMed(Integer anio);
}
