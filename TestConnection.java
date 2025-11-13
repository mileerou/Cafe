public class TestConnection {
    public static void main(String[] args) {
        MongoDBConnection.getDatabase();
        MongoDBConnection.closeConnection();
    }
}