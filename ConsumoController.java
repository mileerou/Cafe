import java.util.ArrayList;
import java.util.Scanner;

public class ConsumoController {
    private ArrayList<Consumo> consumos;
    private Usuario usuario;

    public ConsumoController(Usuario usuario) {
        this.consumos = new ArrayList<>();
        this.usuario = usuario;
    }

    public void guardarConsumoDiario(){
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
            throw new RuntimeException("Error al guardar el consumo diario: " + e.getMessage());
        }
    }

    public String obtenerHistorialConsumo() {
        StringBuilder historial = new StringBuilder();
        if (consumos.isEmpty()) {
            historial.append("No hay consumos registrados.\n");
            return historial.toString();
        }
        historial.append("Historial de Consumos:\n");
        for (Consumo consumo : consumos) {
            historial.append(consumo.toString()).append("\n");
            historial.append("---------------------\n");
        }
        return historial.toString();
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
            
        } catch (Exception e) {
            throw new RuntimeException("Error al evaluar puntos: " + e.getMessage());
        }
    }
}
