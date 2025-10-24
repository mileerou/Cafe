import java.util.ArrayList;

public class PremioController {

    private ArrayList<Premio> premios;

    public PremioController() {
        premios = new ArrayList<>();
    }

    // Agregar un premio a la lista
    public void agregarPremio(Premio premio) {
        premios.add(premio);
    }

    public ArrayList<Premio> getPremios() {
    return premios;}

    // Obtener mensajes sobre premios disponibles
    public void mostrarMensajesPremios() {
        if (premios.isEmpty()) {
            System.out.println("No hay premios disponibles por el momento.");
            return;
        }

        System.out.println("🎉 Mensajes sobre premios:");
        for (Premio premio : premios) {
            String mensaje = "Premio: " + premio.getNombre() +
                             " | Puntos requeridos: " + premio.getPuntosRequeridos() +
                             " | Stock: " + (premio.isDisponible() ? "Disponible ✅" : "Agotado ❌");
            System.out.println(mensaje);
        }
    }
}