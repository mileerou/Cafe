import java.util.ArrayList;

public class MetaController {
    private UsuarioController usuarioController;
    private MensajeController mensajeController;
    private MetaDAO metaDAO;

    public MetaController(UsuarioController usuarioController, MensajeController mensajeController) {
        this.usuarioController = usuarioController;
        this.mensajeController = mensajeController;
        this.metaDAO = new MetaDAO();
    }

    public void asignarMeta(String usuarioId, Meta meta) {
        Usuario usuario = usuarioController.buscarUsuarioPorId(usuarioId);
        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }
        
        usuario.agregarMeta(meta);
        metaDAO.insertarMeta(meta, usuarioId);
        System.out.println("Meta asignada exitosamente: " + meta.getDescripcion());
    }

    public void completarMeta(String usuarioId, String metaId) {
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
        metaDAO.marcarCompletada(usuarioId, metaId);
        
        // Eliminar meta de las metas pendientes
        usuario.getMetas().remove(metaEncontrada);
        metaDAO.deleteMeta(usuarioId, metaId);

        // Sumar puntos al usuario
        int puntosGanados = metaEncontrada.getPuntosObjetivo();
        
        // Registrar puntos ganados
        usuario.registrarPuntosGanados(puntosGanados, "meta", 
            "Meta completada: " + metaEncontrada.getDescripcion());
        
        usuarioController.sumarPuntosConHistorial(usuario, puntosGanados);

        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║            ¡META COMPLETADA!                               ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");
        System.out.printf("║  %s\n", ajustarTexto(metaEncontrada.getDescripcion(), 54));
        System.out.printf("║  Puntos ganados: %-39d ║\n", puntosGanados);
        System.out.printf("║  Total de puntos: %-38d ║\n", usuario.getPuntos());
        System.out.printf("║  Puntos totales ganados: %-31d ║\n", usuario.getPuntosTotalesGanados());
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");

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

    public void actualizarProgresoMeta(String usuarioId, String metaId, int puntosAAgregar) {
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

        int nuevos = metaEncontrada.getPuntosActuales() + puntosAAgregar;
        metaEncontrada.setPuntosActuales(nuevos);

        boolean ahoraCompletada = metaEncontrada.isCompletada();

        // Persistir cambios
        metaDAO.actualizarPuntos(usuarioId, metaId, metaEncontrada.getPuntosActuales(), ahoraCompletada);

        if (ahoraCompletada) {
            // Si al actualizar se completó, otorgar puntos totales y mostrar mensaje
            int puntosGanados = metaEncontrada.getPuntosObjetivo();
            usuario.registrarPuntosGanados(puntosGanados, "meta", "Meta completada: " + metaEncontrada.getDescripcion());
            usuarioController.sumarPuntosConHistorial(usuario, puntosGanados);

            System.out.println("¡Felicidades! Has completado la meta al alcanzar los puntos objetivo.");
            MensajeMotivacional mensajeMeta = mensajeController.generarMensaje(
                "¡Meta completada! " + metaEncontrada.getDescripcion() + ". ¡Sigue así!",
                "meta_completada",
                "meta_completada"
            );
            mensajeController.mostrarMensaje(mensajeMeta);
        } else {
            System.out.printf("Progreso actualizado: %d/%d puntos (%.2f%%)\n",
                metaEncontrada.getPuntosActuales(), metaEncontrada.getPuntosObjetivo(), metaEncontrada.calcularProgreso());
        }
    }

    public void crearMetasPorPreferencias(String usuarioId) {
        Usuario usuario = usuarioController.buscarUsuarioPorId(usuarioId);
        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        PreferenciasUsuario prefs = usuario.getPreferencias();
        if (prefs == null) {
            System.out.println("No tienes preferencias guardadas. Configúralas primero.");
            return;
        }

        if (!usuario.getMetas().isEmpty()) {
            System.out.println("Ya tienes metas asignadas.");
            return;
        }

        ArrayList<Meta> metas = new ArrayList<>();

        if (prefs.getTipoCafe().equalsIgnoreCase("americano")) {
            metas.add(new Meta("1", "Preparar un café americano perfecto durante 3 días seguidos", 50));
        } else if (prefs.getTipoCafe().equalsIgnoreCase("capuchino")) {
            metas.add(new Meta("2", "Tomar un capuchino sin azúcar durante 5 días", 60));
        }

        if (prefs.isUsaAzucar()) {
            metas.add(new Meta("3", "Reducir el azúcar a la mitad durante una semana", 70));
        } else {
            metas.add(new Meta("4", "Mantenerte sin azúcar por 7 días", 80));
        }

        if (prefs.isUsaLeche()) {
            metas.add(new Meta("5", "Probar leche vegetal durante 3 días", 50));
        }

        String[] retos = prefs.getRetosPreferidos();
        for (String reto : retos) {
            switch (reto.toLowerCase()) {
                case "energía":
                    metas.add(new Meta("6", "Dormir 8 horas diarias por una semana", 90));
                    break;
                case "salud":
                    metas.add(new Meta("7", "Tomar 2 litros de agua cada día durante 5 días", 60));
                    break;
                case "actividad física":
                    metas.add(new Meta("8", "Caminar 8,000 pasos diarios durante 7 días", 100));
                    break;
                case "productividad":
                    metas.add(new Meta("9", "Leer 20 minutos al día durante 5 días", 70));
                    break;
            }
        }

        for (Meta meta : metas) {
            asignarMeta(usuarioId, meta);
        }

        System.out.println("\n ¡Metas personalizadas creadas según tus preferencias!");
    }

    public void obtenerMetas(String usuarioId) {
        Usuario usuario = usuarioController.buscarUsuarioPorId(usuarioId);
        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }

        ArrayList<Meta> metas = metaDAO.obtenerMetas(usuarioId);
        usuario.setMetas(metas);

        if (metas.isEmpty()) {
            System.out.println("\nNo tienes metas asignadas aún.");
            return;
        }

        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║                     TUS METAS                            ║");
        System.out.println("╠══════════════════════════════════════════════════════════╣");

        for (Meta meta : metas) {
            String estado = meta.isCompletada() ? "Completada" : "Pendiente";
            String icono = meta.isCompletada() ? "✓" : "✗";
            double progreso = meta.calcularProgreso();

            System.out.printf("║ %s [%s] %s\n", icono, meta.getId(), ajustarTexto(meta.getDescripcion(), 47));
            System.out.printf("║    Estado: %-45s ║\n", estado);
            System.out.printf("║    Progreso: %-6d / %-6d puntos (%5.2f%%) ║\n",
                    meta.getPuntosActuales(), meta.getPuntosObjetivo(), progreso);
            System.out.println("╠══════════════════════════════════════════════════════════╣");
        }

        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
    }

    public void evaluarMetasCompletadas(String usuarioId) {
        ArrayList<Meta> metas = metaDAO.obtenerMetas(usuarioId);

        for (Meta meta : metas) {
            if (!meta.isCompletada() && meta.getPuntosActuales() >= meta.getPuntosObjetivo()) {
                meta.setCompletada(true);
                metaDAO.actualizarMeta(meta); // Asegúrate de tener este método en tu MetaDAO
                System.out.println("🎉 ¡Meta completada! " + meta.getDescripcion());
            }
        }
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
            if (meta.isCompletada()) count++;
        }
        return count;
    }

    private int contarMetasPendientes(Usuario usuario) {
        int count = 0;
        for (Meta meta : usuario.getMetas()) {
            if (!meta.isCompletada()) count++;
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

    public void mostrarProgresoMeta(Usuario usuario, String metaId) {
    if (usuario == null) {
        System.out.println("Usuario no encontrado.");
        return;
    }

    ArrayList<Meta> metas = usuario.getMetas();
    if (metas == null || metas.isEmpty()) {
        System.out.println("No tienes metas asignadas aún.");
        return;
    }

    Meta metaSeleccionada = null;
    for (Meta m : metas) {
        if (m.getId().equals(metaId)) {
            metaSeleccionada = m;
            break;
        }
    }

    if (metaSeleccionada == null) {
        System.out.println("Meta no encontrada con el ID proporcionado.");
        return;
    }

    double progreso = metaSeleccionada.calcularProgreso();
    int porcentaje = (int) progreso;

    System.out.println("\n╔══════════════════════════════════════════╗");
    System.out.printf("║ META: %-35s ║\n", metaSeleccionada.getDescripcion());
    System.out.println("╠══════════════════════════════════════════╣");
    System.out.printf("║ Progreso: %3d%% (%d / %d puntos)          ║\n",
            porcentaje,
            metaSeleccionada.getPuntosActuales(),
            metaSeleccionada.getPuntosObjetivo());

    // Generar una barra visual de progreso
    int barraLength = 30;
    int llenado = (int) (barraLength * progreso / 100);
    String barra = "█".repeat(llenado) + "-".repeat(barraLength - llenado);

    System.out.printf("║ [%s] ║\n", barra);
    System.out.println("╚══════════════════════════════════════════╝\n");

        if (metaSeleccionada.isCompletada()) {
            System.out.println("🎉 ¡Felicidades! Has completado esta meta. 🎉\n");
        }
    }

}