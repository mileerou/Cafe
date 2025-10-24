import java.util.ArrayList;

public class TiendaVirtual {
    private PremioController premioController;

    public TiendaVirtual(PremioController premioController){
        this.premioController = premioController;
        inicializarPremios();
    }

    public void inicializarPremios(){
        ArrayList<Premio> premiosIniciales = new ArrayList<>();

        premiosIniciales.add(new Premio("1", "Café Mocha 12 oz", "Delicioso café Mocha de 12 onzas", 60, 12));
        premiosIniciales.add(new Premio("2", "Termo de acero", "Termo tamaño grande de color escogido", 250, 3 ));
        premiosIniciales.add(new Premio("3", "Cupón Descuento 20%", "Cupón para un 20% de descuento en tu próxima compra en cafeterías participantes", 50, 10));
        premiosIniciales.add(new Premio("4", "Cupón para consumo de Q40", "Cupón para un consumo máximo de Q40 en cafeterías participantes", 100, 5));
        premiosIniciales.add(new Premio("5", "Pie de pecanas", "Delicioso pie de pecanas para compartir", 150, 4));
        premiosIniciales.add(new Premio("6", "Vaso reutilizable", "Vaso tamaño mediano de color escogido", 120, 6));
        premiosIniciales.add(new Premio("7", "Sticker de temporada", "Sticker edición limitada de temporada", 30, 20));

        for (Premio premio : premiosIniciales) {
            premioController.agregarPremio(premio);
        }
    }

    public ArrayList<Premio> obtenerCatalogo(){
        return premioController.getPremios();
    }
}
