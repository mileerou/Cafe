public class PreferenciasUsuario {
    private String tipoCafe;
    private String tamanoTaza;
    private boolean usaAzucar;
    private String tipoAzucar;
    private boolean usaLeche;
    private String tipoLeche;
    private String[] retosPreferidos;

    // Constructor
    public PreferenciasUsuario(String tipoCafe, String tamanoTaza, boolean usaAzucar, String tipoAzucar, boolean usaLeche, String tipoLeche, String[] retosPreferidos) {
        this.tipoCafe = tipoCafe;
        this.tamanoTaza = tamanoTaza;
        this.usaAzucar = usaAzucar;
        this.tipoAzucar = tipoAzucar;
        this.usaLeche = usaLeche;
        this.tipoLeche = tipoLeche;
        this.retosPreferidos = retosPreferidos;
    }
    
     // Getters
    public String getTipoCafe() {
        return tipoCafe;
    }

    public String getTamañoTaza() {
        return tamanoTaza;
    }

    public boolean isUsaAzucar() {
        return usaAzucar;
    }   

    public String getTipoAzucar() {
        return tipoAzucar;
    }

    public boolean isUsaLeche() {
        return usaLeche;
    }

    public String getTipoLeche() {
        return tipoLeche;
    }

    public String[] getRetosPreferidos() {
        return retosPreferidos;
    }

    // Setters
    public void setTipoCafe(String tipoCafe) {
        this.tipoCafe = tipoCafe;
    }

    public void setTamañoTaza(String tamañoTaza) {
        this.tamanoTaza = tamañoTaza;
    }

    public void setUsaAzucar(boolean usaAzucar) {
        this.usaAzucar = usaAzucar;
    }

    public void setTipoAzucar(String tipoAzucar) {
        this.tipoAzucar = tipoAzucar;
    }

    public void setUsaLeche(boolean usaLeche) {
        this.usaLeche = usaLeche;
    }

    public void setTipoLeche(String tipoLeche) {
        this.tipoLeche = tipoLeche;
    }

    public void setRetosPreferidos(String[] retosPreferidos) {
        this.retosPreferidos = retosPreferidos;
    }

    @Override
    public String toString(){
        return "Tipo de café: " + tipoCafe + "\n" +
               "Tamaño de taza: " + tamanoTaza + "\n" +
               "Usa azúcar: " + (usaAzucar ? "Si" : "No") + "\n" +
               "Tipo de azúcar: " + tipoAzucar + "\n" +
               "Usa leche: " + (usaLeche ? "Si" : "No") + "\n" +
               "Tipo de leche: " + tipoLeche + "\n" +
               "Retos preferidos: " + (retosPreferidos.length > 0 ? String.join(", ", retosPreferidos) : "Ninguno");
    }
}
