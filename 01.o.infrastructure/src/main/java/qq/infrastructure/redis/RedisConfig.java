package qq.infrastructure.redis;

public interface RedisConfig {
    String getAddress();

    int getPort();

    int getTimeoutSeconds();

    String getPassword();
}
