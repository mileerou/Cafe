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

    // metodo para actualizar un campo especifico
    // Aca esta el metodo
    /**
     * Permite actualizar un campo específico del consumo si el usuario se equivocó.
     * @param campo El nombre del campo a actualizar (por ejemplo: "tipoLeche")
     * @param nuevoValor El nuevo valor para ese campo
     * @throws IllegalArgumentException si el campo no es válido
     */
    public void actualizarCampo(String campo, String nuevoValor) {
        String campoNormalizado = campo.toLowerCase();
        if (campoNormalizado.equals("tamanotaza") || campoNormalizado.equals("tamanotaza")) {
            this.tamanoTaza = nuevoValor;
            return;
        }
        if (campoNormalizado.equals("tipoazucar")) {
            this.tipoAzucar = nuevoValor;
            return;
        }
        if (campoNormalizado.equals("tipoleche")) {
            this.tipoLeche = nuevoValor;
            return;
        }
        if (campoNormalizado.equals("tipocafe")) {
            this.tipoCafe = nuevoValor;
            return;
        }
        if (campoNormalizado.equals("respuestasextras")) {
            this.respuestasExtras = nuevoValor;
            return;
        }
        throw new IllegalArgumentException("Campo no válido: " + campo);
    }

    @Override
    public String toString() {
        // Formatear la fecha para una mejor presentación
         java.text.SimpleDateFormat formatoFecha = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm");
        return "Consumo: " +
                "\n fecha=" + formatoFecha.format(fecha) +
                "\n tamañoTaza: " + tamanoTaza + "oz" +
                "\n tipoAzucar: " + tipoAzucar +
                "\n tipoLeche: " + tipoLeche +
                "\n tipoCafe: " + tipoCafe +
                "\n Extra: '" + respuestasExtras + "\n";
    }
}
