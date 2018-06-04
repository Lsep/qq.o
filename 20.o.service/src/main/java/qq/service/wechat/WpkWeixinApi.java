package qq.service.wechat;

import qq.infrastructure.AppContext;
import qq.infrastructure.wechat.WeixinApi;

public class WpkWeixinApi extends WeixinApi {

    private static final WpkWeixinApi instance = new WpkWeixinApi();
    public static WpkWeixinApi getInstance(){
        return instance;
    }

    private WpkWeixinApi() {
        super(AppContext.getWeixinWpkConfig());
    }


}
