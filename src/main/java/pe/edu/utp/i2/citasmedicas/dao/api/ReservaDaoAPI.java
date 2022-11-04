package pe.edu.utp.i2.citasmedicas.dao.api;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;
import pe.edu.utp.i2.citasmedicas.model.Reserva;

import java.util.List;

public interface ReservaDaoAPI extends CrudRepository<Reserva, Integer> {
/*
select c.idCita, c.fecha,
	(h.nombre) as horario,
	concat(m.apellidos,' ', m.nombres) as medico,
	(e.nomEspecialidad) as especialidad,
	concat(p.apePaterno,' ',p.apeMaterno,' ',p.nombres) as paciente,
	c.idMedico, e.idEspecialidad, c.idPaciente, c.idHorario
from citas c
	inner join medicos m on c.idMedico=m.idMedico
	inner join pacientes p on c.idPaciente=p.idPaciente
	inner join horarios h on c.idHorario=h.idHorario
	inner join especialidades e on m.idEspecialidad=e.idEspecialidad
where c.fecha between fecI and fecF
	and c.idMedico = IFNULL(idMed, c.idMedico)
	and m.idEspecialidad = IFNULL(idEsp,  m.idEspecialidad)
    and c.idPaciente = IFNULL(idPac, c.idPaciente);

@Query(value = "SELECT * FROM reserva r INNER JOIN medicos m on r.id_medico = m.id_medico WHERE r.fecha_cita between :start and :end and r.id_medico = :cmed and m.id_esp = :cesp",
           nativeQuery = true)
* */
    //MTC(04/11/2022)
    @Query(value = "SELECT * FROM reserva r \n" +
            " INNER JOIN medicos m on r.id_medico = m.id_medico \n" +
            " WHERE r.fecha_cita between :start and :end \n" +
            " and r.id_medico = case when :cmed = 0 then r.id_medico else :cmed end \n" +
            " and m.id_esp = case when :cesp = 0 then m.id_esp else :cesp end",
           nativeQuery = true)
    List<Reserva> findReservasByFechasEspMed(@Param("start") String start, @Param("end") String end, @Param("cesp") String cesp, @Param("cmed") Integer cmed);

}
