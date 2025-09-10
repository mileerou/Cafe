public class UsuarioController {
    private ArrayList<Usuario> usuarios;

    //Controlador
    public UsuarioController() {
        usuarios = new ArrayList<>();
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

    public Usuario login (String correo, String contrasenaHash) trows Exception {
        retunr usuarios.stream()
            .filter(u -> u.getCorreo().equals(correo) && u.getContrasenaHash().equals(contrasenaHash))
            .findFirst()
            .orElseThrow(() -> new Exception("Credenciales inválidas."));
    }
}