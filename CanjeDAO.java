import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;

public class CanjeDAO {
    private final MongoCollection<Document> collection;

    public CanjeDAO() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        collection = db.getCollection("canjes");
    }

    public void insertarCanje(Canje canje) {
        try {
            Document doc = new Document("id", canje.getId())
                    .append("usuarioId", canje.getUsuarioId())
                    .append("premioNombre", canje.getPremioNombre())
                    .append("fechaCanje", canje.getFechaCanje())
                    .append("puntosUsados", canje.getPuntosUsados());
            collection.insertOne(doc);
        } catch (Exception e) {
            throw new RuntimeException("Error al insertar canje: " + e.getMessage());
        }
    }

    public ArrayList<Canje> obtenerCanjes(String usuarioId) {
        ArrayList<Canje> canjes = new ArrayList<>();
        try {
            for (Document doc : collection.find(Filters.eq("usuarioId", usuarioId))) {
                Canje c = new Canje(
                    doc.getString("id"),
                    doc.getString("usuarioId"),
                    doc.getString("premioNombre"),
                    doc.getDate("fechaCanje"),
                    doc.getInteger("puntosUsados", 0)
                );
                canjes.add(c);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener canjes: " + e.getMessage());
        }
        return canjes;
    }
}