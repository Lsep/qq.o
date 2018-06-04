package qq.infrastructure.wechat.core;

import qq.infrastructure.wechat.AppletApi;
import qq.infrastructure.wechat.WeixinApi;
import qq.infrastructure.wechat.core.dtos.AccessTokenDto;
import qq.infrastructure.wechat.core.dtos.JsApiTicketDto;

public class WxKeyManager {

    private final static WxKeyManager instance = new WxKeyManager();

    public static WxKeyManager getInstance() {
        return instance;
    }

    private WxCacheProvider cacheProvider;

    private WxKeyManager() {
    }

    public void initialize(WxCacheProvider cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    public String getAccessToken(WeixinApi api) {
        synchronized (api.getAppId().intern()) {
            AccessTokenDto tokenDto = this.cacheProvider.getAccessToken(api.getAppId());
            if (tokenDto != null && !tokenDto.isExpiringOrExpired()) {
                return tokenDto.getAccess_token();
            }
            return this.refreshAccessTokenByApi(api);
        }
    }

    public String getAccessToken(AppletApi api) {
        synchronized (api.getAppId().intern()) {
            AccessTokenDto tokenDto = this.cacheProvider.getAccessToken(api.getAppId());
            if (tokenDto != null && !tokenDto.isExpiringOrExpired()) {
                return tokenDto.getAccess_token();
            }
            return this.refreshAccessTokenByApi(api);
        }
    }

    public String getJsApiTicket(WeixinApi api) {
        synchronized (api.getAppId().intern()) {
            JsApiTicketDto ticketDto = this.cacheProvider.getJsApiTicket(api.getAppId());
            if (ticketDto != null && !ticketDto.isExpiringOrExpired()) {
                return ticketDto.getTicket();
            }
            return this.refreshJsApiTicketByApi(api);
        }
    }

    public String refreshAccessTokenByApi(WeixinApi api) {
        AccessTokenDto tokenDto = api.getAccessTokenByApi();
        if (tokenDto == null || !tokenDto.isSuccess()) {
            return null;
        }
        this.cacheProvider.setAccessToken(api.getAppId(), tokenDto);
        return tokenDto.getAccess_token();
    }

    public String refreshAccessTokenByApi(AppletApi api) {
        AccessTokenDto tokenDto = api.getAccessTokenByApi();
        if (tokenDto == null || !tokenDto.isSuccess()) {
            return null;
        }
        this.cacheProvider.setAccessToken(api.getAppId(), tokenDto);
        return tokenDto.getAccess_token();
    }

    public String refreshJsApiTicketByApi(WeixinApi api) {
        JsApiTicketDto ticketDto = api.getJsApiTicketByApi();
        if (ticketDto == null || !ticketDto.isSuccess()) {
            return null;
        }
        this.cacheProvider.setJsApiTicket(api.getAppId(), ticketDto);
        return ticketDto.getTicket();
    }

    public AccessTokenDto getCachedAccessToken(String appId) {
        return this.cacheProvider.getAccessToken(appId);
    }

    public JsApiTicketDto getCachedJsApiTicket(String appId) {
        return this.cacheProvider.getJsApiTicket(appId);
    }

}
