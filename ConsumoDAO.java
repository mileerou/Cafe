import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.model.Filters;
import java.util.ArrayList;

public class ConsumoDAO {
    private final MongoCollection<Document> collection;

    public ConsumoDAO() {
        MongoDatabase db = MongoDBConnection.getDatabase();
        collection = db.getCollection("consumos");
    }

    public void insertarConsumo(String usuarioId, Consumo consumo) {
        try {
            Document doc = new Document("usuarioId", usuarioId)
                    .append("fecha", consumo.getFecha())
                    .append("tamanoTaza", consumo.getTamañoTaza())
                    .append("tipoAzucar", consumo.getTipoAzucar())
                    .append("tipoLeche", consumo.getTipoLeche())
                    .append("tipoCafe", consumo.getTipoCafe())
                    .append("respuestasExtras", consumo.getRespuestasExtras());
            collection.insertOne(doc);
        } catch (Exception e) {
            throw new RuntimeException("Error al insertar consumo: " + e.getMessage());
        }
    }

    public ArrayList<Consumo> obtenerConsumos(String usuarioId) {
        ArrayList<Consumo> consumos = new ArrayList<>();
        try {
            for (Document doc : collection.find(Filters.eq("usuarioId", usuarioId))) {
                Consumo c = new Consumo(
                    doc.getDate("fecha"),
                    doc.getString("tamanoTaza"),
                    doc.getString("tipoAzucar"),
                    doc.getString("tipoLeche"),
                    doc.getString("tipoCafe"),
                    doc.getString("respuestasExtras")
                );
                consumos.add(c);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener consumos: " + e.getMessage());
        }
        return consumos;
    }
}