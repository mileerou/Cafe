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

    // Getters
    public Date getFecha() {
        return fecha;
    }

    public String getTamañoTaza() {
        return tamanoTaza;
    }

    public String getTipoAzucar() {
        return tipoAzucar;
    }

    public String getTipoLeche() {
        return tipoLeche;
    }

    public String getTipoCafe() {
        return tipoCafe;
    }

    public String getRespuestasExtras() {
        return respuestasExtras;
    }

    // Setters

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setTamañoTaza(String tamañoTaza) {
        this.tamanoTaza = tamañoTaza;
    }

    public void setTipoAzucar(String tipoAzucar) {
        this.tipoAzucar = tipoAzucar;
    }

    public void setTipoLeche(String tipoLeche) {
        this.tipoLeche = tipoLeche;
    }

    public void setTipoCafe(String tipoCafe) {
        this.tipoCafe = tipoCafe;
    }

    public void setRespuestasExtras(String respuestasExtras) {
        this.respuestasExtras = respuestasExtras;
    }

    @Override
    public String toString() {
        return "Consumo{" +
                "fecha=" + fecha +
                ", tamañoTaza='" + tamanoTaza + '\'' +
                ", tipoAzucar='" + tipoAzucar + '\'' +
                ", tipoLeche='" + tipoLeche + '\'' +
                ", tipoCafe='" + tipoCafe + '\'' +
                ", respuestasExtras='" + respuestasExtras + '\'' +
                '}';
    }
}
