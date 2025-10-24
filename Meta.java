public class Meta {
    private String id;
    private String descripcion;
    private int puntosObjetivo;
    private boolean completada;

    // Constructor
    public Meta(String id, String descripcion, int puntosObjetivo) {
        this.id = id;
        this.descripcion = descripcion;
        this.puntosObjetivo = puntosObjetivo;
        this.completada = false;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPuntosObjetivo() {
        return puntosObjetivo;
    }

    public boolean isCompletada() {
        return completada;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPuntosObjetivo(int puntosObjetivo) {
        this.puntosObjetivo = puntosObjetivo;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    // Método auxiliar
    public void marcarComoCompletada() {
        this.completada = true;
    }

    // toString()
    @Override
    public String toString() {
        return "Meta{" +
                "id='" + id + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", puntosObjetivo=" + puntosObjetivo +
                ", completada=" + completada +
                '}';
    }
}
