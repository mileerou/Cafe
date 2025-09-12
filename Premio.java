public class Premio {
    private String nombre; private int puntos;
    public Premio(String nombre, int puntos) { this.nombre = nombre; this.puntos = puntos; }
    public String toString() { return nombre + " (" + puntos + " pts)"; }
}
