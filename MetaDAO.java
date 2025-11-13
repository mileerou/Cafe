import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;

public class MetaDAO {
    private final MongoCollection<Document> collection;

    public MetaDAO() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        collection = db.getCollection("metas");
    }

    public void insertarMeta(Meta meta, String usuarioId) {
        try {
            Document doc = new Document("id", meta.getId())
                    .append("usuarioId", usuarioId)
                    .append("descripcion", meta.getDescripcion())
                    .append("puntosObjetivo", meta.getPuntosObjetivo())
                    .append("puntosActuales", meta.getPuntosActuales())
                    .append("completada", meta.isCompletada());
            collection.insertOne(doc);
        } catch (Exception e) {
            throw new RuntimeException("Error al insertar meta: " + e.getMessage());
        }
    }

    public ArrayList<Meta> obtenerMetas(String usuarioId) {
        ArrayList<Meta> metas = new ArrayList<>();
        try {
            for (Document doc : collection.find(Filters.eq("usuarioId", usuarioId))) {
                Meta m = new Meta(
                    doc.getString("id"),
                    doc.getString("descripcion"),
                    doc.getInteger("puntosObjetivo", 0)
                );
                m.setPuntosActuales(doc.getInteger("puntosActuales", 0));
                m.setCompletada(doc.getBoolean("completada", false));
                metas.add(m);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener metas: " + e.getMessage());
        }
        return metas;
    }

    public void marcarCompletada(String usuarioId, String metaId) {
        try {
            collection.updateOne(
                Filters.and(
                    Filters.eq("usuarioId", usuarioId),
                    Filters.eq("id", metaId)
                ),
                Updates.set("completada", true)
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al marcar meta completada: " + e.getMessage());
        }
    }

    public void deleteMeta(String usuarioId, String metaId) {
        try {
            collection.deleteOne(
                Filters.and(
                    Filters.eq("usuarioId", usuarioId),
                    Filters.eq("id", metaId)
                )
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar meta: " + e.getMessage());
        }
    }

    public void actualizarPuntos(String usuarioId, String metaId, int puntosActuales, boolean completada) {
        try {
            collection.updateOne(
                Filters.and(
                    Filters.eq("usuarioId", usuarioId),
                    Filters.eq("id", metaId)
                ),
                Updates.combine(
                    Updates.set("puntosActuales", puntosActuales),
                    Updates.set("completada", completada)
                )
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar puntos de la meta: " + e.getMessage());
        }
    }
}