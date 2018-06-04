package qq.service.flow;

import qq.data.WpkDbContext;
import qq.infrastructure.wechat.messaging.core.MessageResult;

import java.util.Date;

public abstract class HandleWeixinMessageFlow {

    public static void run(MessageResult messageResult) {
        switch (messageResult.getMsgType()) {
            case event:
                switch (messageResult.getEvent()) {
                    case SCAN:
                    case subscribe:
                        new HandleWeixinScanFlow().onScan(messageResult);
                        break;
                    case unsubscribe:
                        new HandleWeixinUnsubscribeFlow().run(messageResult);
                        break;
                    case CLICK:
                        break;
                    default:
                        break;
                }
                break;
            case text:
                break;
            default:
                break;
        }

    }

}
