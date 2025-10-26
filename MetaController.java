import java.util.ArrayList;

public class MetaController{
    private UsuarioController usuarioController;
    private MensajeController mensajeController;

    public MetaController(UsuarioController usuarioController, MensajeController mensajeController){
        this.usuarioController = usuarioController;
        this.mensajeController = mensajeController;
    }

    public void asignarMeta(String usuarioId, Meta meta){
        Usuario usuario = usuarioController.buscarUsuarioPorId(usuarioId);
        if (usuario == null){
            System.out.println("Usuario no encontrado.");
            return;
        }
        usuario.agregarMeta(meta);
        System.out.println("Meta asignada exitosamente: " + meta.getDescripcion());  
    }
    public void completarMeta(String usuarioId, String metaId){
        Usuario usuario = usuarioController.buscarUsuarioPorId(usuarioId);
        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        Meta metaEncontrada = buscarMetaPorId(usuario, metaId);

        if (metaEncontrada == null) {
            System.out.println("Meta no encontrada.");
            return;
        }

        if (metaEncontrada.isCompletada()) {
            System.out.println("Esta meta ya está completada.");
            return;
        }

        // Marcar meta como completada
        metaEncontrada.marcarComoCompletada();

        // Sumar puntos al usuario
        int puntosGanados = metaEncontrada.getPuntosObjetivo();
        usuarioController.sumarPuntos(usuario, puntosGanados);

        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║            ¡META COMPLETADA!                               ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.printf("║  %s\n", ajustarTexto(metaEncontrada.getDescripcion(), 54));
        System.out.printf("║  Puntos ganados: %-39d ║\n", puntosGanados);
        System.out.printf("║  Total de puntos: %-38d ║\n", usuario.getPuntos());
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");

        // Mostrar mensaje motivacional de meta completada
        MensajeMotivacional mensajeMeta = mensajeController.generarMensaje(
            "¡Meta completada! " + metaEncontrada.getDescripcion() + ". ¡Sigue así!",
            "meta_completada",
            "meta_completada"
        );
        mensajeController.mostrarMensaje(mensajeMeta);

        int metasCompletadas = contarMetasCompletadas(usuario);
        
        if (metasCompletadas == 1) {
            MensajeMotivacional primeraMeta = mensajeController.generarMensaje(
                "¡Es tu PRIMERA meta completada! Este es solo el comienzo",
                "primera_meta",
                "primera_meta"
            );
            mensajeController.mostrarMensaje(primeraMeta);
        } else if (metasCompletadas % 5 == 0) {
            MensajeMotivacional logroEspecial = mensajeController.generarMensaje(
                "¡LOGRO ESPECIAL! Has completado " + metasCompletadas + " metas. ¡Eres imparable!",
                "logro_metas",
                "metas_completadas % 5 == 0"
            );
            mensajeController.mostrarMensaje(logroEspecial);
        }
    }
    public void obtenerMetas(String usuarioId){
        Usuario usuario = usuarioController.buscarUsuarioPorId(usuarioId);
        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        ArrayList<Meta> metas = usuario.getMetas();

        if (metas.isEmpty()) {
            System.out.println("\n╔══════════════════════════════════════════════════════════╗");
            System.out.println("║                  TUS METAS                               ║");
            System.out.println("╠══════════════════════════════════════════════════════════╣");
            System.out.println("║  No tienes metas asignadas aún.                         ║");
            System.out.println("║  Crea tus metas personales y márcalas cuando las        ║");
            System.out.println("║  completes en la vida real.                             ║");
            System.out.println("╚══════════════════════════════════════════════════════════╝\n");
            return;
        }

        int completadas = contarMetasCompletadas(usuario);
        int pendientes = contarMetasPendientes(usuario);
        int totalPuntosDisponibles = calcularPuntosPendientes(usuario);

        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                 TUS METAS                               ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.printf("║  Completadas: %-5d | Pendientes: %-5d                  ║\n", completadas, pendientes);
        System.out.printf("║  Puntos disponibles en metas pendientes: %-14d ║\n", totalPuntosDisponibles);
        System.out.println("╠══════════════════════════════════════════════════════════╣");

        for (Meta meta : metas) {
            String estado = meta.isCompletada() ? "Completada" : "Pendiente";
            String icono = meta.isCompletada() ? "/" : "X";
            
            System.out.printf("║ %s [%s] %s\n", icono, meta.getId(), ajustarTexto(meta.getDescripcion(), 47));
            System.out.printf("║    Estado: %-45s ║\n", estado);
            System.out.printf("║    Recompensa: %-38d puntos ║\n", meta.getPuntosObjetivo());
            System.out.println("╠══════════════════════════════════════════════════════════╣");
        }

        System.out.println("╚══════════════════════════════════════════════════════════╝\n");

        // Mensaje motivacional según el progreso
        mostrarMensajeProgreso(completadas, pendientes);
    }
    public void evaluarMetasCompletadas(String usuarioId){
        Usuario usuario = usuarioController.buscarUsuarioPorId(usuarioId);
        if (usuario == null) {
            return;
        }

        int metasPendientes = contarMetasPendientes(usuario);
        
        // Mostrar recordatorio ocasional (cada 5 consumos)
        if (metasPendientes > 0 && usuario.getConsumos().size() % 5 == 0) {
            MensajeMotivacional mensaje = mensajeController.generarMensaje(
                "Tienes " + metasPendientes + " meta(s) pendiente(s). ¡No olvides marcarlas cuando las completes!",
                "recordatorio_metas",
                "metas_pendientes"
            );
            mensajeController.mostrarMensaje(mensaje);
        }
    }

