package qq.infrastructure.wechat.core;

public interface WeixinConfig {

    String getAppId();

    String getAppSecret();

    String getMessageToken();

    String getMessageAesKey();
}
