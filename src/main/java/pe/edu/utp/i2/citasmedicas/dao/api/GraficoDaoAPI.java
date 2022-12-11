package pe.edu.utp.i2.citasmedicas.dao.api;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pe.edu.utp.i2.citasmedicas.model.Grafico;

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
            " month(fecha_cita) as id, \n" +
            " month(fecha_cita) as mes, \n" +
            " count(*) as total,\n" +
            " right(substr('ENEFEBMARABRMAYJUNJULAGOSETOCTNOVDIC',1, month(fecha_cita) * 3),3) as nameM\n" +
            " from reserva where estado = 'ATENDIDO' and year(fecha_cita) = :anio group by mes, nameM order by 2",
            nativeQuery = true)
    List<Grafico> findCountReservasByAnio(@Param("anio") Integer anio);


    @Query(value = "select \n" +
            "r.id_medico as id,\n" +
            "r.id_medico as mes, \n" +
            "count(*) as total,\n" +
            "left(concat(m.apellidos,' ',m.nombres),20) as nameM \n" +
            "from reserva r\n" +
            "inner join medicos m on r.id_medico=m.id_medico \n" +
            "where year(fecha_cita) = :anio \n" +
            "and estado = 'ATENDIDO' \n" +
            "group by mes, nameM \n" +
            "order by 3 desc, 4 asc limit 10;",
            nativeQuery = true)
    List<Grafico> findCountReservasByAnioByMed(@Param("anio") Integer anio);

}
