package pe.edu.utp.i2.citasmedicas.dao.api;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pe.edu.utp.i2.citasmedicas.model.Grafico;
import pe.edu.utp.i2.citasmedicas.model.Reserva;

import java.util.List;

public interface GraficoDaoAPI extends CrudRepository<Grafico, Integer> {

    /*
    select month(fecha_cita) mes, count(*) total,
    right(substr('ENEFEBMARABRMAYJUNJULAGOSETOCTNOVDIC',1, month(fecha_cita) * 3),3) nameM
    from reserva where year(fecha_cita)=2022 group by month(fecha_cita) order by 1;

    @Query(value = "select month(fecha_cita) mes, count(*) total from reserva where year(fecha_cita) = :anio group by month(fecha_cita) order by 1",
            nativeQuery = true)

    */

    //MTC(04/11/2022)
    @Query(value = "select \n" +
            " row_number() over(order by fecha_cita) id,\n" +
            " month(fecha_cita) mes, \n" +
            " count(*) total,\n" +
            " right(substr('ENEFEBMARABRMAYJUNJULAGOSETOCTNOVDIC',1, month(fecha_cita) * 3),3) nameM\n" +
            " from reserva where year(fecha_cita) = :anio group by month(fecha_cita) order by 1",
            nativeQuery = true)
    List<Grafico> findCountReservasByAnio(@Param("anio") Integer anio);

}
