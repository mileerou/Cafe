public class PreferenciasUsuario {
    private String tipoCafe;
    private String tamañoTaza;
    private boolean usaAzucar;
    private String tipoAzucar;
    private boolean usaLeche;
    private String tipoLeche;
    private String[] retosPreferidos;

    // Constructor con valores por defecto
    public PreferenciasUsuario(String tipoCafe, String tamañoTaza, boolean usaAzucar, String tipoAzucar, boolean usaLeche, String tipoLeche, String[] retosPreferidos) {
        this.tipoCafe = (tipoCafe != null) ? tipoCafe : "Americano";
        this.tamañoTaza = (tamañoTaza != null) ? tamañoTaza : "Mediana";
        this.usaAzucar = usaAzucar;
        this.tipoAzucar = (tipoAzucar != null) ? tipoAzucar : "Blanca";
        this.usaLeche = usaLeche;
        this.tipoLeche = (tipoLeche != null) ? tipoLeche : "Entera";
        this.retosPreferidos = (retosPreferidos != null) ? retosPreferidos : new String[0];
    }
    
}
