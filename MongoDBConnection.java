import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;

public class MongoDBConnection {
    // User: JjCc268930_22
    // Password: Cafe42
    // Cluster: Cluster-c
    // REEMPLAZA ESTO CON TU CONNECTION STRING REAL DE MONGODB ATLAS
    private static final String CONNECTION_STRING =
    "mongodb+srv://jceferino2006_db_user:Cafe42@cluster-c.xgmmd6x.mongodb.net/?appName=Cluster-c";
    private static final String DATABASE_NAME = "movaccinoDB";
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    public static MongoDatabase getDatabase() {
        if (database == null) {
            try {
                ConnectionString connString = new ConnectionString(CONNECTION_STRING);
                MongoClientSettings settings = MongoClientSettings.builder()
                        .applyConnectionString(connString)
                        .build();
                
                mongoClient = MongoClients.create(settings);
                database = mongoClient.getDatabase(DATABASE_NAME);
                
                // Test connection
                database.listCollectionNames().first();
                System.out.println("✅ Conectado exitosamente a MongoDB Atlas!");
            } catch (Exception e) {
                System.err.println("❌ Error al conectar con MongoDB Atlas: " + e.getMessage());
                throw new RuntimeException("No se pudo conectar a la base de datos", e);
            }
        }
        return database;
    }

    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexión a MongoDB cerrada.");
        }
    }
}