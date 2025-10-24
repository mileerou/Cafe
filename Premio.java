public class Premio {
    private String id;
    private String nombre;
    private String descripcion;
    private int puntosRequeridos;
    private int stock;

    // Constructor
    public Premio(String id, String nombre, String descripcion, int puntosRequeridos, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.puntosRequeridos = puntosRequeridos;
        this.stock = stock;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getPuntosRequeridos() {
        return puntosRequeridos;
    }

    public int getStock() {
        return stock;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPuntosRequeridos(int puntosRequeridos) {
        this.puntosRequeridos = puntosRequeridos;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Método auxiliar
    public boolean isDisponible() {
        return stock > 0;
    }

    // toString()
    @Override
    public String toString() {
        return "Premio {" +
               "\n  ID: '" + id + '\'' +
               ",\n  Nombre: '" + nombre + '\'' +
               ",\n  Descripción: '" + descripcion + '\'' +
               ",\n  Puntos requeridos: " + puntosRequeridos +
               ",\n  Stock disponible: " + stock +
               "\n}";
    }
}