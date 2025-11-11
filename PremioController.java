import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class PremioController {

    private ArrayList<Premio> premios;
    private ArrayList<Canje> historialCanjes;

    public PremioController() {
        premios = new ArrayList<>();
        historialCanjes = new ArrayList<>();
    }

    // Agregar un premio a la lista
    public void agregarPremio(Premio premio) {
        premios.add(premio);
    }

    public ArrayList<Premio> getPremios() {
    return premios;}

    //  Registrar canje
    private void registrarCanje(String usuarioId, Premio premio, int puntosUsados) {
        Canje nuevoCanje = new Canje(
            UUID.randomUUID().toString(),
            usuarioId,
            premio.getNombre(),
            new Date(),
            puntosUsados
        );
        historialCanjes.add(nuevoCanje);
    }

    //Obtener historial de canjes de un usuario
    public ArrayList<Canje> obtenerHistorialCanjes(String usuarioId) {
        ArrayList<Canje> canjesUsuario = new ArrayList<>();
        for (Canje c : historialCanjes) {
            if (c.getUsuarioId().equals(usuarioId)) {
                canjesUsuario.add(c);
            }
        }
        return canjesUsuario;
    }

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
                             " | Stock: " + (premio.isDisponible() ? "Disponible " : "Agotado ");
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

        // Registrar el canje en el historial
        registrarCanje(usuario.getId(), premio, premio.getPuntosRequeridos());

        return "Has canjeado el premio: " + premio.getNombre() + ". ¡Disfrútalo!";
    }
}