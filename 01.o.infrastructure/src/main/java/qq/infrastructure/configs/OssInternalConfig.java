package qq.infrastructure.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import qq.infrastructure.aliyun.oss.OssConfig;

@Component
@PropertySource("/WEB-INF/app.properties")
public class OssInternalConfig implements OssConfig {

    @Value("${aliyun.oss.internal.key}")
    private String key;

    @Value("${aliyun.oss.internal.secret}")
    private String secret;

    @Value("${aliyun.oss.internal.bucketName}")
    private String bucketName;

    @Value("${aliyun.oss.internal.endPoint}")
    private String endPoint;

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getSecret() {
        return this.secret;
    }

    @Override
    public String getBucketName() {
        return this.bucketName;
    }

    @Override
    public String getEndPoint() {
        return this.endPoint;
    }
}
