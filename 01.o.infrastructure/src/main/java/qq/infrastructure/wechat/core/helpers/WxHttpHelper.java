package qq.infrastructure.wechat.core.helpers;

import qq.infrastructure.http.WebClient;
import qq.infrastructure.logging.LogHelper;
import qq.infrastructure.wechat.core.dtos._ApiDtoBase;
import qq.util.helper.JsonHelper;

import java.io.File;

public class WxHttpHelper {

    private static String getUrlPath(String fullUrl) {
        try {
            String url = fullUrl.substring(fullUrl.indexOf("//") + 2);
            url = url.substring(url.indexOf("/"));
            int lastIndex = url.indexOf("?");
            if (lastIndex >= 0) {
                url = url.substring(0, lastIndex);
            }
            return url.replace("/", "-");
        } catch (Exception ex) {
            LogHelper.log("InvalidWXUrl", fullUrl);
            return "invalid-url-format";
        }
    }

    public static String downloadString(String url) {
        try (WebClient client = new WebClient()) {
            return client.downloadString(url);
        } catch (Exception ex) {
            LogHelper.log("wx.httperror." + getUrlPath(url), ex);
            return null;
        }
    }

    public static <T extends _ApiDtoBase> T getApiDto(Class<T> clazz, String url) {
        String html;
        try (WebClient client = new WebClient()) {
            html = client.downloadString(url);
        } catch (Exception ex) {
            LogHelper.log("wx.httperror." + getUrlPath(url), ex);
            return null;
        }
        return tryGetDto(clazz, html, url);
    }

    public static <T extends _ApiDtoBase> T postApiDto(Class<T> clazz, String url, String json) {
        String html;
        try (WebClient client = new WebClient()) {
            html = client.post(url, json);
            LogHelper.log("wx.postFile result:", html);
        } catch (Exception ex) {
            LogHelper.log("wx.httperror." + getUrlPath(url), ex);
            return null;
        }
        return tryGetDto(clazz, html, url);
    }

    public static <T extends _ApiDtoBase> T postFile(Class<T> clazz, String url, File file) {
        String html;
        try (WebClient client = new WebClient()) {
            html = client.postFile(url, file);
        } catch (Exception ex) {
            LogHelper.log("wx.httperror." + getUrlPath(url), ex);
            return null;
        }
        return tryGetDto(clazz, html, url);
    }

    private static <T extends _ApiDtoBase> T tryGetDto(Class<T> clazz, String html, String url) {
        try {
            T dto = JsonHelper.deserialize(html, clazz);
            if (dto.isSuccess() == false) {
                LogHelper.log("wx.failed." + getUrlPath(url), dto.getFullError());
            }
            return dto;
        } catch (Exception ex) {
            LogHelper.log("wx.jsonerror." + getUrlPath(url), ex);
            return null;
        }
    }


}
