import java.util.ArrayList;
import java.util.Optional;

public class UsuarioController {
    private ArrayList<Usuario> usuarios;

    public UsuarioController() {
        usuarios = new ArrayList<>();
    }

    public boolean esPrimerLogin(Usuario usuario) {
        return usuario.isPrimerLogin();
    }

    public void registrarUsuario(String id, String nombre, String coreo, String contrasenaHash) throws Exception{
        Optional<Usuario> existente = usuarios.stream()
            .filter(u -> u.getCorreo().equals(coreo))
            .findFirst();
        
        if (existente.isPresent()){
            throw new Exception("El correo ya está registrado.");
        }
        usuarios.add(new Usuario(id, nombre, coreo, contrasenaHash));
    }

    public Usuario login (String correo, String contrasenaHash) throws Exception {
        return usuarios.stream()
            .filter(u -> u.getCorreo().equals(correo) && u.getContrasenaHash().equals(contrasenaHash))
            .findFirst()
            .orElseThrow(() -> new Exception("Credenciales inválidas."));
            
    }

    public Usuario buscarUsuarioPorId(String id) {
        return usuarios.stream()
            .filter(u -> u.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    public String obtenerPerfil(Usuario usuario) {
        return usuario.toString();
    }

    public void actualizarUsuario(String id, String nuevoNombre, String nuevoCorreo) throws Exception {
        Usuario usuario = buscarUsuarioPorId(id);
        if (usuario == null) {
            throw new Exception("Usuario no encontrado.");
        }
        usuario.setNombre(nuevoNombre);
        usuario.setCorreo(nuevoCorreo);
    }
}