import java.util.ArrayList;
import java.util.Scanner;

public class ConsumoController {
    private ArrayList<Consumo> consumos;
    private Usuario usuario;

    public ConsumoController(Usuario usuario) {
        this.consumos = new ArrayList<>();
        this.usuario = usuario;
    }

    public void guardarConsumoDiario() {
        Scanner sc = new Scanner(System.in);
        String tamanoTaza = "";
        String tipoAzucar = "N/A";
        String tipoLeche = "N/A";
        String tipoCafe = "";
        String respuestasExtras = "N/A";

        try {
            tamanoTaza = sc.nextLine();

            String usaAzucar = sc.nextLine().toLowerCase();
            if (usaAzucar.equals("si")) {
                tipoAzucar = sc.nextLine();
            }

            String usaLeche = sc.nextLine().toLowerCase();
            if (usaLeche.equals("si")) {
                tipoLeche = sc.nextLine();
            }

            tipoCafe = sc.nextLine();

            String respuestaExtra = sc.nextLine().toLowerCase();
            if (respuestaExtra.equals("si")) {
                respuestasExtras = sc.nextLine();
            }

            Consumo nuevoConsumo = new Consumo(new java.util.Date(), tamanoTaza, tipoAzucar, tipoLeche, tipoCafe, respuestasExtras);
            consumos.add(nuevoConsumo);

        } catch (Exception e) {
            // Handle input errors or unexpected exceptions
            throw new RuntimeException("Error al guardar el consumo diario: " + e.getMessage());
        }
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

    public void evaluarPuntos() {
        try {
            int puntosGanados = consumos.size() * 10;
            usuario.setPuntos(usuario.getPuntos() + puntosGanados);
            // Instead of printing, you could return the points or update a log, etc.
        } catch (Exception e) {
            throw new RuntimeException("Error al evaluar puntos: " + e.getMessage());
        }
    }
}
