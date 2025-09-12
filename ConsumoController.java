import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

public class ConsumoController {
    private ArrayList<Consumo> consumos;
    private Usuario usuario;

    public ConsumoController(Usuario usuario) {
        this.consumos = new ArrayList<>();
        this.usuario = usuario;
    }

    public void guardarConsumoDiario(Date fecha, String tamanoTaza, String tipoAzucar, String tipoLeche, String tipoCafe, String respuestasExtras) {
        try {
            Consumo nuevoConsumo = new Consumo(fecha, tamanoTaza, tipoAzucar, tipoLeche, tipoCafe, respuestasExtras);
            consumos.add(nuevoConsumo);
            usuario.agregarConsumo(nuevoConsumo); 
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el consumo diario: " + e.getMessage());
        }
    }
    /*
    Comentado porque en Main el método necesitaba un arraylist de Consumo y no de String
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
    }*/
    public ArrayList<Consumo> obtenerHistorialConsumo() {
        return usuario.getConsumos();
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
