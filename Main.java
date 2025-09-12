import java.util.Scanner;
import java.util.Date;

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

        do{
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); 

            switch(opcion){
                case 1:
                    
                    break;
                case 2:
                    
                    break;
                case 3:
                    System.out.println("Gracias por usar Movaccino. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.\n");
            }
        }while(opcion != 3);
    }
}