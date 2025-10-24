import java.util.Date;
import java.util.ArrayList;

public class Usuario {
    private String id;
    private String nombre;
    private String correo;
    private String contrasenaHash;
    private Date fechaRegistro;
    private PreferenciasUsuario preferencias;
    private int puntos;
    private ArrayList<Consumo> consumos;
    private ArrayList<Meta> metas;
    private ArrayList<Premio> premiosCanjeados;
    private boolean primerLogin;
    public Usuario(String id, String nombre, String correo, String contrasenaHash) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenaHash = contrasenaHash;
        this.fechaRegistro = new Date();
        this.preferencias = new PreferenciasUsuario();
        this.puntos = 0;
        this.consumos = new ArrayList<>();
        this.metas = new ArrayList<>();
        this.premiosCanjeados = new ArrayList<>();
        this.primerLogin = true;
    }

    // Getters

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public int getPuntos() {
        return puntos;
    }

    public PreferenciasUsuario getPreferencias() {
        return preferencias;
    }

    public ArrayList<Consumo> getConsumos() {
        return consumos;
    }

    public ArrayList<Meta> getMetas() {
        return metas;
    }

    public ArrayList<Premio> getPremiosCanjeados() {
        return premiosCanjeados;
    }
    
    public boolean isPrimerLogin() {
        return primerLogin;
    }

    //Setters

    public void setPreferencias(PreferenciasUsuario preferencias) {
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

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public void agregarMeta(Meta meta) {
        this.metas.add(meta);
    }

    public void agregarPremioCanjeado(Premio premio) {
        this.premiosCanjeados.add(premio);
    }

    public void setPrimerLogin(boolean primerLogin) {
        this.primerLogin = primerLogin;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", puntos=" + puntos +
                ", preferencias=" + preferencias +
                '}';
    }
}