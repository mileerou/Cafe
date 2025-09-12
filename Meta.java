public class Meta {
    private String descripcion; private boolean completada;
    public Meta(String descripcion) { this.descripcion = descripcion; }
    public String toString() { return descripcion + (completada ? " ✔" : " ✘"); }
}

