package qq.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import qq.helper.StringHelper;

import java.util.ArrayList;
import java.util.List;

public class MongoDatabaseFactory {
    private MongoClient client;

    public void initialize(MongodbConfig config) {
        if (client != null) {
            return;
        }
        ServerAddress address = new ServerAddress(config.getAddress(), config.getPort());
        if (!StringHelper.isNullOrWhitespace(config.getUsername())) {
            List<MongoCredential> credentials = new ArrayList<>();
            credentials.add(MongoCredential.createCredential(config.getUsername(), config.getDatabase(), config.getPassword().toCharArray()));
            client = new MongoClient(address, credentials);
        } else {
            client = new MongoClient(address);
        }
        System.out.println("\nmongodb initialized\n");
    }

    public void close() {
        this.client.close();
    }

    public MongoDatabase getDatabase(String database) {
        return this.client.getDatabase(database);
    }


}
