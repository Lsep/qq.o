package qq.infrastructure.wechat.core;

import qq.infrastructure.security.Crypto;
import qq.infrastructure.wechat.WeixinApi;
import qq.util.extensions.JDate;
import qq.util.extensions.JList;
import qq.util.extensions.KeyValuePair;
import qq.util.helper.StringHelper;

import java.util.UUID;

public class WeixinJsConfigDto {
    private String appId;
    public String timestamp;
    public String nonceStr;
    public String signature;
    private Long userId;
    private final WeixinApi api;

    public WeixinJsConfigDto(WeixinApi api, Long userId) {
        this.api = api;
        this.appId = api.getAppId();
        this.timestamp = (JDate.now().getVersionMiliSeconds() / 1000) + "";
        this.nonceStr = UUID.randomUUID().toString().replace("-", "");
        this.userId = userId;
    }

    public WeixinJsConfigDto buildSignature(String url) {
        JList<KeyValuePair<String, String>> list = this.getKeyValues(url);
        String query = list.joinString("&", x -> x.getKey() + "=" + x.getValue());
        this.signature = Crypto.sha(query).toUpperCase();
        return this;
    }

    private JList<KeyValuePair<String, String>> getKeyValues(String url) {
        JList<KeyValuePair<String, String>> list = new JList<>();
        list.add(new KeyValuePair<>("noncestr", this.nonceStr));
        list.add(new KeyValuePair<>("jsapi_ticket", WxKeyManager.getInstance().getJsApiTicket(this.api)));
        list.add(new KeyValuePair<>("timestamp", this.timestamp));
        list.add(new KeyValuePair<>("url", url));

        return list.where(x -> !StringHelper.isNullOrWhitespace(x.getValue()))
                .orderByAsc(x -> x.getKey())
                .toList();
    }

    public String getAppId() {
        return appId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public Long getUserId() {
        return userId;
    }
}
