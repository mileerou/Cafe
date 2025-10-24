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

    public String canjearPremio(Usuario usuario, String premioId) {
        Premio premio = premios.stream()
                               .filter(p -> p.getId().equals(premioId))
                               .findFirst()
                               .orElse(null);

        if (premio == null) {
            return "Premio no encontrado.";
        }

        if (!premio.isDisponible()) {
            return "El premio está agotado.";
        }

        if (usuario.getPuntos() < premio.getPuntosRequeridos()) {
            return "No tienes suficientes puntos para canjear este premio.";
        }

        // Realizar el canje
        usuario.restarPuntos(premio.getPuntosRequeridos());
        premio.setStock(premio.getStock() - 1);

        return "Has canjeado el premio: " + premio.getNombre() + ". ¡Disfrútalo!";
    }
}