import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class PremioController {
    private PremioDAO premioDAO;
    private CanjeDAO canjeDAO;

    public PremioController() {
        this.premioDAO = new PremioDAO();
        this.canjeDAO = new CanjeDAO();
    }

    public void agregarPremio(Premio premio) {
        premioDAO.insertarPremio(premio);
    }

    public ArrayList<Premio> getPremios() {
        return premioDAO.listarPremios();
    }

    private void registrarCanje(String usuarioId, Premio premio, int puntosUsados) {
        Canje nuevoCanje = new Canje(
            UUID.randomUUID().toString(),
            usuarioId,
            premio.getNombre(),
            new Date(),
            puntosUsados
        );
        canjeDAO.insertarCanje(nuevoCanje);
    }

    public ArrayList<Canje> obtenerHistorialCanjes(String usuarioId) {
        return canjeDAO.obtenerCanjes(usuarioId);
    }

    public void mostrarMensajesPremios() {
        ArrayList<Premio> premios = getPremios();
        
        if (premios.isEmpty()) {
            System.out.println("No hay premios disponibles por el momento.");
            return;
        }

        System.out.println("🎉 Mensajes sobre premios:");
        for (Premio premio : premios) {
            String mensaje = "Premio: " + premio.getNombre() +
                             " | Puntos requeridos: " + premio.getPuntosRequeridos() +
                             " | Stock: " + (premio.isDisponible() ? "Disponible" : "Agotado");
            System.out.println(mensaje);
        }
    }

    public String canjearPremio(Usuario usuario, String premioId) {
        Premio premio = premioDAO.buscarPremioPorId(premioId);

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
        int nuevosPuntos = usuario.getPuntos() - premio.getPuntosRequeridos();
        usuario.setPuntos(nuevosPuntos);
        
        // Actualizar puntos del usuario en la base de datos
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.actualizarPuntos(usuario.getId(), nuevosPuntos);
        
        // Actualizar stock del premio
        int nuevoStock = premio.getStock() - 1;
        premio.setStock(nuevoStock);
        premioDAO.actualizarStock(premio.getId(), nuevoStock);

        // Registrar el canje en el historial
        registrarCanje(usuario.getId(), premio, premio.getPuntosRequeridos());

        return "Has canjeado el premio: " + premio.getNombre() + ". ¡Disfrútalo!";
    }
}