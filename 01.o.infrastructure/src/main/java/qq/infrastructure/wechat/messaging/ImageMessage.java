package qq.infrastructure.wechat.messaging;

import qq.infrastructure.wechat.messaging.core.MessageBase;
import qq.util.helper.JsonHelper;

public class ImageMessage extends MessageBase {

    private String mediaId ;

    public ImageMessage(String openId, String mediaId)
    {
        super(openId);
        this.mediaId = mediaId;
    }

    @Override
    public String toJson()
    {
        JsonDto dto = new JsonDto();
        dto.touser = this.getToUserOpenId();
        dto.image.media_id = this.mediaId;
        return JsonHelper.serialize(dto);
    }

    private class JsonDto{
        private String touser;
        private String msgtype = "image";
        private JsonDtoImage image = new JsonDtoImage();

        public String getTouser() {
            return touser;
        }

        public String getMsgtype() {
            return msgtype;
        }

        public JsonDtoImage getImage() {
            return image;
        }
    }

    private class JsonDtoImage{
        private String media_id;

        public String getMedia_id() {
            return media_id;
        }
    }

}
