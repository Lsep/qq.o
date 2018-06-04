package qq.web.controller.wechat;

import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import qq.infrastructure.logging.LogHelper;
import qq.infrastructure.wechat.messaging.core.MessageRequest;
import qq.infrastructure.wechat.messaging.core.MessageResult;
import qq.service.flow.HandleWeixinMessageFlow;
import qq.service.wechat.WpkWeixinApi;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;

@Controller("app-weixinmessage-controller")
@RequestMapping("/wechat-message")
public class WechatMessageController {


    @RequestMapping()
    @ResponseBody
    public String index(MessageRequest messageRequest, HttpServletRequest request) {
        try {
            if (messageRequest.isEcho() || !messageRequest.isValid(WpkWeixinApi.getInstance().getConfig())) {
                return messageRequest.getEchostr();
            }
            String xml = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
            MessageResult result = MessageResult.fromXml(xml);
            HandleWeixinMessageFlow.run(result);
        } catch (Exception ex) {
            LogHelper.log("weixin-message", ex);
            return "error";
        }
        return "";
    }

}
