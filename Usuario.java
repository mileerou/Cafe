import java.util.Date;
import java.util.ArrayList;

public class Usuario {
    private String id;
    private String nombre;
    private String correo;
    private String contrasenaHash;
    private Date fechaRegistro;
    private PreferenciaUsuario preferencias;
    private int puntos;
    private ArrayList<Consumo> consumos;
    private ArrayList<Meta> metas;
    private ArrayList<Premio> premiosCanjeados;

    public Usuario(String id, String nombre, String correo, String contrasenaHash) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenaHash = contrasenaHash;
        this.fechaRegistro = new Date();
        this.preferencias = new PreferenciaUsuario();
        this.puntos = 0;
        this.consumos = new ArrayList<>();
        this.metas = new ArrayList<>();
        this.premiosCanjeados = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPreferencias(PreferenciaUsuario preferencias) {
        this.preferencias = preferencias;
    }

    public void agregarConsumo(Consumo consumo) {
        this.consumos.add(consumo);
    }

    public void sumarPuntos(int cantidad){
        puntos += cantidad;
    }

    public void restarPuntos(int cantidad){
        puntos = Math.max(0, puntos - cantidad);
    }
}