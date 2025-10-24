import java.io.*; //importe todas las clases y interfaces contenidas en el paquete java.io

public class PreferenciasUsuarioController {

    private final String RUTA_PREFERENCIAS = "preferencias.dat";

    public void crearPreferencias(PreferenciasUsuario preferencias) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_PREFERENCIAS))) {
            oos.writeObject(preferencias);
            System.out.println("Preferencias guardadas exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar las preferencias: " + e.getMessage());
        }
    }

    public PreferenciasUsuario obtenerPreferencias() {
        PreferenciasUsuario preferencias = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RUTA_PREFERENCIAS))) {
            preferencias = (PreferenciasUsuario) ois.readObject();
        } catch (FileNotFoundException e) {
            System.err.println("Archivo de preferencias no encontrado.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar las preferencias: " + e.getMessage());
        }
        return preferencias;
    }

    public void actualizarPreferencias(String tipoCafe, String tamanoTaza, Boolean usaAzucar, String tipoAzucar,
                                       Boolean usaLeche, String tipoLeche, String[] retosPreferidos) {
        PreferenciasUsuario actuales = obtenerPreferencias();
        if (actuales == null) {
            actuales = new PreferenciasUsuario();
        }

        if (tipoCafe != null && !tipoCafe.trim().isEmpty()) {
            actuales.setTipoCafe(tipoCafe);
        }
        if (tamanoTaza != null && !tamanoTaza.trim().isEmpty()) {
            actuales.setTamañoTaza(tamanoTaza);
        }
        if (usaAzucar != null) {
            actuales.setUsaAzucar(usaAzucar);
        }
        if (tipoAzucar != null && !tipoAzucar.trim().isEmpty()) {
            actuales.setTipoAzucar(tipoAzucar);
        }
        if (usaLeche != null) {
            actuales.setUsaLeche(usaLeche);
        }
        if (tipoLeche != null && !tipoLeche.trim().isEmpty()) {
            actuales.setTipoLeche(tipoLeche);
        }
        if (retosPreferidos != null && retosPreferidos.length > 0) {
            actuales.setRetosPreferidos(retosPreferidos);
        }

        crearPreferencias(actuales);
    }
}

