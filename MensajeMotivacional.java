public class MensajeMotivacional {
    private String id, contenido, tipo;
    public MensajeMotivacional(String id, String contenido, String tipo) {
        this.id = id; this.contenido = contenido; this.tipo = tipo;
    }
    public String toString() { return "[" + tipo + "] " + contenido; }
}