    public void crearMetasIniciales(String usuarioId) {
        Usuario usuario = usuarioController.buscarUsuarioPorId(usuarioId);
        if (usuario == null) {
            return;
        }

        if (!usuario.getMetas().isEmpty()) {
            return;
        }

        asignarMeta(usuarioId, new Meta("1", "Correr 10 km", 100));
        asignarMeta(usuarioId, new Meta("2", "Meditar 15 minutos por 7 días", 50));
        asignarMeta(usuarioId, new Meta("3", "Hacer ejercicio 3 veces esta semana", 60));
        asignarMeta(usuarioId, new Meta("4", "Leer un libro completo", 80));
        asignarMeta(usuarioId, new Meta("5", "Caminar 10,000 pasos diarios por 7 días", 70));
        asignarMeta(usuarioId, new Meta("6", "No tomar café por 3 días", 40));
        asignarMeta(usuarioId, new Meta("7", "Tomar 8 vasos de agua diarios por 7 días", 50));

        System.out.println("\n🎯 ¡Metas iniciales creadas! Márcalas como completadas cuando las logres.");
    }


    private Meta buscarMetaPorId(Usuario usuario, String metaId) {
        for (Meta meta : usuario.getMetas()) {
            if (meta.getId().equals(metaId)) {
                return meta;
            }
        }
        return null;
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

    private int calcularPuntosPendientes(Usuario usuario) {
        int total = 0;
        for (Meta meta : usuario.getMetas()) {
            if (!meta.isCompletada()) {
                total += meta.getPuntosObjetivo();
            }
        }
        return total;
    }

    private void mostrarMensajeProgreso(int completadas, int pendientes) {
        if (completadas == 0 && pendientes > 0) {
            MensajeMotivacional mensaje = mensajeController.generarMensaje(
                "¡Tienes " + pendientes + " meta(s) esperándote! ¿Cuál completarás primero? 💪",
                "animo_metas",
                "sin_metas_completadas"
            );
            mensajeController.mostrarMensaje(mensaje);
        } else if (pendientes > 0) {
            double progreso = (completadas * 100.0) / (completadas + pendientes);
            MensajeMotivacional mensaje = mensajeController.generarMensaje(
                String.format("¡Vas al %.0f%% de progreso! Sigue así 🌟", progreso),
                "progreso_metas",
                "progreso_metas"
            );
            mensajeController.mostrarMensaje(mensaje);
        } else {
            MensajeMotivacional mensaje = mensajeController.generarMensaje(
                "🎉 ¡Has completado todas tus metas! Eres increíble",
                "todas_completadas",
                "todas_metas_completadas"
            );
            mensajeController.mostrarMensaje(mensaje);
        }
    }

    private String ajustarTexto(String texto, int longitudMaxima) {
        if (texto.length() > longitudMaxima) {
            return texto.substring(0, longitudMaxima - 3) + "...";
        }
        return String.format("%-" + longitudMaxima + "s", texto) + " ║";
    }
}