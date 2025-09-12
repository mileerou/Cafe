public class Premio {
    private String id, nombre; private int puntosRequeridos, stock;
    public Premio(String id, String nombre, int puntosRequeridos, int stock) {
        this.id = id; this.nombre = nombre; this.puntosRequeridos = puntosRequeridos; this.stock = stock;
    }
    public boolean isDisponible() { return stock > 0; }
    public String toString() { return nombre + " - " + puntosRequeridos + " pts"; }
}
