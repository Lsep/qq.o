package qq.infrastructure.wechat.core;

import com.github.wxpay.sdk.WXPayConfig;

public interface WeixinPayConfig extends WXPayConfig {

    String getPayNotifyUrl();

    String getClientIp();
}
