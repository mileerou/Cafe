import java.util.Scanner;
import java.util.Date;
import java.util.UUID;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    private static UsuarioController usuarioController = new UsuarioController();
    private static MensajeController mensajeController = new MensajeController();

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

    public static void mostrarMenuUsuario(Scanner sc, Usuario usuarioActual) {
        int opcion;
        PreferenciasUsuarioController preferenciasController = new PreferenciasUsuarioController();
        do {
            System.out.println("   ^    ^  ");
            System.out.println("  ( ; . ; ) つ ☕ ");
            System.out.println("  (     ⎠");
            System.out.println("  (      )  ");
            System.out.println(" (   ) (   )  ");
            System.out.println("  ^^    ^^   ");
            System.out.println("1. Registrar consumo diario de café");
            System.out.println("2. Ver historial de consumo");
            System.out.println("3. Configurar preferencias");
            System.out.println("4. Ver mis preferencias");
            System.out.println("5. Modificar un campo de mi consumo de hoy");
            System.out.println("6. Cerrar sesión");
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

                case 6:
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
        } while (opcion != 6);
    }
}
