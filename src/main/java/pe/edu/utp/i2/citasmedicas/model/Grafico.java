package pe.edu.utp.i2.citasmedicas.model;

import javax.persistence.*;

@Entity
public class Grafico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;




    private int mes;
    private double total;
    private String nameM;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMes() { return mes; }
    public void setMes(int mes) { this.mes = mes; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public String getNameM() { return nameM; }
    public void setNameM(String nameM) { this.nameM = nameM; }

}
