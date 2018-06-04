package qq.infrastructure.wechat.messaging;

import qq.infrastructure.wechat.messaging.core.MessageBase;
import qq.util.helper.JsonHelper;

public class MpNewsMessage extends MessageBase {

    private String mediaId ;

    public MpNewsMessage(String openId, String mediaId)
    {
        super(openId);
        this.mediaId = mediaId;
    }

    @Override
    public String toJson() {
        JsonDto dto = new JsonDto();
        dto.touser = this.getToUserOpenId();
        dto.mpnews.media_id = this.mediaId;
        return JsonHelper.serialize(dto);
    }

    private class JsonDto{
        private String touser;
        private String msgtype = "mpnews";
        private JsonDtoNews mpnews = new JsonDtoNews();

        public String getTouser() {
            return touser;
        }

        public String getMsgtype() {
            return msgtype;
        }

        public JsonDtoNews getMpnews() {
            return mpnews;
        }
    }

    private class JsonDtoNews{
        private String media_id;

        public String getMedia_id() {
            return media_id;
        }
    }

}
