package qq.infrastructure.redis;

import qq.infrastructure.AppContext;

public class RedisWpkPoolContext extends RedisPoolContext {

    private final static RedisWpkPoolContext instance = new RedisWpkPoolContext();

    public static RedisWpkPoolContext getInstance(){
        return instance;
    }

    private RedisWpkPoolContext(){

    }

    public void initialize(){
        super.initialize(AppContext.getRedisConfig());
    }

}
