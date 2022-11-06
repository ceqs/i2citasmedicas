package pe.edu.utp.i2.citasmedicas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import pe.edu.utp.i2.citasmedicas.commons.GenericServiceImpl;
import pe.edu.utp.i2.citasmedicas.dao.api.GraficoDaoAPI;
import pe.edu.utp.i2.citasmedicas.model.Grafico;
import pe.edu.utp.i2.citasmedicas.model.Reserva;
import pe.edu.utp.i2.citasmedicas.service.api.GraficoServiceAPI;

import java.util.List;

@Service
public class GraficoServiceImpl extends GenericServiceImpl<Reserva, Integer> implements GraficoServiceAPI {

    @Autowired
    private GraficoDaoAPI graficoDaoAPI;

    @Override
    public List<Grafico> findCountReservasByAnio(Integer anio) {
        return graficoDaoAPI.findCountReservasByAnio(anio);
    }

    @Override
    public List<Grafico> findCountReservasByAnioByMed(Integer anio) {
        return graficoDaoAPI.findCountReservasByAnioByMed(anio);
    }

    @Override
    public CrudRepository<Reserva, Integer> getDao() {
        return null;
    }
}
