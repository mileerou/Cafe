import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.util.List;

public class UsuarioDAO {
    private final MongoCollection<Document> collection;

    public UsuarioDAO() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        collection = db.getCollection("usuarios");
    }

    public void insertarUsuario(Usuario usuario) {
        try {
            Document doc = new Document("id", usuario.getId())
                    .append("nombre", usuario.getNombre())
                    .append("correo", usuario.getCorreo())
                    .append("contrasenaHash", usuario.getContrasenaHash())
                    .append("fechaRegistro", usuario.getFechaRegistro())
                    .append("puntos", usuario.getPuntos())
                    .append("primerLogin", usuario.isPrimerLogin())
                    .append("puntosTotalesGanados", usuario.getPuntosTotalesGanados())
                    .append("historialPuntos", convertirHistorialPuntosADocumentos(usuario.getHistorialPuntos()));
            collection.insertOne(doc);
        } catch (Exception e) {
            throw new RuntimeException("Error al insertar usuario: " + e.getMessage());
        }
    }

    public Usuario buscarUsuarioPorId(String id) {
        try {
            Document doc = collection.find(Filters.eq("id", id)).first();
            if (doc == null) return null;
            return documentoAUsuario(doc);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar usuario: " + e.getMessage());
        }
    }

    public Usuario topMovacciners() {
        try {
            Document doc = collection.find().sort(new Document("puntosTotalesGanados", -1)).first();
            if (doc == null) return null;
            return documentoAUsuario(doc);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar top Movacciners: " + e.getMessage());
        }
    }

    // New: retorna los top N usuarios ordenados por puntos totales ganados (desc)
    public ArrayList<Usuario> topMovacciners(int limit) {
        ArrayList<Usuario> top = new ArrayList<>();
        try {
            for (Document doc : collection.find().sort(new Document("puntosTotalesGanados", -1)).limit(limit)) {
                Usuario u = documentoAUsuario(doc);
                top.add(u);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener top Movacciners: " + e.getMessage());
        }
        return top;
    }

    public Usuario buscarUsuarioPorCorreo(String correo) {
        try {
            Document doc = collection.find(Filters.eq("correo", correo)).first();
            if (doc == null) return null;
            return documentoAUsuario(doc);
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar usuario por correo: " + e.getMessage());
        }
    }

    public void actualizarUsuario(Usuario usuario) {
        try {
            collection.updateOne(
                Filters.eq("id", usuario.getId()),
                Updates.combine(
                    Updates.set("nombre", usuario.getNombre()),
                    Updates.set("correo", usuario.getCorreo()),
                    Updates.set("contrasenaHash", usuario.getContrasenaHash()),
                    Updates.set("puntos", usuario.getPuntos()),
                    Updates.set("primerLogin", usuario.isPrimerLogin()),
                    Updates.set("puntosTotalesGanados", usuario.getPuntosTotalesGanados()),
                    Updates.set("historialPuntos", convertirHistorialPuntosADocumentos(usuario.getHistorialPuntos()))
                )
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar usuario: " + e.getMessage());
        }
    }

    public void actualizarPuntos(String id, int nuevosPuntos) {
        try {
            collection.updateOne(
                Filters.eq("id", id),
                Updates.set("puntos", nuevosPuntos)
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar puntos: " + e.getMessage());
        }
    }

    public void actualizarPuntosYHistorial(String id, int nuevosPuntos, int puntosTotalesGanados, ArrayList<HashMap<String, Object>> historialPuntos) {
        try {
            collection.updateOne(
                Filters.eq("id", id),
                Updates.combine(
                    Updates.set("puntos", nuevosPuntos),
                    Updates.set("puntosTotalesGanados", puntosTotalesGanados),
                    Updates.set("historialPuntos", convertirHistorialPuntosADocumentos(historialPuntos))
                )
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar puntos y historial: " + e.getMessage());
        }
    }

    public void actualizarPrimerLogin(String id, boolean valor) {
        try {
            collection.updateOne(
                Filters.eq("id", id),
                Updates.set("primerLogin", valor)
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar primer login: " + e.getMessage());
        }
    }

    private Usuario documentoAUsuario(Document doc) {
        Usuario u = new Usuario(
            doc.getString("id"),
            doc.getString("nombre"),
            doc.getString("correo"),
            doc.getString("contrasenaHash")
        );
        u.setPuntos(doc.getInteger("puntos", 0));
        u.setPrimerLogin(doc.getBoolean("primerLogin", true));
        u.setPuntosTotalesGanados(doc.getInteger("puntosTotalesGanados", 0));
        
        // Cargar historial de puntos
        List<Document> historialDocs = (List<Document>) doc.get("historialPuntos");
        if (historialDocs != null) {
            ArrayList<HashMap<String, Object>> historial = new ArrayList<>();
            for (Document histDoc : historialDocs) {
                HashMap<String, Object> registro = new HashMap<>();
                registro.put("fecha", histDoc.getDate("fecha"));
                registro.put("puntos", histDoc.getInteger("puntos"));
                registro.put("fuente", histDoc.getString("fuente"));
                registro.put("descripcion", histDoc.getString("descripcion"));
                registro.put("puntosTotales", histDoc.getInteger("puntosTotales"));
                historial.add(registro);
            }
            u.setHistorialPuntos(historial);
        }
        
        return u;
    }

    private List<Document> convertirHistorialPuntosADocumentos(ArrayList<HashMap<String, Object>> historial) {
        List<Document> docs = new ArrayList<>();
        for (HashMap<String, Object> registro : historial) {
            Document doc = new Document()
                .append("fecha", registro.get("fecha"))
                .append("puntos", registro.get("puntos"))
                .append("fuente", registro.get("fuente"))
                .append("descripcion", registro.get("descripcion"))
                .append("puntosTotales", registro.get("puntosTotales"));
            docs.add(doc);
        }
        return docs;
    }
}