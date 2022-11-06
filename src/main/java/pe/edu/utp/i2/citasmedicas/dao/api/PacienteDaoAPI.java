package pe.edu.utp.i2.citasmedicas.dao.api;

import org.springframework.data.repository.CrudRepository;
import pe.edu.utp.i2.citasmedicas.model.Paciente;
import pe.edu.utp.i2.citasmedicas.model.Usuario;

public interface PacienteDaoAPI extends CrudRepository<Paciente, Integer> {
    Paciente findByUsuarioUsuario(String usuario);
}
