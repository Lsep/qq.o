package qq.infrastructure.mongodb;

public interface MongodbConfig {
    String getAddress();

    int getPort();

    String getUsername();

    String getPassword();

    String getDatabase();
}
