import java.util.Scanner;
import java.util.Date;
import java.util.UUID;
import java.time.LocalDate;
public class Main{
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        UsuarioController usuarioController = new UsuarioController();
        Usuario usuarioActual = null;

        System.out.println("==============================================");
        System.out.println("   ¡Bienvenido a Movaccino, la app que te ayuda");
        System.out.println("         a reducir tu consumo de café!");
        System.out.println("==============================================");
        System.out.println("           ( (");
        System.out.println("            ) )");
        System.out.println("         ........");
        System.out.println("         |      |]");
        System.out.println("         \\      /");
        System.out.println("          `----'");
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
                        System.out.print("Correo: ");
                        String correo = sc.nextLine();
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
                    } catch (Exception e){
                        System.out.println("Error al registrar usuario: " + e.getMessage());
                    }
                    
                    break;
                case 2:
                    try{
                        System.out.println("Iniciar sesión");
                        System.out.print("Correo: ");
                        String correoLogin = sc.nextLine();
                        System.out.print("Contraseña: ");
                        String contrasenaLogin = sc.nextLine();
                        String contraseñaLoginHash = HashUtil.hashPassword(contrasenaLogin);
                        usuarioActual = usuarioController.login(correoLogin, contraseñaLoginHash);
                        System.out.println("¡Inicio de sesión exitoso! Bienvenido, " + usuarioActual.getNombre() + ".\n");
                        mostrarMenuUsuario(sc, usuarioActual);
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

    public static void mostrarMenuUsuario(Scanner sc, Usuario usuarioActual){
        int opcion;
        do{
          System.out.println("   ^    ^  ");
          System.out.println("  ( ; . ; ) つ ☕ ");
          System.out.println("  (      ⎠");
          System.out.println("  (        )   ");
          System.out.println(" (   ) (   )  ");
          System.out.println("  ^^    ^^     ");
            System.out.println("1. Registrar consumo diario de café");
            System.out.println("2. Ver historial de consumo");
            System.out.println("3. Cerrar sesión");
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
                    try {
                        Date fecha = new Date();
                        System.out.print("Tamaño de taza: ");
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
                    } catch (Exception e) {
                        System.out.println("Error al registrar consumo: " + e.getMessage());
                    }
                    break;
                case 2:
                    
                    break;
                case 3:
                    System.out.println("Cerrando sesión...\n");
                    usuarioActual = null;
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.\n");
            }
        }while(opcion != 3);
    }
}