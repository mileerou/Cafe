import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
//nuevas librerias
import java.io.IOException;

public class HashUtil {

        // Método para leer una contraseña mostrando * por cada carácter
    public static String leerPasswordConAsteriscos(String mensaje) {
        StringBuilder password = new StringBuilder();
        System.out.print(mensaje);

        try {
            while (true) {
                char c = (char) System.in.read();
                // Si el usuario presiona Enter (fin de contraseña)
                if (c == '\n' || c == '\r') {
                    System.out.println(); // Salto de línea
                    break;
                }
                // Si presiona Backspace, borra un *
                else if (c == '\b') {
                    if (password.length() > 0) {
                        password.deleteCharAt(password.length() - 1);
                        System.out.print("\b \b"); // borra el último asterisco en consola
                    }
                }
                // Si es un carácter válido, agrega y muestra *
                else {
                    password.append(c);
                    System.out.print("*");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer la contraseña.");
        }

        return password.toString();
    }

    //ya existente
    public static String hashPassword(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }catch(NoSuchAlgorithmException e){
            throw new RuntimeException("Error al hashear la contraseña", e);
        }
        
    }
}