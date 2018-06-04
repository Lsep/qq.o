package qq.service.flow;

import qq.infrastructure.wechat.messaging.core.MessageResult;

public class HandleWeixinUnsubscribeFlow {
    public void run(MessageResult result) {
        System.out.println(result);
    }
}
