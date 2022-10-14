package pe.edu.utp.i2.citasmedicas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "roles")
public class Rol {
    public final static String ADMIN = "ADMINISTRADOR";

    @Id
    @Column(name = "id_rol")
    private String id;

    @Column()
    private String nombre;

    @Column()
    private String descripcion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isAdmin() {
        return id.equals(ADMIN);
    }
}
