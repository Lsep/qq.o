package qq.infrastructure.aliyun.oss;

import qq.infrastructure.aliyun.AliyunAccountConfig;

public interface OssConfig extends AliyunAccountConfig {
    String getBucketName();
    String getEndPoint();
}
