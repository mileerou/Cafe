import java.util.ArrayList;
import java.util.Random;

public class MensajeController {
    private ArrayList<MensajeMotivacional> mensajesPredefinidos;
    private Random random;

    public MensajeController() {
        this.mensajesPredefinidos = new ArrayList<>();
        this.random = new Random();
        inicializarMensajes();
    }

   
    private void inicializarMensajes() {
        // Mensajes de bienvenida
        mensajesPredefinidos.add(new MensajeMotivacional("msg_001", "¡Bienvenido de nuevo! Hoy es un gran día para reducir tu consumo de café ☕"));
        mensajesPredefinidos.add(new MensajeMotivacional("msg_002", "¡Cada pequeño paso cuenta! Registra tu consumo diario 📝"));
        
        // Mensajes después de registrar consumo
        mensajesPredefinidos.add(new MensajeMotivacional("msg_003", "¡Excelente! Has registrado tu consumo. La consciencia es el primer paso 💪"));
        mensajesPredefinidos.add(new MensajeMotivacional("msg_004", "¡Bien hecho! Mantén el registro constante para ver tu progreso 📊"));
        
        // Mensajes de metas completadas
        mensajesPredefinidos.add(new MensajeMotivacional("msg_005", "🎉 ¡FELICIDADES! Has completado una meta. ¡Eres increíble!"));
        mensajesPredefinidos.add(new MensajeMotivacional("msg_006", "🌟 ¡Meta alcanzada! Tu dedicación está dando frutos"));
        
        // Mensajes de puntos
        mensajesPredefinidos.add(new MensajeMotivacional("msg_007", "💰 ¡Has ganado puntos! Sigue acumulando para canjear premios"));
        mensajesPredefinidos.add(new MensajeMotivacional("msg_008", "⭐ ¡Puntos sumados! Estás más cerca de tu próximo premio"));
        
        // Mensajes motivacionales generales
        mensajesPredefinidos.add(new MensajeMotivacional("msg_009", "Recuerda: el cambio es un proceso, no un evento 🌱"));
        mensajesPredefinidos.add(new MensajeMotivacional("msg_010", "Tu salud te lo agradecerá. ¡Sigue adelante! 💚"));
        
        // Mensajes de premios
        mensajesPredefinidos.add(new MensajeMotivacional("msg_011", "🎁 ¡Premio canjeado! Disfruta tu recompensa, te lo mereces"));
        mensajesPredefinidos.add(new MensajeMotivacional("msg_012", "✨ ¡Increíble! Has canjeado un premio. Sigue así"));
    }

    public MensajeMotivacional obtenerMensaje(String usuarioId, UsuarioController usuarioController) {
        
    }

    public MensajeMotivacional obtenerMensajesDelDia(String usuarioId, UsuarioController usuarioController) {
        
    }

   
    public MensajeMotivacional generarMensaje(String contenido, String tipo, String condicionAparecer) {
    }

}