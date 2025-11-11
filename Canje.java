import java.util.Date;

public class Canje {
    private String id;
    private String usuarioId;
    private String premioNombre;
    private Date fechaCanje;
    private int puntosUsados;

    public Canje(String id, String usuarioId, String premioNombre, Date fechaCanje, int puntosUsados) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.premioNombre = premioNombre;
        this.fechaCanje = fechaCanje;
        this.puntosUsados = puntosUsados;
    }

    public String getId() { return id; }
    public String getUsuarioId() { return usuarioId; }
    public String getPremioNombre() { return premioNombre; }
    public Date getFechaCanje() { return fechaCanje; }
    public int getPuntosUsados() { return puntosUsados; }

    @Override
    public String toString() {
        return "Canje{" +
                "premio='" + premioNombre + '\'' +
                ", fechaCanje=" + fechaCanje +
                ", puntosUsados=" + puntosUsados +
                '}';
    }
}
