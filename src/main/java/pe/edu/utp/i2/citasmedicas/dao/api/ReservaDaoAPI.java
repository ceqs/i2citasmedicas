package pe.edu.utp.i2.citasmedicas.dao.api;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.i2.citasmedicas.model.Reserva;

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
    and c.idPaciente = IFNULL(idPac, c.idPaciente)  ;

-- c.fecha between '2022-02-24' and '2022-02-24'
-- set idMed = IFNULL(idMed, 99) ;
* */
}
