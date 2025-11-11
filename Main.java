import java.util.Scanner;
import java.util.Date;
import java.util.UUID;
import java.time.LocalDate;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

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

                        usuarioController.registrarUsuario(id, nombre, correo, contrasenaHash);

                        System.out.println("¡Registro exitoso! Bienvenido, " + nombre + ".\n");
                        MensajeMotivacional mensajeBienvenida = mensajeController.generarMensaje(
                            "¡Bienvenido a Movaccino, " + nombre + "! Estamos emocionados de acompañarte en este viaje 🌟",
                            "registro",
                            "nuevo_usuario"
                        );
                        mensajeController.mostrarMensaje(mensajeBienvenida);

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
            System.out.println("2. Reportes de consumo (con filtros)");
            System.out.println("3. Configurar preferencias");
            System.out.println("4. Ver mis preferencias");
            System.out.println("5. Ver mis puntos actuales");
            System.out.println("6. Ver mis puntos totales ganados"); // NUEVA OPCIÓN
            System.out.println("7. Modificar un campo de mi consumo de hoy");
            System.out.println("8. Actualizar usuario");
            System.out.println("9. Ver tienda de premios");
            System.out.println("10. Ver mis metas");
            System.out.println("11. Completar una meta");
            System.out.println("12. Ver historial de canjes");
            System.out.println("13. Cerrar sesión");
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
                        System.out.println("¿Deseas usar tus preferencias habituales?");
                        System.out.println("1. Sí (registrar según preferencias)");
                        System.out.println("2. No (ingresar un café diferente)");
                        System.out.print("Selecciona una opción: ");
                        int subOpcion = Integer.parseInt(sc.nextLine());

                        Date fecha = new Date();
                        String tamanoTaza = "", tipoAzucar = "", tipoLeche = "", tipoCafe = "", respuestasExtras = "";

                        if (subOpcion == 1) {
                            // Usar preferencias guardadas
                            PreferenciasUsuario pref = usuarioActual.getPreferencias();

                            if (pref == null) {
                                System.out.println("\nNo tienes preferencias guardadas. Debes configurarlas primero.\n");
                                break;
                            }

                            // Validar que las preferencias tengan datos
                            if (pref.getTipoCafe() == null || pref.getTipoCafe().isEmpty() ||
                                pref.getTamañoTaza() == null || pref.getTamañoTaza().isEmpty()) {
                                System.out.println("\nTus preferencias están incompletas. Configúralas antes de usar esta opción.\n");
                                break; 
                            }

                            tamanoTaza = pref.getTamañoTaza();
                            tipoAzucar = pref.getTipoAzucar();
                            tipoLeche = pref.getTipoLeche();
                            tipoCafe = pref.getTipoCafe();
                            respuestasExtras = "Consumo habitual";

                            System.out.println("\n☕ Se ha registrado tu consumo habitual.");
                        } 
                        else if (subOpcion == 2) {
                            // Ingreso manual (consumo especial)
                            System.out.print("Tamaño de taza (Oz): ");
                            tamanoTaza = sc.nextLine();

                            System.out.print("Tipo de azúcar: ");
                            tipoAzucar = sc.nextLine();

                            System.out.print("Tipo de leche: ");
                            tipoLeche = sc.nextLine();

                            System.out.print("Tipo de café: ");
                            tipoCafe = sc.nextLine();

                            System.out.print("¿Algún comentario extra? ");
                            respuestasExtras = sc.nextLine();

                            System.out.println("\n☕ Se ha registrado tu consumo especial.");
                        } 
                        else {
                            System.out.println("Opción inválida.");
                            break; // volver al menú principal
                        }

                        // Validación final antes de guardar
                        if (tamanoTaza.isEmpty() || tipoCafe.isEmpty()) {
                            System.out.println("\n⚠️ Error: no se registró el consumo porque faltan datos.\n");
                            break;
                        }

                        // Guardar el consumo
                        ConsumoController consumoController = new ConsumoController(usuarioActual);
                        consumoController.guardarConsumoDiario(fecha, tamanoTaza, tipoAzucar, tipoLeche, tipoCafe, respuestasExtras);

                        System.out.println("✅ ¡Consumo registrado exitosamente!\n");

                        mensajeController.mostrarMensaje(
                            mensajeController.obtenerMensajeRegistroConsumo()
                        );
                        metaController.evaluarMetasCompletadas(usuarioActual.getId());

                    } catch (Exception e) {
                        System.out.println("Error al registrar consumo: " + e.getMessage());
                    }
                break;

                case 2:
                    // Submenú de reportes con filtros
                    System.out.println("\n===== REPORTES DE CONSUMO =====");
                    System.out.println("1. Ver todo el historial");
                    System.out.println("2. Filtrar por fecha específica");
                    System.out.println("3. Filtrar por rango de fechas");
                    System.out.println("4. Filtrar por tipo de café");
                    System.out.println("5. Filtrar por tamaño de taza");
                    System.out.print("Elige una opción: ");
                    
                    try {
                        int opcionReporte = Integer.parseInt(sc.nextLine());
                        ConsumoController consumoController = new ConsumoController(usuarioActual);
                        ArrayList<Consumo> consumosFiltrados = new ArrayList<>();
                        
                        switch (opcionReporte) {
                            case 1:
                                // Ver todo el historial (comportamiento original)
                                consumosFiltrados = consumoController.obtenerHistorialConsumo();
                                break;
                                
                            case 2:
                                // Filtrar por fecha específica
                                System.out.print("Ingresa la fecha (dd/MM/yyyy): ");
                                String fechaStr = sc.nextLine();
                                try {
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                    Date fecha = sdf.parse(fechaStr);
                                    consumosFiltrados = consumoController.obtenerPorFecha(fecha);
                                } catch (Exception e) {
                                    System.out.println("Formato de fecha inválido. Usa dd/MM/yyyy");
                                    break;
                                }
                                break;
                                
                            case 3:
                                // Filtrar por rango de fechas
                                System.out.print("Fecha inicio (dd/MM/yyyy): ");
                                String inicioStr = sc.nextLine();
                                System.out.print("Fecha fin (dd/MM/yyyy): ");
                                String finStr = sc.nextLine();
                                try {
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                    Date inicio = sdf.parse(inicioStr);
                                    Date fin = sdf.parse(finStr);
                                    consumosFiltrados = consumoController.obtenerPorRangoFechas(inicio, fin);
                                } catch (Exception e) {
                                    System.out.println("Formato de fecha inválido. Usa dd/MM/yyyy");
                                    break;
                                }
                                break;
                                
                            case 4:
                                // Filtrar por tipo de café
                                System.out.print("Ingresa el tipo de café: ");
                                String tipoCafe = sc.nextLine();
                                consumosFiltrados = consumoController.obtenerPorTipoCafe(tipoCafe);
                                break;
                                
                            case 5:
                                // Filtrar por tamaño de taza
                                System.out.print("Ingresa el tamaño de taza: ");
                                String tamanoTaza = sc.nextLine();
                                consumosFiltrados = consumoController.obtenerPorTamanoTaza(tamanoTaza);
                                break;
                                
                            default:
                                System.out.println("Opción inválida.");
                                break;
                        }
                        
                        // Mostrar resultados filtrados
                        if (consumosFiltrados.isEmpty()) {
                            System.out.println("No hay consumos que coincidan con los criterios.\n");
                        } else {
                            System.out.println("\n======= CONSUMOS FILTRADOS =======");
                            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                            for (Consumo consumo : consumosFiltrados) {
                                System.out.println("╔══════════════════════════════════╗");
                                System.out.println("║   ☕  Registro de Consumo        ║");
                                System.out.println("╠══════════════════════════════════╣");
                                System.out.printf("║ Fecha: %-24s ║\n", formatoFecha.format(consumo.getFecha()));
                                System.out.printf("║ Tamaño de taza: %-16s ║\n", consumo.getTamañoTaza());
                                System.out.printf("║ Tipo de azúcar: %-16s ║\n", consumo.getTipoAzucar());
                                System.out.printf("║ Tipo de leche: %-16s ║\n", consumo.getTipoLeche());
                                System.out.printf("║ Tipo de café: %-17s ║\n", consumo.getTipoCafe());
                                System.out.printf("║ Extra: %-24s ║\n", consumo.getRespuestasExtras());
                                System.out.println("╚══════════════════════════════════╝\n");
                            }
                            
                            // Mensaje motivacional solo para el caso de ver todo el historial
                            if (opcionReporte == 1 && consumosFiltrados.size() >= 7) {
                                MensajeMotivacional mensaje = mensajeController.generarMensaje(
                                    "¡Llevas " + consumosFiltrados.size() + " registros! Tu constancia es admirable 📈",
                                    "progreso",
                                    "consumos >= 7"
                                );
                                mensajeController.mostrarMensaje(mensaje);
                            }
                        }
                        
                    } catch (NumberFormatException e) {
                        System.out.println("Por favor, ingresa un número válido.");
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
                    
                    usuarioActual.setPreferencias(preferencias);
                    
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
                    // Ver puntos actuales
                    System.out.println("\n===== TUS PUNTOS ACTUALES =====");
                    System.out.println("Puntos disponibles: " + usuarioActual.getPuntos() + " puntos");
                    System.out.println("Puntos totales ganados: " + usuarioActual.getPuntosTotalesGanados() + " puntos");
                    System.out.println("Puntos canjeados: " + (usuarioActual.getPuntosTotalesGanados() - usuarioActual.getPuntos()) + " puntos\n");
                break;

                case 6:
                    // NUEVO: Ver puntos totales ganados con historial
                    usuarioActual.mostrarHistorialPuntos();
                break;

                case 7:
                    // Modificar consumo de hoy
                    ConsumoController consumoControllerMod = new ConsumoController(usuarioActual);
                    ArrayList<Consumo> historial = consumoControllerMod.obtenerHistorialConsumo();
                    
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

                    // Permitir seleccionar varios campos por número para editar de una sola vez
                    System.out.println("¿Qué campos deseas modificar? Selecciona los números separados por comas:");
                    System.out.println("1. Tamaño de taza");
                    System.out.println("2. Tipo de azúcar");
                    System.out.println("3. Tipo de leche");
                    System.out.println("4. Tipo de café");
                    System.out.println("5. Comentarios extras");
                    System.out.print("Selecciona (ej: 1,4 para tamaño y tipo de café): ");
                    String seleccion = sc.nextLine();

                    String[] partes = seleccion.split(",");
                    for (String p : partes) {
                        p = p.trim();
                        if (p.isEmpty()) continue;
                        int num;
                        try {
                            num = Integer.parseInt(p);
                        } catch (NumberFormatException nfe) {
                            System.out.println("Opción inválida: " + p + " (no es un número). Se omite.");
                            continue;
                        }

                        String campoClave;
                        String etiqueta;
                        switch (num) {
                            case 1:
                                campoClave = "tamanotaza";
                                etiqueta = "Tamaño de taza (Oz)";
                                break;
                            case 2:
                                campoClave = "tipoazucar";
                                etiqueta = "Tipo de azúcar";
                                break;
                            case 3:
                                campoClave = "tipoleche";
                                etiqueta = "Tipo de leche";
                                break;
                            case 4:
                                campoClave = "tipocafe";
                                etiqueta = "Tipo de café";
                                break;
                            case 5:
                                campoClave = "respuestasextras";
                                etiqueta = "Comentarios extras";
                                break;
                            default:
                                System.out.println("Opción inválida: " + num + ". Se omite.");
                                continue;
                        }

                        System.out.print("Nuevo valor para " + etiqueta + ": ");
                        String nuevoValor = sc.nextLine();
                        try {
                            consumoSeleccionado.actualizarCampo(campoClave, nuevoValor);
                            System.out.println("Campo '" + etiqueta + "' actualizado correctamente.");
                        } catch (IllegalArgumentException e) {
                            System.out.println("No se pudo actualizar '" + etiqueta + "': " + e.getMessage());
                        }
                    }
                    System.out.println();
                break;

                case 8:
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

                case 9:
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
                case 10:
                    metaController.obtenerMetas(usuarioActual.getId());
                    break;
                case 11:
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

                case 12:
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

                case 13:
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
        } while (opcion != 13);
    }

    
}