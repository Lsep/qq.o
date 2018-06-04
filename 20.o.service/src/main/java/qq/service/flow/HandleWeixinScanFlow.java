package qq.service.flow;

import qq.data.WpkDbContext;
import qq.infrastructure.wechat.messaging.core.MessageResult;

public class HandleWeixinScanFlow extends HandleWeixinMessageFlow {

    private WpkDbContext db;

    public void onScan(MessageResult result) {
        System.out.println(result);
    }

}
