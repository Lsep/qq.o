package qq.infrastructure.mongodb;


import qq.infrastructure.AppContext;

public class MongoDatabaseWpkFactory extends MongoDatabaseFactory {

    private final static MongoDatabaseWpkFactory instance = new MongoDatabaseWpkFactory();

    public static MongoDatabaseWpkFactory getInstance(){
        return instance;
    }

    public void initialize(){
        super.initialize(AppContext.getMongodbConfig());
    }

    public MongoDbContext createLogsBackupDb() {
        return new MongoDbContext(this, AppContext.getAppConfig().getLogsDbName() + "_backup");
    }

    public MongoDbContext createLogsDb(){
        return new MongoDbContext(this, AppContext.getAppConfig().getLogsDbName());
    }

}
