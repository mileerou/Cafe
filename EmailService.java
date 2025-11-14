import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.util.UUID;

public class EmailService {
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_FROM = "jceferino2006@gmail.com";
    private static final String EMAIL_PASSWORD = "sqkb ixsa wfxl zooh";

    /**
     * Envía email según el tipo de premio
     * Si contiene "Cupón" → envía código
     * Si no → solo confirmación
     */
    public boolean enviarNotificacionPremio(String destinatario, String nombreUsuario, 
                                             String nombrePremio, String descripcionPremio) {
        try {
            // Configurar propiedades SMTP
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", SMTP_HOST);
            props.setProperty("mail.smtp.port", SMTP_PORT);
            props.setProperty("mail.smtp.auth", "true");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.starttls.required", "true");
            props.setProperty("mail.smtp.connectiontimeout", "5000");
            props.setProperty("mail.smtp.timeout", "5000");

            // Crear sesión
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(EMAIL_FROM, EMAIL_PASSWORD);
                }
            });

            // Crear mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_FROM, "Movaccino"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            
            String codigoCupon = null;
            
            // Detectar si es un cupón
            if (nombrePremio.toLowerCase().contains("cupón") || 
                nombrePremio.toLowerCase().contains("cupon")) {
                codigoCupon = generarCodigoCupon();
                message.setSubject("Tu cupon de Movaccino esta listo!");
            } else {
                message.setSubject("Premio canjeado en Movaccino!");
            }

            String htmlContent = construirEmailHTML(nombreUsuario, nombrePremio, 
                                                     descripcionPremio, codigoCupon);
            message.setContent(htmlContent, "text/html; charset=utf-8");

            // Intentar enviar
            Transport.send(message);
            
            System.out.println("Email enviado exitosamente a: " + destinatario);
            if (codigoCupon != null) {
                System.out.println("Codigo de cupon generado: " + codigoCupon);
            }
            return true;

        } catch (AuthenticationFailedException e) {
            System.err.println("Error de autenticacion Gmail (verifica credentials)");
            System.err.println("El premio ha sido canjeado, pero el email no se pudo enviar.");
            System.err.println("Detalles: " + e.getMessage());
            return false;
        } catch (MessagingException e) {
            System.err.println("Error SMTP al enviar email");
            System.err.println("El premio ha sido canjeado, pero el email no se pudo enviar.");
            System.err.println("Detalles: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error inesperado al enviar email: " + e.getClass().getName());
            System.err.println("El premio ha sido canjeado, pero el email no se pudo enviar.");
            System.err.println("Detalles: " + e.getMessage());
            return false;
        }
    }

    /**
     * Construye el HTML del email (con o sin cupón)
     */
    private String construirEmailHTML(String nombreUsuario, String nombrePremio, 
                                       String descripcion, String codigoCupon) {
        StringBuilder html = new StringBuilder();
        
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("<meta charset=\"UTF-8\">\n");
        html.append("<style>\n");
        html.append("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }\n");
        html.append(".container { max-width: 600px; margin: 0 auto; background-color: white; border-radius: 10px; padding: 30px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }\n");
        html.append(".header { text-align: center; color: #6F4E37; margin-bottom: 20px; }\n");
        html.append(".coffee-icon { font-size: 50px; }\n");
        html.append(".prize-box { background: linear-gradient(135deg, #6F4E37 0%, #8B6F47 100%); color: white; padding: 25px; border-radius: 8px; text-align: center; margin: 20px 0; }\n");
        html.append(".prize-name { font-size: 24px; font-weight: bold; margin: 10px 0; }\n");
        html.append(".prize-desc { font-size: 14px; margin: 10px 0; opacity: 0.9; }\n");
        html.append(".coupon-code { font-size: 32px; font-weight: bold; letter-spacing: 3px; background-color: rgba(255,255,255,0.2); padding: 15px; border-radius: 5px; margin: 15px 0; border: 2px dashed white; }\n");
        html.append(".instructions { background-color: #f9f9f9; padding: 15px; border-radius: 5px; margin-top: 20px; border-left: 4px solid #6F4E37; }\n");
        html.append(".footer { text-align: center; color: #666; font-size: 12px; margin-top: 30px; padding-top: 20px; border-top: 1px solid #eee; }\n");
        html.append(".highlight { background-color: #fff3cd; padding: 10px; border-radius: 5px; margin: 15px 0; border-left: 4px solid #ffc107; }\n");
        html.append("</style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("<div class=\"container\">\n");
        html.append("<div class=\"header\">\n");
        html.append("<div class=\"coffee-icon\">CAFE</div>\n");
        html.append("<h1>Felicidades ").append(nombreUsuario).append("!</h1>\n");
        html.append("</div>\n");
        html.append("<p>Has canjeado exitosamente tu premio en <strong>Movaccino</strong>.</p>\n");
        html.append("<div class=\"prize-box\">\n");
        html.append("<div class=\"prize-name\">").append(nombrePremio).append("</div>\n");
        html.append("<div class=\"prize-desc\">").append(descripcion).append("</div>\n");
        
        if (codigoCupon != null) {
            html.append("<p style=\"margin: 15px 0 5px 0; font-size: 14px;\">Tu codigo de cupon es:</p>\n");
            html.append("<div class=\"coupon-code\">").append(codigoCupon).append("</div>\n");
            html.append("<p style=\"margin-top: 10px; font-size: 12px; opacity: 0.9;\">Este codigo es unico y personal</p>\n");
        } else {
            html.append("<p style=\"margin-top: 15px; font-size: 14px;\">Disfruta tu premio!</p>\n");
        }
        
        html.append("</div>\n");
        
        if (codigoCupon != null) {
            html.append("<div class=\"instructions\">\n");
            html.append("<h3>Como usar tu cupon:</h3>\n");
            html.append("<ul style=\"text-align: left;\">\n");
            html.append("<li>Presenta este codigo en la cafeteria participante</li>\n");
            html.append("<li>El codigo es valido por 30 dias desde su emision</li>\n");
            html.append("<li>No es transferible ni canjeable por dinero</li>\n");
            html.append("<li>Un solo uso por cupon</li>\n");
            html.append("</ul>\n");
            html.append("</div>\n");
            html.append("<div class=\"highlight\">\n");
            html.append("<strong>Consejo:</strong> Guarda este email o toma una captura de pantalla del codigo para mostrarlo facilmente.\n");
            html.append("</div>\n");
        } else {
            html.append("<div class=\"instructions\">\n");
            html.append("<h3>Instrucciones:</h3>\n");
            html.append("<ul style=\"text-align: left;\">\n");
            html.append("<li>Tu premio estara disponible en los proximos dias</li>\n");
            html.append("<li>Recibiras una notificacion cuando este listo para recoger</li>\n");
            html.append("<li>Presenta este email como comprobante</li>\n");
            html.append("</ul>\n");
            html.append("</div>\n");
        }
        
        html.append("<p style=\"margin-top: 20px;\">Gracias por ser parte de Movaccino! Cada paso que das hacia reducir tu consumo de cafe es un logro importante.</p>\n");
        html.append("<div class=\"footer\">\n");
        html.append("<p>Este es un mensaje automatico de Movaccino</p>\n");
        html.append("<p>Si no solicitaste este premio, por favor ignora este mensaje</p>\n");
        html.append("</div>\n");
        html.append("</div>\n");
        html.append("</body>\n");
        html.append("</html>\n");
        
        return html.toString();
    }

    /**
     * Genera código único para cupones
     */
    public static String generarCodigoCupon() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        return "MOVAC-" + uuid.substring(0, 4) + "-" + uuid.substring(4, 8);
    }
}