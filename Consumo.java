import java.util.Date;

public class Consumo {
    private Date fecha;
    private String tamanoTaza;
    private String tipoAzucar;
    private String tipoLeche;
    private String tipoCafe;
    private String respuestasExtras;

    // Constructor
    public Consumo(Date fecha, String tamanoTaza, String tipoAzucar, String tipoLeche, String tipoCafe, String respuestasExtras) {
        this.fecha = fecha;
        this.tamanoTaza = tamanoTaza;
        this.tipoAzucar = tipoAzucar;
        this.tipoLeche = tipoLeche;
        this.tipoCafe = tipoCafe;
        this.respuestasExtras = respuestasExtras;
    }
}
