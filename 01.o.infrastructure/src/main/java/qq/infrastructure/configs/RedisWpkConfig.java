package qq.infrastructure.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import qq.infrastructure.redis.RedisConfig;

@Component
@PropertySource("/WEB-INF/app.properties")
public class RedisWpkConfig implements RedisConfig {

    @Value("${redis.address}")
    private String address;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.timeoutSeconds}")
    private int timeoutSeconds;

    @Value("${redis.password}")
    private String password;

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public int getTimeoutSeconds() {
        return this.timeoutSeconds;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
