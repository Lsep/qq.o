package qq.infrastructure.wechat.core;

import qq.infrastructure.wechat.core.dtos.AccessTokenDto;
import qq.infrastructure.wechat.core.dtos.JsApiTicketDto;

public interface WxCacheProvider {

    AccessTokenDto getAccessToken(String appId);

    void setAccessToken(String appId, AccessTokenDto tokenDto);

    JsApiTicketDto getJsApiTicket(String appId);

    void setJsApiTicket(String appId, JsApiTicketDto ticketDto);
}
