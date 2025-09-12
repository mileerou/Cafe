import java.io.Serializable; //permitirá guardar el estado del objeto en otro
import java.util.ArrayList;

public class PreferenciasUsuarioController{
    
    private String tipoCafe;
    private String tamanoTaza;
    private boolean usaAzucar;
    private String tipoAzucar;
    private boolean usaLeche;
    private String tipoLeche;
    private ArrayList<String> retosPreferidos;

    public PreferenciasUsuario(String tipoCafe, String tamanoTaza, boolean usaAzucar,
                            String tipoAzucar, boolean usaLeche, String tipoLeche,
                            ArrayList<String> retosPreferidos) {
        this.tipoCafe = tipoCafe;
        this.tamanoTaza = tamanoTaza;
        this.usaAzucar = usaAzucar;
        this.tipoAzucar = tipoAzucar;
        this.usaLeche = usaLeche;
        this.tipoLeche = tipoLeche;
        this.retosPreferidos = retosPreferidos;
    }

        public String getTipoCafe() {
        return tipoCafe;
    }

    public void setTipoCafe(String tipoCafe) {
        this.tipoCafe = tipoCafe;
    }

    public String getTamanoTaza() {
        return tamanoTaza;
    }

    public void setTamanoTaza(String tamanoTaza) {
        this.tamanoTaza = tamanoTaza;
    }

    public boolean isUsaAzucar() {
        return usaAzucar;
    }

    public void setUsaAzucar(boolean usaAzucar) {
        this.usaAzucar = usaAzucar;
    }

    public String getTipoAzucar() {
        return tipoAzucar;
    }

    public void setTipoAzucar(String tipoAzucar) {
        this.tipoAzucar = tipoAzucar;
    }

    public boolean isUsaLeche() {
        return usaLeche;
    }

    public void setUsaLeche(boolean usaLeche) {
        this.usaLeche = usaLeche;
    }

    public String getTipoLeche() {
        return tipoLeche;
    }

    public void setTipoLeche(String tipoLeche) {
        this.tipoLeche = tipoLeche;
    }

    public ArrayList<String> getRetosPreferidos() {
        return retosPreferidos;
    }

    public void setRetosPreferidos(ArrayList<String> retosPreferidos) {
        this.retosPreferidos = retosPreferidos;
    }

    @Override
    public String toString() {
        return "PreferenciasUsuario{" +
                "tipoCafe='" + tipoCafe + '\'' +
                ", tamanoTaza='" + tamanoTaza + '\'' +
                ", usaAzucar=" + usaAzucar +
                ", tipoAzucar='" + tipoAzucar + '\'' +
                ", usaLeche=" + usaLeche +
                ", tipoLeche='" + tipoLeche + '\'' +
                ", retosPreferidos=" + retosPreferidos +
                '}';
    }
}