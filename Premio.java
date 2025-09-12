public class Premio {
    private String id;
    private String nombre;
    private String descripcion;
    private int puntosRequeridos;
    private int stock;

    public Premio(String id, String nombre, String descripcion, int puntosRequeridos, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntosRequeridos = puntosRequeridos;
        this.stock = stock;
    }

    public boolean isDisponible() {
        return stock > 0;
    }

    public String toString() {
        return nombre + " (" + puntosRequeridos + " pts) - Stock: " + stock;
    }
}
