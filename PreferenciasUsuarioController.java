import java.io.*;

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

    //actualizador de arcivos
    public void actualizarPreferencias(PreferenciasUsuario preferencias) {
        crearPreferencias(preferencias); // Simplemente sobrescribe el archivo existente
    }
}
