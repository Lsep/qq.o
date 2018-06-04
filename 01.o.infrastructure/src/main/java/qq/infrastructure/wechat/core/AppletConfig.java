package qq.infrastructure.wechat.core;

public interface AppletConfig {

    String getAppId();

    String getAppSecret();

    String getMessageToken();

    String getMessageAesKey();
}
