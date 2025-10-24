public class MensajeMotivacional {
    private String id;
    private String contenido;

    public MensajeMotivacional(String id, String contenido) {
        this.id = id;
        this.contenido = contenido;
    }

    public String getContenido() { return contenido; }

    public String getId() { return id; }

    public String toString() {
        return contenido;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
