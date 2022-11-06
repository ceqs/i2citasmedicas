package pe.edu.utp.i2.citasmedicas.service.api;

import pe.edu.utp.i2.citasmedicas.commons.GenericServiceAPI;
import pe.edu.utp.i2.citasmedicas.model.Paciente;

public interface PacienteServiceAPI extends GenericServiceAPI<Paciente, Integer> {

    Paciente findByUsuario(String usuario);
}
