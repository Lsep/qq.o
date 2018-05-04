package qq.infrastructure.redis;

import qq.infrastructure.AppContext;

public class WpkRedisSession extends RedisSession {

    public WpkRedisSession() {
        super(RedisWpkPoolContext.getInstance());
    }

    @Override
    protected String getActualKey(String key) {
        return AppContext.getAppConfig().getRedisKeyPrefix() + key;
    }


}
