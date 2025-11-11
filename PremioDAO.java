import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.ArrayList;

public class PremioDAO {
    private final MongoCollection<Document> collection;

    public PremioDAO() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        collection = db.getCollection("premios");
    }

    public void insertarPremio(Premio premio) {
        try {
            if (buscarPremioPorId(premio.getId()) != null) return;
            
            Document doc = new Document("id", premio.getId())
                    .append("nombre", premio.getNombre())
                    .append("descripcion", premio.getDescripcion())
                    .append("puntosRequeridos", premio.getPuntosRequeridos())
                    .append("stock", premio.getStock());
            collection.insertOne(doc);
        } catch (Exception e) {
            throw new RuntimeException("Error al insertar premio: " + e.getMessage());
        }
    }

    public Premio buscarPremioPorId(String id) {
        try {
            Document doc = collection.find(Filters.eq("id", id)).first();
            if (doc == null) return null;
            
            return new Premio(
                doc.getString("id"),
                doc.getString("nombre"),
                doc.getString("descripcion"),
                doc.getInteger("puntosRequeridos", 0),
                doc.getInteger("stock", 0)
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar premio: " + e.getMessage());
        }
    }

    public ArrayList<Premio> listarPremios() {
        ArrayList<Premio> premios = new ArrayList<>();
        try {
            for (Document doc : collection.find()) {
                Premio p = new Premio(
                    doc.getString("id"),
                    doc.getString("nombre"),
                    doc.getString("descripcion"),
                    doc.getInteger("puntosRequeridos", 0),
                    doc.getInteger("stock", 0)
                );
                premios.add(p);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al listar premios: " + e.getMessage());
        }
        return premios;
    }

    public void actualizarStock(String id, int nuevoStock) {
        try {
            collection.updateOne(Filters.eq("id", id), Updates.set("stock", nuevoStock));
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar stock: " + e.getMessage());
        }
    }

    public void inicializarPremios(ArrayList<Premio> premios) {
        try {
            if (collection.countDocuments() == 0) {
                for (Premio premio : premios) {
                    insertarPremio(premio);
                }
                System.out.println("✅ Premios iniciales cargados en la base de datos.");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar premios: " + e.getMessage());
        }
    }
}
