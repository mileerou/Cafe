public class Meta {
    private String id, descripcion; private boolean completada; private int recompensa;
    public Meta(String id, String descripcion, int recompensa) {
        this.id = id; this.descripcion = descripcion; this.recompensa = recompensa; this.completada = false;
    }
    public void completar() { completada = true; }
    public String toString() { return descripcion + " (" + (completada ? "✔" : "Pendiente") + ")"; }
}
