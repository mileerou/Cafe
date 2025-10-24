import java.util.ArrayList;
import java.util.Random;

public class MensajeController {
    private ArrayList<MensajeMotivacional> mensajesPredefinidos;
    private Random random;

    public MensajeController() {
        this.mensajesPredefinidos = new ArrayList<>();
        this.random = new Random();
        inicializarMensajes();
    }

   
    private void inicializarMensajes() {
        // Mensajes de bienvenida
        mensajesPredefinidos.add(new MensajeMotivacional("msg_001", "¡Bienvenido de nuevo! Hoy es un gran día para reducir tu consumo de café "));
        mensajesPredefinidos.add(new MensajeMotivacional("msg_002", "¡Cada pequeño paso cuenta! Registra tu consumo diario "));
        
        // Mensajes después de registrar consumo
        mensajesPredefinidos.add(new MensajeMotivacional("msg_003", "¡Excelente! Has registrado tu consumo. La consciencia es el primer paso "));
        mensajesPredefinidos.add(new MensajeMotivacional("msg_004", "¡Bien hecho! Mantén el registro constante para ver tu progreso "));
        
        // Mensajes de metas completadas
        mensajesPredefinidos.add(new MensajeMotivacional("msg_005", " ¡FELICIDADES! Has completado una meta. ¡Eres increíble!"));
        mensajesPredefinidos.add(new MensajeMotivacional("msg_006", " ¡Meta alcanzada! Tu dedicación está dando frutos"));
        
        // Mensajes de puntos
        mensajesPredefinidos.add(new MensajeMotivacional("msg_007", " ¡Has ganado puntos! Sigue acumulando para canjear premios"));
        mensajesPredefinidos.add(new MensajeMotivacional("msg_008", " ¡Puntos sumados! Estás más cerca de tu próximo premio"));
        
        // Mensajes motivacionales generales
        mensajesPredefinidos.add(new MensajeMotivacional("msg_009", "Recuerda: el cambio es un proceso, no un evento "));
        mensajesPredefinidos.add(new MensajeMotivacional("msg_010", "Tu salud te lo agradecerá. ¡Sigue adelante! "));
        
        // Mensajes de premios
        mensajesPredefinidos.add(new MensajeMotivacional("msg_011", " ¡Premio canjeado! Disfruta tu recompensa, te lo mereces"));
        mensajesPredefinidos.add(new MensajeMotivacional("msg_012", " ¡Increíble! Has canjeado un premio. Sigue así"));
    }

    public MensajeMotivacional obtenerMensaje(String usuarioId, UsuarioController usuarioController) {
        try {
            Usuario usuario = buscarUsuarioPorId(usuarioId, usuarioController);
            if (usuario == null) {
                return mensajesPredefinidos.get(random.nextInt(Math.min(4, mensajesPredefinidos.size())));
            }

            // Evaluar el estado del usuario y retornar mensaje apropiado
            int metasCompletadas = contarMetasCompletadas(usuario);
            int premiosCanjeados = usuario.getPremiosCanjeados().size();
            int consumosRegistrados = usuario.getConsumos().size();
            int puntosActuales = usuario.getPuntos();

            // Priorizar mensajes según logros
            if (metasCompletadas > 0 && metasCompletadas % 5 == 0) {
                // Mensaje especial cada 5 metas
                return generarMensaje(
                    " ¡IMPRESIONANTE! Has completado " + metasCompletadas + " metas. ¡Eres un campeón!",
                    "meta_especial",
                    "metas_completadas >= 5"
                );
            }

            if (premiosCanjeados > 0 && premiosCanjeados % 3 == 0) {
                // Mensaje especial cada 3 premios
                return generarMensaje(
                    " ¡WOW! Ya has canjeado " + premiosCanjeados + " premios. ¡Increíble progreso!",
                    "premio_especial",
                    "premios_canjeados >= 3"
                );
            }

            if (consumosRegistrados >= 30) {
                return generarMensaje(
                    " ¡Un mes completo de registros! Tu compromiso es inspirador",
                    "racha_consumos",
                    "consumos >= 30"
                );
            }

            if (puntosActuales >= 500) {
                return generarMensaje(
                    " ¡Tienes " + puntosActuales + " puntos! Estás muy cerca de premios increíbles",
                    "puntos_altos",
                    "puntos >= 500"
                );
            }

            // Mensaje aleatorio de motivación
            return mensajesPredefinidos.get(random.nextInt(mensajesPredefinidos.size()));

        } catch (Exception e) {
            // En caso de error, retornar un mensaje genérico
            return mensajesPredefinidos.get(0);
        }
    }

