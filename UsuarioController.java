import java.util.ArrayList;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;
    private ConsumoDAO consumoDAO;
    private MetaDAO metaDAO;
    private PreferenciasDAO preferenciasDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
        this.consumoDAO = new ConsumoDAO();
        this.metaDAO = new MetaDAO();
        this.preferenciasDAO = new PreferenciasDAO();
    }

    public boolean esPrimerLogin(Usuario usuario) {
        return usuario.isPrimerLogin();
    }

    public Usuario registrarUsuario(String id, String nombre, String correo, String contrasenaHash) throws Exception {
        Usuario existente = usuarioDAO.buscarUsuarioPorCorreo(correo);
        if (existente != null) {
            throw new Exception("El correo ya está registrado.");
        }

        Usuario nuevoUsuario = new Usuario(id, nombre, correo, contrasenaHash);
        usuarioDAO.insertarUsuario(nuevoUsuario);
        return nuevoUsuario;
    }

    public Usuario login(String correo, String contrasenaHash) throws Exception {
        Usuario usuario = usuarioDAO.buscarUsuarioPorCorreo(correo);
        
        if (usuario == null || !usuario.getContrasenaHash().equals(contrasenaHash)) {
            throw new Exception("Credenciales inválidas.");
        }

        // Cargar datos relacionados del usuario
        cargarDatosUsuario(usuario);
        
        return usuario;
    }

    private void cargarDatosUsuario(Usuario usuario) {
        // Cargar consumos
        ArrayList<Consumo> consumos = consumoDAO.obtenerConsumos(usuario.getId());
        for (Consumo c : consumos) {
            usuario.agregarConsumo(c);
        }

        // Cargar metas
        ArrayList<Meta> metas = metaDAO.obtenerMetas(usuario.getId());
        for (Meta m : metas) {
            usuario.agregarMeta(m);
        }

        // Cargar preferencias
        PreferenciasUsuario preferencias = preferenciasDAO.obtenerPreferencias(usuario.getId());
        if (preferencias != null) {
            usuario.setPreferencias(preferencias);
        }
    }

    public Usuario buscarUsuarioPorId(String id) {
        Usuario usuario = usuarioDAO.buscarUsuarioPorId(id);
        if (usuario != null) {
            cargarDatosUsuario(usuario);
        }
        return usuario;
    }

    public String obtenerPerfil(Usuario usuario) {
        return usuario.toString();
    }

    public void sumarPuntos(Usuario usuario, int puntos) {
        if (puntos > 0) {
            int nuevosPuntos = usuario.getPuntos() + puntos;
            usuario.setPuntos(nuevosPuntos);
            usuarioDAO.actualizarPuntos(usuario.getId(), nuevosPuntos);
        }
    }

    public void sumarPuntosConHistorial(Usuario usuario, int puntos) {
        if (puntos > 0) {
            int nuevosPuntos = usuario.getPuntos() + puntos;
            usuario.setPuntos(nuevosPuntos);
            
            // Actualizar puntos y historial en la base de datos
            usuarioDAO.actualizarPuntosYHistorial(
                usuario.getId(), 
                nuevosPuntos, 
                usuario.getPuntosTotalesGanados(), 
                usuario.getHistorialPuntos()
            );
        }
    }

    public void restarPuntos(Usuario usuario, int puntos) {
        if (puntos > 0 && usuario.getPuntos() >= puntos) {
            int nuevosPuntos = usuario.getPuntos() - puntos;
            usuario.setPuntos(nuevosPuntos);
            usuarioDAO.actualizarPuntos(usuario.getId(), nuevosPuntos);
        }
    }

    public void actualizarUsuario(Usuario usuarioActual, String nuevoNombre, String nuevoCorreo, String nuevaContrasenaHash) {
        if (usuarioActual == null) {
            throw new RuntimeException("Usuario no proporcionado.");
        }

        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            usuarioActual.setNombre(nuevoNombre);
        }

        if (nuevoCorreo != null && !nuevoCorreo.trim().isEmpty() && !nuevoCorreo.equals(usuarioActual.getCorreo())) {
            Usuario existente = usuarioDAO.buscarUsuarioPorCorreo(nuevoCorreo);
            if (existente != null && !existente.getId().equals(usuarioActual.getId())) {
                throw new RuntimeException("El correo ya está registrado por otro usuario.");
            }
            usuarioActual.setCorreo(nuevoCorreo);
        }

        if (nuevaContrasenaHash != null && !nuevaContrasenaHash.trim().isEmpty()) {
            usuarioActual.setContrasenaHash(nuevaContrasenaHash);
        }

        usuarioDAO.actualizarUsuario(usuarioActual);
    }

    public void actualizarPrimerLogin(Usuario usuario) {
        usuario.setPrimerLogin(false);
        usuarioDAO.actualizarPrimerLogin(usuario.getId(), false);
    }
}