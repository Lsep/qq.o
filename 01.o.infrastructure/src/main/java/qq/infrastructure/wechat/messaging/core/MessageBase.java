package qq.infrastructure.wechat.messaging.core;

public abstract class MessageBase {

    private String toUserOpenId;

    public abstract String toJson();

    protected MessageBase() {

    }

    protected MessageBase(String openId) {
        this.toUserOpenId = openId;
    }

    public String getToUserOpenId() {
        return this.toUserOpenId;
    }

    public void setToUserOpenId(String toUserOpenId) {
        this.toUserOpenId = toUserOpenId;
    }
}
