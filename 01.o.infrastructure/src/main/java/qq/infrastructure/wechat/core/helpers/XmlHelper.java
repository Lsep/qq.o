package qq.infrastructure.wechat.core.helpers;

import java.io.Serializable;

public class XmlHelper {

    public static <T extends Serializable> T fromWeixinXml(Class<T> clazz, String xmlRaw) throws Exception {
        String name = clazz.getSimpleName();
        String place1 = "<" + name + ">";
        String place2 = "</" + name + ">";

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + xmlRaw;
        xml = xml.replace("<xml>", place1).replace("</xml>", place2);
        return (T) qq.util.helper.XmlHelper.deserialize(clazz, xml);
    }
}
