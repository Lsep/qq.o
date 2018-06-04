package qq.infrastructure.wechat.messaging.core;

import qq.infrastructure.security.Crypto;
import qq.infrastructure.wechat.core.AppletConfig;
import qq.infrastructure.wechat.core.WeixinConfig;
import qq.util.extensions.JList;
import qq.util.helper.StringHelper;

import java.util.Objects;

public class MessageRequest {
    private String signature;
    private String timestamp;
    private String nonce;
    private String echostr;

    public boolean isValid(WeixinConfig config) {
        String[] paras = new String[]{this.timestamp, this.nonce, config.getMessageToken()};
        String joined = JList.from(paras).orderByAsc(x -> x).toList().joinString("", x -> x);
        return Objects.equals(Crypto.sha(joined), this.signature);
    }

    public boolean isValid(AppletConfig config) {
        String[] paras = new String[]{this.timestamp, this.nonce, config.getMessageToken()};
        String joined = JList.from(paras).orderByAsc(x -> x).toList().joinString("", x -> x);
        return Objects.equals(Crypto.sha(joined), this.signature);
    }

    public boolean isEcho() {
        return !StringHelper.isNullOrEmpty(this.echostr);
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getEchostr() {
        return echostr;
    }

    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }
}
