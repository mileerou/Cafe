import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;
import java.util.Arrays;

public class PreferenciasDAO {
    private final MongoCollection<Document> collection;

    public PreferenciasDAO() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        collection = db.getCollection("preferencias");
    }

    public void guardarPreferencias(String usuarioId, PreferenciasUsuario preferencias) {
        try {
            Document doc = new Document("usuarioId", usuarioId)
                    .append("tipoCafe", preferencias.getTipoCafe())
                    .append("tamanoTaza", preferencias.getTamañoTaza())
                    .append("usaAzucar", preferencias.isUsaAzucar())
                    .append("tipoAzucar", preferencias.getTipoAzucar())
                    .append("usaLeche", preferencias.isUsaLeche())
                    .append("tipoLeche", preferencias.getTipoLeche())
                    .append("retosPreferidos", Arrays.asList(preferencias.getRetosPreferidos()));

            collection.replaceOne(
                Filters.eq("usuarioId", usuarioId),
                doc,
                new com.mongodb.client.model.ReplaceOptions().upsert(true)
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar preferencias: " + e.getMessage());
        }
    }

    public PreferenciasUsuario obtenerPreferencias(String usuarioId) {
        try {
            Document doc = collection.find(Filters.eq("usuarioId", usuarioId)).first();
            if (doc == null) return null;

            ArrayList<String> retosList = (ArrayList<String>) doc.get("retosPreferidos");
            String[] retos = retosList != null ? retosList.toArray(new String[0]) : new String[0];

            return new PreferenciasUsuario(
                doc.getString("tipoCafe"),
                doc.getString("tamanoTaza"),
                doc.getBoolean("usaAzucar", false),
                doc.getString("tipoAzucar"),
                doc.getBoolean("usaLeche", false),
                doc.getString("tipoLeche"),
                retos
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener preferencias: " + e.getMessage());
        }
    }
}