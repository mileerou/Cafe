public class Meta {
    private String id;
    private String descripcion;
    private boolean completada;
    private int recompensa;

    public Meta(String id, String descripcion, int recompensa) {
        this.id = id;
        this.descripcion = descripcion;
        this.recompensa = recompensa;
        this.completada = false;
    }

    public String getId() { return id; }
    public String getDescripcion() { return descripcion; }
    public int getRecompensa() { return recompensa; }
    public boolean isCompletada() { return completada; }

    public void completar() {
        this.completada = true;
    }

    public String toString() {
        return descripcion + " (" + (completada ? "✔" : "Pendiente") + ") - Recompensa: " + recompensa + " pts";
    }
}
