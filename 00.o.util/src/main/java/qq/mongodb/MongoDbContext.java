package qq.mongodb;

import com.mongodb.client.MongoDatabase;

public class MongoDbContext {

    private final MongoDatabase database;
    private final MongoDatabaseFactory factory;

    public MongoDbContext(MongoDatabaseFactory factory, String databaseName) {
        this.factory = factory;
        this.database = factory.getDatabase(databaseName);
    }

    public MongoDatabase getDatabase() {
        return this.database;
    }

}
