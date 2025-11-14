import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

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
    private ArrayList<Canje> historialCanjes;
    private boolean primerLogin;
    
    // NUEVO: Registro de puntos ganados
    private int puntosTotalesGanados;
    private ArrayList<HashMap<String, Object>> historialPuntos;
    
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
        this.historialCanjes = new ArrayList<>();
        this.premiosCanjeados = new ArrayList<>();
        this.primerLogin = true;
        
        // NUEVO: Inicializar registro de puntos
        this.puntosTotalesGanados = 0;
        this.historialPuntos = new ArrayList<>();
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

     public ArrayList<Canje> getHistorialCanjes() { 
        return historialCanjes;
    }
    
    public boolean isPrimerLogin() {
        return primerLogin;
    }

    // NUEVOS Getters para puntos totales
    public int getPuntosTotalesGanados() {
        return puntosTotalesGanados;
    }
    
    public ArrayList<HashMap<String, Object>> getHistorialPuntos() {
        return historialPuntos;
    }

    //Setters

    public void setPreferencias(PreferenciasUsuario preferencias) {
        this.preferencias = preferencias;
    }

    public void setMetas(ArrayList<Meta> metas) {
        this.metas = metas;
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

    public void setPuntosTotalesGanados(int puntosTotalesGanados) {
        this.puntosTotalesGanados = puntosTotalesGanados;
    }

    public void setHistorialPuntos(ArrayList<HashMap<String, Object>> historialPuntos) {
        this.historialPuntos = historialPuntos;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    //método canje
    public void agregarCanje(Canje canje) {
    this.historialCanjes.add(canje);
    }

    public void mostrarHistorialCanjes() {
        if (historialCanjes.isEmpty()) {
            System.out.println(" No has canjeado ningún premio todavía.\n");
        } else {
            System.out.println(" Historial de Canjes:");
            for (int i = 0; i < historialCanjes.size(); i++) {
                System.out.println((i + 1) + ". " + historialCanjes.get(i));
            }
            System.out.println();
        }
    }

    // NUEVO: Método para registrar ganancia de puntos
    public void registrarPuntosGanados(int puntos, String fuente, String descripcion) {
        this.puntosTotalesGanados += puntos;
        
        HashMap<String, Object> registro = new HashMap<>();
        registro.put("fecha", new Date());
        registro.put("puntos", puntos);
        registro.put("fuente", fuente);
        registro.put("descripcion", descripcion);
        registro.put("puntosTotales", this.puntosTotalesGanados);
        
        this.historialPuntos.add(registro);
        
        System.out.println("✅ +" + puntos + " puntos ganados por: " + descripcion);
    }

    // NUEVO: Método para mostrar historial de puntos
    public void mostrarHistorialPuntos() {
        if (historialPuntos.isEmpty()) {
            System.out.println(" No has ganado puntos todavía.\n");
            return;
        }
        
        System.out.println(  "\n╔════════════════════════════════════════════════════════════╗");
        System.out.println(    "║                 HISTORIAL DE PUNTOS GANADOS                ║");
        System.out.println(    "╠════════════════════════════════════════════════════════════╣");
        System.out.printf("║              Puntos totales ganados: %-33d                 ║\n", puntosTotalesGanados);
        System.out.println(    "╠════════════════════════════════════════════════════════════╣");
        
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
        
        for (int i = 0; i < historialPuntos.size(); i++) {
            HashMap<String, Object> registro = historialPuntos.get(i);
            System.out.printf("║ %2d. %-10s | +%-3d pts | %-25s ║\n", 
                i + 1,
                sdf.format(registro.get("fecha")),
                registro.get("puntos"),
                truncarTexto(registro.get("descripcion").toString(), 25)
            );
        }
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
    }
    
    private String truncarTexto(String texto, int longitud) {
        if (texto.length() <= longitud) {
            return texto;
        }
        return texto.substring(0, longitud - 3) + "...";
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