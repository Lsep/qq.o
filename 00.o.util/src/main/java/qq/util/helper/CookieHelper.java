package qq.util.helper;

import qq.util.extensions.Action;
import qq.util.extensions.JList;

import javax.servlet.http.Cookie;
import java.util.Objects;

public class CookieHelper {

    public static void entryCookie(String name, int expireSeconds, Action action) {
        String value = getValue(name);
        if (!StringHelper.isNullOrWhitespace(value)) {
            return;
        }
        action.apply();
        setValue(name, "1", expireSeconds);
    }

    public static String getValue(String name) {
        Cookie cookie = JList.from(HttpHelper.getCurrentRequest().getCookies()).firstOrNull(x -> Objects.equals(x.getName(), name));
        return cookie == null ? null : cookie.getValue();
    }

    public static void setValue(String name, String value, int expireSeconds) {
        Cookie cookie = new Cookie(name, value);
//        cookie.setDomain(AppContext.getAppConfig().getCookieDomain());
        cookie.setMaxAge(expireSeconds);
        cookie.setPath("/");
        HttpHelper.getCurrentResponse().addCookie(cookie);
    }

    public static void remove(String name) {
        Cookie cookie = new Cookie(name, null);
//        cookie.setDomain(AppContext.getAppConfig().getCookieDomain());
        cookie.setMaxAge(0);
        cookie.setPath("/");
        HttpHelper.getCurrentResponse().addCookie(cookie);
    }


}
