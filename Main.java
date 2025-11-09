import java.util.Scanner;
import java.util.Date;
import java.util.UUID;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    private static UsuarioController usuarioController = new UsuarioController();
    private static MensajeController mensajeController = new MensajeController();
    private static MetaController metaController = new MetaController(usuarioController, mensajeController);
    private static PremioController premioController = new PremioController();
    private static TiendaVirtual tienda = new TiendaVirtual(premioController);
    private static ArrayList<Premio> catalogo = tienda.obtenerCatalogo();

    public static void main(String[] args) {
    Scanner sc = new Scanner (System.in);
        Usuario usuarioActual = null;

        System.out.println("==============================================");
        System.out.println("   ¡Bienvenido a Movaccino, la app que te ayuda");
        System.out.println("      a reducir tu consumo de café!");
        System.out.println("==============================================");
        System.out.println("           ( (");
        System.out.println("            ) )");
        System.out.println("           ........");
        System.out.println("           |      |]");
        System.out.println("           \\      /");
        System.out.println("            `----'");
        System.out.println("==============================================\n");
        int opcion;

        do {
            System.out.println("1. Registrar");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            System.out.print("Elige una opción: ");
            String input = sc.nextLine();
            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingresa un número válido.");
                opcion = 0;
            }
            switch(opcion){
                case 1:
                    try{
                        UUID uuid = UUID.randomUUID();
                        String id = uuid.toString();
                        System.out.print("Nombre: ");
                        String nombre = sc.nextLine();
                        String correo;
                        do {
                            System.out.print("Correo: ");
                            correo = sc.nextLine();
                            if (!correo.contains("@")) {
                                System.out.println("Por favor, ingresa un correo válido (debe contener '@').");
                            }
                        } while (!correo.contains("@"));
                        System.out.print("Contraseña: ");
                        String contrasena = sc.nextLine();
                        String contrasenaHash = HashUtil.hashPassword(contrasena);

                        Usuario nuevoUsuario = usuarioController.registrarUsuario(id, nombre, correo, contrasenaHash);

                        System.out.println("¡Registro exitoso!\n");
                        System.out.println("Detalles del usuario:");
                        System.out.println("ID: " + id);
                        System.out.println("Nombre: " + nombre);
                        System.out.println("Correo: " + correo + "\n");
                        System.out.println("Contraseña: " + contrasena);
                        MensajeMotivacional mensajeBienvenida = mensajeController.generarMensaje(
                            "¡Bienvenido a Movaccino, " + nombre + "! Estamos emocionados de acompañarte en este viaje 🌟",
                            "registro",
                            "nuevo_usuario"
                        );
                        mensajeController.mostrarMensaje(mensajeBienvenida);

                        // === NUEVO: Pide preferencias y crea metas personalizadas ===
                        System.out.println("\nAntes de continuar, configura tus preferencias:");
                        PreferenciasUsuario preferencias = registrarPreferencias(sc);
                        nuevoUsuario.setPreferencias(preferencias);

                        metaController.crearMetasPorPreferencias(nuevoUsuario.getId());

                        System.out.println("Metas asignadas según tus preferencias \n");

                    } catch (Exception e){
                        System.out.println("Error al registrar usuario: " + e.getMessage());
                    }

                    break;
                case 2:
                    try{
                        System.out.println("Iniciar sesión");
                        String correoLogin;
                        do {
                            System.out.print("Correo: ");
                            correoLogin = sc.nextLine();
                            if (!correoLogin.contains("@")) {
                                System.out.println("Por favor, ingresa un correo válido (debe contener '@').");
                            }
                        } while (!correoLogin.contains("@"));
                        System.out.print("Contraseña: ");
                        String contrasenaLogin = sc.nextLine();
                        String contraseñaLoginHash = HashUtil.hashPassword(contrasenaLogin);
                        usuarioActual = usuarioController.login(correoLogin, contraseñaLoginHash);
                        if(usuarioActual != null){
                            if(usuarioActual.isPrimerLogin()) {
                                System.out.println("¡Bienvenido por primera vez, " + usuarioActual.getNombre() + "!");
                                usuarioActual.setPrimerLogin(false);
                            }
                            System.out.println("¡Inicio de sesión exitoso! Bienvenido, " + usuarioActual.getNombre() + ".\n");

                            MensajeMotivacional mensajeDelDia = mensajeController.obtenerMensajesDelDia(
                                usuarioActual.getId(), 
                                usuarioController
                            );
                            mensajeController.mostrarMensaje(mensajeDelDia);

                            mostrarMenuUsuario(sc, usuarioActual);
                        } else {
                            System.out.println("Credenciales incorrectas.");
                        }
                    }catch (Exception e){
                        System.out.println("Error al iniciar sesión: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Gracias por usar Movaccino. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.\n");
            }
        }while(opcion != 3);
    }

    private static PreferenciasUsuario registrarPreferencias(Scanner sc) {
        System.out.println("=== CONFIGURA TUS PREFERENCIAS ===");

        System.out.print("Tipo de café preferido: ");
        String tipoCafe = sc.nextLine();

        System.out.print("Tamaño de taza preferido (pequeña, mediana, grande): ");
        String tamanoTaza = sc.nextLine();

        System.out.print("¿Usas azúcar? (si/no): ");
        boolean usaAzucar = sc.nextLine().equalsIgnoreCase("si");
        String tipoAzucar = "";
        if (usaAzucar) {
            System.out.print("Tipo de azúcar (blanca, morena, stevia...): ");
            tipoAzucar = sc.nextLine();
        }

        System.out.print("¿Usas leche? (si/no): ");
        boolean usaLeche = sc.nextLine().equalsIgnoreCase("si");
        String tipoLeche = "";
        if (usaLeche) {
            System.out.print("Tipo de leche (entera, descremada, vegetal...): ");
            tipoLeche = sc.nextLine();
        }

        System.out.print("Menciona tus retos preferidos (separados por comas): ");
        String[] retos = sc.nextLine().split(",");

        return new PreferenciasUsuario(
                tipoCafe,
                tamanoTaza,
                usaAzucar,
                tipoAzucar,
                usaLeche,
                tipoLeche,
                retos
        );
    }

    public static void mostrarMenuUsuario(Scanner sc, Usuario usuarioActual) {
        int opcion;
        PreferenciasUsuarioController preferenciasController = new PreferenciasUsuarioController();
        do {
            System.out.println("   ^    ^  ");
            System.out.println("  ( ; . ; ) つ  ");
            System.out.println("  (     ⎠");
            System.out.println("  (      )  ");
            System.out.println(" (   ) (   )  ");
            System.out.println("  ^^    ^^   ");
            System.out.println("1. Registrar consumo diario de café");
            System.out.println("2. Ver historial de consumo");
            System.out.println("3. Configurar preferencias");
            System.out.println("4. Ver mis preferencias");
            System.out.println("5. Ver mis puntos");
            System.out.println("6. Modificar un campo de mi consumo de hoy");
            System.out.println("7. Actualizar usuario");
            System.out.println("8. Ver tienda de premios");
            System.out.println("9. Ver mis metas");
            System.out.println("10. Completar una meta");
            System.out.println("11. Ver historial de canjes");
            System.out.println("12. Cerrar sesión");
            System.out.print("Elige una opción: ");
            String input = sc.nextLine();
            try {
                opcion = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingresa un número válido.");
                opcion = 0;
            }
            switch (opcion) {
                case 1:
                    try {
                        Date fecha = new Date();
                        System.out.print("Tamaño de taza (Oz): ");
                        String tamanoTaza = sc.nextLine();
                        System.out.print("Tipo de azúcar: ");
                        String tipoAzucar = sc.nextLine();
                        System.out.print("Tipo de leche: ");
                        String tipoLeche = sc.nextLine();
                        System.out.print("Tipo de café: ");
                        String tipoCafe = sc.nextLine();
                        System.out.print("¿Algún comentario extra? ");
                        String respuestasExtras = sc.nextLine();

                        ConsumoController consumoController = new ConsumoController(usuarioActual);
                        consumoController.guardarConsumoDiario(fecha, tamanoTaza, tipoAzucar, tipoLeche, tipoCafe, respuestasExtras);

                        System.out.println("¡Consumo registrado exitosamente!\n");

                        mensajeController.mostrarMensaje(
                            mensajeController.obtenerMensajeRegistroConsumo()
                        );
                        metaController.evaluarMetasCompletadas(usuarioActual.getId());

                    } catch (Exception e) {
                        System.out.println("Error al registrar consumo: " + e.getMessage());
                    }
                break;

                case 2:
                    ConsumoController consumoController = new ConsumoController(usuarioActual);
                    ArrayList<Consumo> historial = consumoController.obtenerHistorialConsumo();
                    if (historial.isEmpty()) {
                        System.out.println("No hay consumos registrados.\n");
                    } else {
                        System.out.println("======= HISTORIAL DE CONSUMOS =======");
                        for (Consumo consumo : historial) {
                            System.out.println("╔══════════════════════════════════╗");
                            System.out.println("║   ☕  Registro de Consumo        ║");
                            System.out.println("╠══════════════════════════════════╣");
                            System.out.printf("║ Fecha: %-24s ║\n", consumo.getFecha());
                            System.out.printf("║ Tamaño de taza: %-16s ║\n", consumo.getTamañoTaza());
                            System.out.printf("║ Tipo de azúcar: %-16s ║\n", consumo.getTipoAzucar());
                            System.out.printf("║ Tipo de leche: %-16s ║\n", consumo.getTipoLeche());
                            System.out.printf("║ Tipo de café: %-17s ║\n", consumo.getTipoCafe());
                            System.out.printf("║ Extra: %-24s ║\n", consumo.getRespuestasExtras());
                            System.out.println("╚══════════════════════════════════╝\n");
                        }

                        if (historial.size() >= 7) {
                            MensajeMotivacional mensaje = mensajeController.generarMensaje(
                                "¡Llevas " + historial.size() + " registros! Tu constancia es admirable 📈",
                                "progreso",
                                "consumos >= 7"
                            );
                            mensajeController.mostrarMensaje(mensaje);
                        }

                    }
                break;

                case 3:
                    // Formulario de preferencias
                    System.out.println("\n===== CONFIGURAR PREFERENCIAS =====");
                    System.out.print("Tipo de café preferido: ");
                    String tipoCafe = sc.nextLine();
                    System.out.print("Tamaño de taza (Oz): ");
                    String tamanoTaza = sc.nextLine();
                    System.out.print("¿Usas azúcar? (si/no): ");
                    boolean usaAzucar = sc.nextLine().equalsIgnoreCase("si");
                    String tipoAzucar = "";
                    if (usaAzucar) {
                        System.out.print("Tipo de azúcar: ");
                        tipoAzucar = sc.nextLine();
                    }
                    System.out.print("¿Usas leche? (si/no): ");
                    boolean usaLeche = sc.nextLine().equalsIgnoreCase("si");
                    String tipoLeche = "";
                    if (usaLeche) {
                        System.out.print("Tipo de leche: ");
                        tipoLeche = sc.nextLine();
                    }
                    ArrayList<String> retosList = new ArrayList<>();
                    System.out.print("Retos preferidos (separados por comas, ej: 'caminar, correr'): ");
                    String[] retosArray = sc.nextLine().split(",");
                    for (String reto : retosArray) {
                        retosList.add(reto.trim());
                    }
                    PreferenciasUsuario preferencias = new PreferenciasUsuario(tipoCafe, tamanoTaza, usaAzucar, tipoAzucar, usaLeche, tipoLeche, retosList.toArray(new String[0]));
                    preferenciasController.crearPreferencias(preferencias);
                    MensajeMotivacional mensajePref = mensajeController.generarMensaje(
                        "¡Preferencias guardadas! Conocerte mejor nos ayuda a brindarte una mejor experiencia 🎯",
                        "preferencias",
                        "preferencias_configuradas"
                    );
                    mensajeController.mostrarMensaje(mensajePref);
                    metaController.evaluarMetasCompletadas(usuarioActual.getId());
                break;

                case 4:
                    // Ver preferencias
                    System.out.println("\n===== TUS PREFERENCIAS =====");
                    PreferenciasUsuario pref = preferenciasController.obtenerPreferencias();
                    if (pref != null) {
                        System.out.println("Tipo de café: " + pref.getTipoCafe());
                        System.out.println("Tamaño de taza: " + pref.getTamañoTaza());
                        System.out.println("Usa azúcar: " + (pref.isUsaAzucar() ? "Sí" : "No"));
                        if (pref.isUsaAzucar()) {
                            System.out.println("Tipo de azúcar: " + pref.getTipoAzucar());
                        }
                        System.out.println("Usa leche: " + (pref.isUsaLeche() ? "Sí" : "No"));
                        if (pref.isUsaLeche()) {
                            System.out.println("Tipo de leche: " + pref.getTipoLeche());
                        }
                        System.out.println("Retos preferidos: " + java.util.Arrays.toString(pref.getRetosPreferidos()));
                    }
                break;

                case 5: 
                    // Ver puntos
                    System.out.println("\n===== TUS PUNTOS =====");
                    System.out.println("Puntos acumulados: " + usuarioActual.getPuntos() + " puntos\n");
                break;

                case 6:
                    // Modificar consumo de hoy
                    ConsumoController consumoControllerMod = new ConsumoController(usuarioActual);
                    historial = consumoControllerMod.obtenerHistorialConsumo();
                    
                    if (historial.isEmpty()) {
                        System.out.println("No hay consumo registrado hoy para modificar.\n");
                        break;
                    }

                    System.out.println("Selecciona el número del consumo que deseas modificar:");
                    for (int i = 0; i < historial.size(); i++) {
                        System.out.println((i + 1) + ". " + historial.get(i));
                    }

                    System.out.print("Opción: ");
                    opcion = sc.nextInt();
                    sc.nextLine(); // limpiar buffer

                    if (opcion < 1 || opcion > historial.size()) {
                        System.out.println("Opción inválida.\n");
                        break;
                    }

                    Consumo consumoSeleccionado = historial.get(opcion - 1);

                    System.out.print("¿Qué campo deseas modificar? (tamanotaza, tipoazucar, tipoleche, tipocafe, respuestasextras): ");
                    String campo = sc.nextLine();
                    System.out.print("Nuevo valor: ");
                    String nuevoValor = sc.nextLine();

                    try {
                        consumoSeleccionado.actualizarCampo(campo, nuevoValor);
                        System.out.println("Campo actualizado correctamente.\n");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                break;

                case 7:
                    System.out.println("\n===== ACTUALIZAR USUARIO =====");
                    System.out.print("Nuevo nombre (dejar en blanco para no cambiar): ");
                    String nuevoNombre = sc.nextLine();
                    System.out.print("Nuevo correo (dejar en blanco para no cambiar): ");
                    String nuevoCorreo = sc.nextLine();
                    System.out.print("Nueva contraseña (dejar en blanco para no cambiar): ");
                    String nuevaContrasena = sc.nextLine();
                    String nuevaContrasenaHash = nuevaContrasena.isEmpty() ? null : HashUtil.hashPassword(nuevaContrasena);

                    usuarioController.actualizarUsuario(
                        usuarioActual,
                        nuevoNombre.isEmpty() ? usuarioActual.getNombre() : nuevoNombre,
                        nuevoCorreo.isEmpty() ? usuarioActual.getCorreo() : nuevoCorreo,
                        nuevaContrasenaHash == null ? null : nuevaContrasenaHash
                    );

                    System.out.println("Usuario actualizado correctamente.\n");

                break;

                case 8:
                    System.out.println("\n===== TIENDA DE PREMIOS =====");
                    for (Premio premio : catalogo) {
                        System.out.println("ID: " + premio.getId());
                        System.out.println("Nombre: " + premio.getNombre());
                        System.out.println("Descripción: " + premio.getDescripcion());
                        System.out.println("Puntos requeridos: " + premio.getPuntosRequeridos());
                        System.out.println("Stock disponible: " + premio.getStock());
                        System.out.println("---------------------------");
                    }

                    System.out.print("¿Deseas canjear algún premio? (si/no): ");
                    String respuestaCanjear = sc.nextLine();
                    if (respuestaCanjear.equalsIgnoreCase("si")) {
                        System.out.print("Ingresa el ID del premio que deseas canjear: ");
                        String premioId = sc.nextLine();
                        String resultadoCanje = premioController.canjearPremio(usuarioActual, premioId);
                        System.out.println(resultadoCanje + "\n");

                        if(resultadoCanje.contains("exitosamente")){
                            MensajeMotivacional mensajePremio = mensajeController.generarMensaje(
                                "¡Felicidades por canjear tu premio!",
                                "premio",
                                "premio_canjeado"
                            );
                            mensajeController.mostrarMensaje(mensajePremio);

                            metaController.evaluarMetasCompletadas(usuarioActual.getId());
                        }
                    }
                break;
                case 9:
                    metaController.obtenerMetas(usuarioActual.getId());
                    break;
                case 10:
                    System.out.println("\n===== COMPLETAR META =====");
                    
                    ArrayList<Meta> metasUsuario = usuarioActual.getMetas();
                    ArrayList<Meta> metasPendientes = new ArrayList<>();
                    
                    for (Meta meta : metasUsuario) {
                        if (!meta.isCompletada()) {
                            metasPendientes.add(meta);
                        }
                    }
                    
                    if (metasPendientes.isEmpty()) {
                        System.out.println("¡Felicidades! No tienes metas pendientes.\n");
                        break;
                    }
                    
                    System.out.println("Metas pendientes:");
                    for (int i = 0; i < metasPendientes.size(); i++) {
                        Meta meta = metasPendientes.get(i);
                        System.out.printf("%d. [%s] %s - %d puntos\n", 
                            (i + 1), meta.getId(), meta.getDescripcion(), meta.getPuntosObjetivo());
                    }
                    
                    System.out.print("\nIngresa el ID de la meta que completaste: ");
                    String metaId = sc.nextLine();
                    
                    metaController.completarMeta(usuarioActual.getId(), metaId);

                    break;

                case 11:
                    System.out.println("\n===== HISTORIAL DE CANJES =====");
                    ArrayList<Canje> historialCanjes = premioController.obtenerHistorialCanjes(usuarioActual.getId());
                    if (historialCanjes.isEmpty()) {
                        System.out.println("No has canjeado ningún premio aún.\n");
                    } else {
                        for (Canje c : historialCanjes) {
                            System.out.println("╔══════════════════════════════════╗");
                            System.out.printf("║ Fecha del canje: %-16s ║\n", c.getFechaCanje());
                            System.out.printf("║ Premio: %-23s ║\n", c.getPremioNombre());
                            System.out.printf("║ Puntos usados: %-17d ║\n", c.getPuntosUsados());
                            System.out.println("╚══════════════════════════════════╝\n");
                        }
                    }
                    break;

                case 12:
                    System.out.println("Cerrando sesión...\n");
                     MensajeMotivacional mensajeDespedida = mensajeController.generarMensaje(
                        "¡Hasta pronto, " + usuarioActual.getNombre() + "! Recuerda: cada día es una oportunidad para mejorar 🌟",
                        "despedida",
                        "cierre_sesion"
                    );
                    mensajeController.mostrarMensaje(mensajeDespedida);

                    usuarioActual = null;
                    break;

                default:
                    System.out.println("Opción inválida. Intente de nuevo.\n");
            }
        } while (opcion != 12);
    }

    
}
