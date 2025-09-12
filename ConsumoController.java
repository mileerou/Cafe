import java.util.ArrayList;
import java.util.Scanner;

public class ConsumoController {
    private ArrayList<Consumo> consumos;
    private Usuario usuario; // Asegúrate de importar la clase Usuario

    public ConsumoController(Usuario usuario) {
        this.consumos = new ArrayList<>();
        this.usuario = usuario;
    }

    public void guardarConsumoDiario(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese el tamaño de la taza (Pequeña, Mediana, Grande): ");
        String tamanoTaza = sc.nextLine();

        System.out.println("¿Usa azúcar? (si/no): ");
        String usaAzucar = sc.nextLine().toLowerCase();
        String tipoAzucar = "N/A";
        if (usaAzucar.equals("si")) {
            System.out.println("Ingrese el tipo de azúcar (Blanca, Morena, Stevia, Miel): ");
            tipoAzucar = sc.nextLine();
        }

        System.out.println("¿Usa leche? (si/no): ");
        String usaLeche = sc.nextLine().toLowerCase();
        String tipoLeche = "N/A";
        if (usaLeche.equals("si")) {
            System.out.println("Ingrese el tipo de leche (Entera, Descremada, Almendra, Avena, Soja): ");
            tipoLeche = sc.nextLine();
        }

        System.out.println("Ingrese el tipo de café (Expreso, Americano, Latte, Capuchino, Mocha): ");
        String tipoCafe = sc.nextLine();

        System.out.println("¿Desea agregar respuestas extras? (si/no): ");
        String respuestaExtra = sc.nextLine().toLowerCase();
        String respuestasExtras = "N/A";
        if (respuestaExtra.equals("si")) {
            System.out.println("Ingrese sus respuestas extras: ");
            respuestasExtras = sc.nextLine();
        }

        Consumo nuevoConsumo = new Consumo(new java.util.Date(), tamanoTaza, tipoAzucar, tipoLeche, tipoCafe, respuestasExtras);
        consumos.add(nuevoConsumo);
        System.out.println("Consumo diario guardado exitosamente.");
    }

    public void obtenerHistorialConsumo(){
        if (consumos.isEmpty()) {
            System.out.println("No hay consumos registrados.");
            return;
        }
        System.out.println("Historial de Consumos:");
        for (Consumo consumo : consumos) {
            System.out.println(consumo);
            System.out.println("---------------------");
        }
    }

    public String obtenerResumenConsumo(){
        return "----Resumen de Consumos----\n" +
               "Total de consumos: " + consumos.size() + "\n" +
               "Puntos acumulados: " + usuario.getPuntos() + "\n";

    }

    public void evaluarPuntos(){
        int puntosGanados = consumos.size() * 10;
        usuario.setPuntos(usuario.getPuntos() + puntosGanados);
        System.out.println("Has ganado " + puntosGanados + " puntos. Total de puntos: " + usuario.getPuntos());
    }
}
