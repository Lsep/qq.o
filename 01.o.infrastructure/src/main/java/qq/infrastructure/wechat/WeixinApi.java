package qq.infrastructure.wechat;


import qq.infrastructure.logging.LogHelper;
import qq.infrastructure.wechat.core.WeixinConfig;
import qq.infrastructure.wechat.core.WxKeyManager;
import qq.infrastructure.wechat.core.WxUrls;
import qq.infrastructure.wechat.core.dtos.*;
import qq.infrastructure.wechat.core.enums.MediaType;
import qq.infrastructure.wechat.core.helpers.WxHttpHelper;
import qq.infrastructure.wechat.core.parameters.QrCodeParameter;
import qq.infrastructure.wechat.core.parameters.SendMassMessageParameter;
import qq.infrastructure.wechat.core.parameters.ShortenUrlParameter;
import qq.infrastructure.wechat.messaging.core.MessageBase;
import qq.infrastructure.wechat.messaging.core.TemplateMessageBase;
import qq.util.extensions.KnownException;
import qq.util.helper.JsonHelper;

import java.io.File;

public class WeixinApi {

    private WeixinConfig config;

    protected WeixinApi() {
    }

    protected WeixinApi(WeixinConfig config) {
        this.config = config;
    }

//    public void initialize(WeixinConfig config) {
//        this.config = config;
//        System.out.println("weixin api initialized, appid = " + config.getAppId());
//    }

    public WeixinConfig getConfig() {
        return this.config;
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

    public JsApiTicketDto getJsApiTicketByApi() {
        return WxHttpHelper.getApiDto(JsApiTicketDto.class, WxUrls.getJsApiTicket(this.getCachedAccessToken()));
    }

    public OAuthOpenIdDto getOpenIdByCode(String code) {
        return WxHttpHelper.getApiDto(OAuthOpenIdDto.class, WxUrls.getOpenIdByCode(code, this.config.getAppId(), this.config.getAppSecret()));
    }

    public String getOAuthLoginUrl_Silently(String returnUrl, String state) {
        return WxUrls.getOAuthLoginUrl_Silently(returnUrl, this.config.getAppId(), state);
    }

    public String getOAuthLoginUrl_UserInfo(String returnUrl, String state) {
        return WxUrls.getOAuthLoginUrl_UserInfo(returnUrl, this.getAppId(), state);
    }


    public UserDto getUserInfo(String openId) {
        return WxHttpHelper.getApiDto(UserDto.class, WxUrls.getUserInfo(openId, this.getCachedAccessToken()));
    }

    public UserOAuthDto getUserInfo_OAuthCallback(String openId, String oauthAccessToken) {
        return WxHttpHelper.getApiDto(UserOAuthDto.class, WxUrls.getUserInfo_OAuthCallback(openId, oauthAccessToken));
    }

    public String getMenuJson() {
        return WxHttpHelper.downloadString(WxUrls.getMenu(this.getCachedAccessToken()));
    }

    public _ApiDtoBase saveMenu(String json) {
        return WxHttpHelper.postApiDto(_ApiDtoBase.class, WxUrls.saveMenu(this.getCachedAccessToken()), json);
    }

    public _ApiDtoBase deleteMenu() {
        return WxHttpHelper.postApiDto(_ApiDtoBase.class, WxUrls.deleteMenu(this.getCachedAccessToken()), null);
    }

    public String getMediaDownloadUrl(String mediaId) {
        return WxUrls.getMediaDownloadUrl(mediaId, this.getCachedAccessToken());
    }

    public _ApiDtoBase trySendMessage(String jsonData, boolean isTemplate) {
        try {
            String url = WxUrls.sendMessage(isTemplate, this.getCachedAccessToken());
            return WxHttpHelper.postApiDto(_ApiDtoBase.class, url, jsonData);
        } catch (Exception ex) {
            if (ex.getMessage() != null && ex.getMessage().contains("基础连接已经关闭")) {
                return null;
            }
            LogHelper.log("WeixinApi TrySendMessage", ex);
        }
        return null;
    }

    public _ApiDtoBase trySendMessage(MessageBase message) {
        return this.trySendMessage(message.toJson(), message instanceof TemplateMessageBase);
    }

    public String getPermanentQrCodeUrl(int codeType, String id) {
        String key = codeType + "|" + id;
        String data = JsonHelper.serialize(new QrCodeParameter("QR_LIMIT_STR_SCENE", key));
        GetQrCodeDto result = WxHttpHelper.postApiDto(GetQrCodeDto.class, WxUrls.generateQrCode(this.getCachedAccessToken()), data);
        return getQrCodeUrl(result);
    }

    public String getTempQrCodeUrl(Long autoId, int expireMinutes) {
        String data = JsonHelper.serialize(new QrCodeParameter("QR_SCENE", expireMinutes * 60, autoId));
        GetQrCodeDto result = WxHttpHelper.postApiDto(GetQrCodeDto.class, WxUrls.generateQrCode(this.getCachedAccessToken()), data);
        return getQrCodeUrl(result);
    }

    private String getQrCodeUrl(GetQrCodeDto result) {
        if (!result.isSuccess()) {
            throw new KnownException(result.getFullError());
        }
        return WxUrls.showQrCode(result.getTicket());
    }

    public UploadMediaDto uploadFile(File file, MediaType type) {
        return WxHttpHelper.postFile(UploadMediaDto.class, WxUrls.uploadMedia(type, this.getCachedAccessToken()), file);
    }

    public ShortenUrlDto shortenUrl(String longUrl) {
        String data = JsonHelper.serialize(new ShortenUrlParameter("long2short", longUrl));
        return WxHttpHelper.postApiDto(ShortenUrlDto.class, WxUrls.shortenUrl(this.getCachedAccessToken()), data);
    }

    public SendMassMessageDto sendMass(String mediaId, Integer tagId) {
        SendMassMessageParameter parameter = new SendMassMessageParameter(tagId, "mpnews");
        String data = JsonHelper.serialize(parameter);
        return WxHttpHelper.postApiDto(SendMassMessageDto.class, WxUrls.sendMassMessage(this.getCachedAccessToken()), data);
    }

    public UserTagDto getUserTags() {
        return WxHttpHelper.getApiDto(UserTagDto.class, WxUrls.getUserTags(this.getCachedAccessToken()));
    }

}
