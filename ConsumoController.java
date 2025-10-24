import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class ConsumoController {
    private ArrayList<Consumo> consumos;
    private Usuario usuario;

    public ConsumoController(Usuario usuario) {
        this.consumos = new ArrayList<>();
        this.usuario = usuario;
    }

    // Guardar un nuevo consumo
    public void guardarConsumoDiario(Date fecha, String tamanoTaza, String tipoAzucar, String tipoLeche, String tipoCafe, String respuestasExtras) {
        try {
            Consumo nuevoConsumo = new Consumo(fecha, tamanoTaza, tipoAzucar, tipoLeche, tipoCafe, respuestasExtras);
            consumos.add(nuevoConsumo);
            usuario.agregarConsumo(nuevoConsumo);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el consumo diario: " + e.getMessage());
        }
    }

    // Obtener todo el historial de consumos
    public ArrayList<Consumo> obtenerHistorialConsumo() {
        return usuario.getConsumos();
    }

    // Obtener resumen de consumos
    public String obtenerResumenConsumo(){
        return "----Resumen de Consumos----\n" +
               "Total de consumos: " + consumos.size() + "\n" +
               "Puntos acumulados: " + usuario.getPuntos() + "\n";
    }

    // Evaluar puntos del usuario según consumos
    public void evaluarPuntos() {
        try {
            int puntosGanados = consumos.size() * 10;
            usuario.setPuntos(usuario.getPuntos() + puntosGanados);
        } catch (Exception e) {
            throw new RuntimeException("Error al evaluar puntos: " + e.getMessage());
        }
    }

    // ================= FILTROS =================

    // Obtener consumos por fecha exacta
    public ArrayList<Consumo> obtenerPorFecha(Date fecha) {
        return consumos.stream()
                .filter(c -> c.getFecha().equals(fecha))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    // Obtener consumos por rango de fechas
    public ArrayList<Consumo> obtenerPorRangoFechas(Date inicio, Date fin) {
        return consumos.stream()
                .filter(c -> !c.getFecha().before(inicio) && !c.getFecha().after(fin))
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
