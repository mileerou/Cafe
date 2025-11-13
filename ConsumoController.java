import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class ConsumoController {
    private Usuario usuario;
    private ConsumoDAO consumoDAO;
    private UsuarioController usuarioController;

    public ConsumoController(Usuario usuario) {
        this.usuario = usuario;
        this.consumoDAO = new ConsumoDAO();
        this.usuarioController = new UsuarioController();
    }

    public void guardarConsumoDiario(Date fecha, String tamanoTaza, String tipoAzucar, String tipoLeche, String tipoCafe, String respuestasExtras) {
        try {
            Consumo nuevoConsumo = new Consumo(fecha, tamanoTaza, tipoAzucar, tipoLeche, tipoCafe, respuestasExtras);
            
            // Guardar en memoria del usuario
            usuario.agregarConsumo(nuevoConsumo);
            
            // Guardar en base de datos
            consumoDAO.insertarConsumo(usuario.getId(), nuevoConsumo);
            
            // NUEVO: Registrar puntos por consumo
            int puntosPorConsumo = 10; // 10 puntos por cada consumo registrado
            usuario.registrarPuntosGanados(puntosPorConsumo, "consumo", 
                "Registro de consumo: " + tipoCafe + " (" + tamanoTaza + "oz)");
            
            // Sumar puntos al usuario
            usuarioController.sumarPuntosConHistorial(usuario, puntosPorConsumo);
            
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el consumo diario: " + e.getMessage());
        }
    }

    public ArrayList<Consumo> obtenerHistorialConsumo() {
        return usuario.getConsumos();
    }

    public String obtenerResumenConsumo() {
        return "----Resumen de Consumos----\n" +
               "Total de consumos: " + usuario.getConsumos().size() + "\n" +
               "Puntos acumulados: " + usuario.getPuntos() + "\n";
    }

    // ================= FILTROS =================

    public ArrayList<Consumo> obtenerPorFecha(Date fecha) {
        return usuario.getConsumos().stream()
                .filter(c -> c.getFecha().equals(fecha))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Consumo> obtenerPorRangoFechas(Date inicio, Date fin) {
        return usuario.getConsumos().stream()
                .filter(c -> !c.getFecha().before(inicio) && !c.getFecha().after(fin))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Consumo> obtenerPorTipoCafe(String tipoCafe) {
        return usuario.getConsumos().stream()
                .filter(c -> c.getTipoCafe().equalsIgnoreCase(tipoCafe))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Consumo> obtenerPorTamanoTaza(String tamanoTaza) {
        return usuario.getConsumos().stream()
                .filter(c -> c.getTamañoTaza().equalsIgnoreCase(tamanoTaza))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}