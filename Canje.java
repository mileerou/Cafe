import java.util.Date;

public class Canje {
    private Premio premio;
    private Date fechaCanje;
    private int puntosUsados;

    // Constructor
    public Canje(Premio premio, int puntosUsados, Date fechaCanje) {
        this.premio = premio;
        this.puntosUsados = puntosUsados;
        this.fechaCanje = fechaCanje;
    }

    // Getters
    public Premio getPremio() {
        return premio;
    }
    public Date getFechaCanje() {
        return fechaCanje;
    }
    public int getPuntosUsados() {
        return puntosUsados;
    }

    @Override
    public String toString() {
        return "Canje{" +
                "premio=" + premio +
                ", fechaCanje=" + fechaCanje +
                ", puntosUsados=" + puntosUsados +
                '}';
    }
}