package pe.edu.utp.i2.citasmedicas.dao.api;

import pe.edu.utp.i2.citasmedicas.model.Usuario;

import org.springframework.data.repository.CrudRepository;

public interface UsuarioDaoAPI extends CrudRepository<Usuario, Long> {

	Usuario findByUsuario(String usuario);
}