    public MensajeMotivacional obtenerMensajesDelDia(String usuarioId, UsuarioController usuarioController) {
        try {
            Usuario usuario = buscarUsuarioPorId(usuarioId, usuarioController);
            if (usuario == null) {
                return mensajesPredefinidos.get(random.nextInt(2));
            }

            // Evaluar progreso del usuario
            int consumosHoy = contarConsumosHoy(usuario);
            int metasPendientes = contarMetasPendientes(usuario);
            int puntosActuales = usuario.getPuntos();

            // Mensajes personalizados según el estado
            if (usuario.isPrimerLogin()) {
                return generarMensaje(
                    "¡Bienvenido a Movaccino, " + usuario.getNombre() + "! 🎉 Comienza tu viaje hacia una vida más saludable",
                    "primer_login",
                    "primer_login == true"
                );
            }

            if (consumosHoy == 0) {
                return generarMensaje(
                    "¡Buenos días, " + usuario.getNombre() + "!  No olvides registrar tu consumo de hoy",
                    "recordatorio_diario",
                    "consumos_hoy == 0"
                );
            }

            if (metasPendientes > 0) {
                return generarMensaje(
                    "Tienes " + metasPendientes + " meta(s) pendiente(s). ¡Tú puedes completarlas! ",
                    "metas_pendientes",
                    "metas_pendientes > 0"
                );
            }

            if (puntosActuales >= 100 && usuario.getPremiosCanjeados().isEmpty()) {
                return generarMensaje(
                    "¡Tienes " + puntosActuales + " puntos! ¿Qué tal si canjeas tu primer premio? ",
                    "sugerencia_premio",
                    "puntos >= 100 && premios_canjeados == 0"
                );
            }

            // Mensajes motivacionales generales (índices 8-9)
            ArrayList<MensajeMotivacional> mensajesMotivacionales = new ArrayList<>();
            for (int i = 8; i <= 9 && i < mensajesPredefinidos.size(); i++) {
                mensajesMotivacionales.add(mensajesPredefinidos.get(i));
            }
            
            return mensajesMotivacionales.get(random.nextInt(mensajesMotivacionales.size()));

        } catch (Exception e) {
            return mensajesPredefinidos.get(0);
        }
    }

   
    public MensajeMotivacional generarMensaje(String contenido, String tipo, String condicionAparecer) {
        String nuevoId = "msg_custom_" + System.currentTimeMillis();
        return new MensajeMotivacional(nuevoId, contenido);
    }

    private Usuario buscarUsuarioPorId(String usuarioId, UsuarioController usuarioController) {
        return usuarioController.buscarUsuarioPorId(usuarioId);
    }

    private int contarMetasCompletadas(Usuario usuario) {
        int count = 0;
        for (Meta meta : usuario.getMetas()) {
            if (meta.isCompletada()) {
                count++;
            }
        }
        return count;
    }

    private int contarMetasPendientes(Usuario usuario) {
        int count = 0;
        for (Meta meta : usuario.getMetas()) {
            if (!meta.isCompletada()) {
                count++;
            }
        }
        return count;
    }

    private int contarConsumosHoy(Usuario usuario) {
        // Esta lógica debería verificar consumos de hoy
        // Por simplicidad, retornamos el total
        return usuario.getConsumos().size();
    }

    // Método auxiliar para mostrar mensaje en consola
    public void mostrarMensaje(MensajeMotivacional mensaje) {
        if (mensaje != null) {
            System.out.println("\n╔════════════════════════════════════════════════════════════╗");
            System.out.println("║  " + mensaje.toString());
            System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        }
    }

    public MensajeMotivacional obtenerMensajeRegistroConsumo() {
        ArrayList<MensajeMotivacional> mensajes = new ArrayList<>();
        mensajes.add(mensajesPredefinidos.get(2));
        mensajes.add(mensajesPredefinidos.get(3));
        return mensajes.get(random.nextInt(mensajes.size()));
    }

}