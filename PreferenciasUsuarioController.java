public class PreferenciasUsuarioController {
    private PreferenciasDAO preferenciasDAO;
    private String usuarioIdActual;

    public PreferenciasUsuarioController() {
        this.preferenciasDAO = new PreferenciasDAO();
    }

    public void setUsuarioActual(String usuarioId) {
        this.usuarioIdActual = usuarioId;
    }

    public void crearPreferencias(PreferenciasUsuario preferencias) {
        try {
            if (usuarioIdActual == null) {
                System.err.println("No hay usuario activo.");
                return;
            }
            preferenciasDAO.guardarPreferencias(usuarioIdActual, preferencias);
            System.out.println("Preferencias guardadas exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al guardar las preferencias: " + e.getMessage());
        }
    }

    public PreferenciasUsuario obtenerPreferencias() {
        if (usuarioIdActual == null) {
            System.err.println("No hay usuario activo.");
            return null;
        }
        
        try {
            return preferenciasDAO.obtenerPreferencias(usuarioIdActual);
        } catch (Exception e) {
            System.err.println("Error al cargar las preferencias: " + e.getMessage());
            return null;
        }
    }

    public void actualizarPreferencias(String tipoCafe, String tamanoTaza, Boolean usaAzucar, String tipoAzucar,
                                       Boolean usaLeche, String tipoLeche, String[] retosPreferidos) {
        if (usuarioIdActual == null) {
            System.err.println("No hay usuario activo.");
            return;
        }

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