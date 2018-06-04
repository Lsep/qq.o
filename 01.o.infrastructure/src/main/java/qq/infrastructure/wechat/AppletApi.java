package qq.infrastructure.wechat;


import qq.infrastructure.http.WebClient;
import qq.infrastructure.wechat.core.AppletConfig;
import qq.infrastructure.wechat.core.WxKeyManager;
import qq.infrastructure.wechat.core.WxUrls;
import qq.infrastructure.wechat.core.dtos.AccessTokenDto;
import qq.infrastructure.wechat.core.dtos.AppletOpenIdDto;
import qq.infrastructure.wechat.core.helpers.WxHttpHelper;
import qq.infrastructure.wechat.core.parameters.AppletQrCodeParameter;
import qq.util.helper.JsonHelper;

import java.io.InputStream;

/**
 * @desc 微信小程序通用接口
 * @date 2018-04-20 11:01
 */
public class AppletApi {

    private AppletConfig config;

    public AppletApi() {
    }

    public AppletApi(AppletConfig config) {
        this.config = config;
    }

    public AppletConfig getConfig() {
        return config;
    }

    private String getCachedAccessToken() {
        return WxKeyManager.getInstance().getAccessToken(this);
    }

    public String getAppId() {
        return this.config == null ? null : this.config.getAppId();
    }

    public AccessTokenDto getAccessTokenByApi() {
        return WxHttpHelper.getApiDto(AccessTokenDto.class, WxUrls.getAccessToken(this.config.getAppId(), this.config.getAppSecret()));
    }

    public InputStream getAppletQrCodeStream(String page, String scene) throws Exception {
        String data = JsonHelper.serialize(new AppletQrCodeParameter(page, scene));
        WebClient webClient = new WebClient();
        InputStream inputStream = webClient.uploadStream(WxUrls.generateAppletQrCode(this.getCachedAccessToken()), data);
        return inputStream;
    }

    public AppletOpenIdDto getOpenIdByCode(String code) {
        return WxHttpHelper.getApiDto(AppletOpenIdDto.class, WxUrls.getOpenIdForApplet(code, this.config.getAppId(), this.config.getAppSecret()));
    }

}
