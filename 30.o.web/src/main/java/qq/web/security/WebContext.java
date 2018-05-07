package qq.web.security;

import qq.infrastructure.AppContext;
import qq.infrastructure.redis.WpkRedisSession;
import qq.service.security.UserSession;
import qq.util.helper.HttpHelper;
import qq.util.helper.JsonHelper;

import javax.servlet.http.HttpServletRequest;

public class WebContext {
    private final static String CacheKey = "WebContext";

    public static WebContext getCurrent() {
        HttpServletRequest request = HttpHelper.getCurrentRequest();
        WebContext instance = (WebContext) request.getAttribute(CacheKey);
        if (instance == null) {
            instance = initializeFromRequest();
            request.setAttribute(CacheKey, instance);
        }
        return instance;
    }

    public static void refreshSession(UserSession session) {
        HttpServletRequest request = HttpHelper.getCurrentRequest();
        String sessionId = getSessionId(request);
        try (WpkRedisSession redisSession = new WpkRedisSession()) {
            String cacheKey = getCacheKey(sessionId);
            redisSession.setex(cacheKey, AppContext.getAppConfig().getSessionExpireSeconds(), JsonHelper.serialize(session));
        }
    }

    private static WebContext initializeFromRequest() {
        HttpServletRequest request = HttpHelper.getCurrentRequest();
        String sessionId = getSessionId(request);
        try (WpkRedisSession redisSession = new WpkRedisSession()) {
            UserSession session;
            String cacheKey = getCacheKey(sessionId);
            if (redisSession.exists(cacheKey)) {
                session = JsonHelper.deserialize(redisSession.get(cacheKey), UserSession.class);
                redisSession.expire(cacheKey, AppContext.getAppConfig().getSessionExpireSeconds());
                return new WebContext(session);
            }
            WebIdentity identity = WebIdentity.parseFromRequest();
            if (identity == null) {
                return new WebContext(null);
            }
            session = new UserSession();//todo
            redisSession.setex(cacheKey, AppContext.getAppConfig().getSessionExpireSeconds(), JsonHelper.serialize(session));
            return new WebContext(session);
        }
    }

    private static String getSessionId(HttpServletRequest request) {
        return request.getSession().getId();
    }

    private static String getCacheKey(String sessionId) {
        return "SESSION-" + sessionId;
    }

    private UserSession session;
    private boolean isAuthenticated;

    private WebContext(UserSession session) {
        this.session = session;
        this.isAuthenticated = session != null;
    }

    public boolean isAuthenticated() {
        return this.isAuthenticated;
    }

    public UserSession getSession() {
        return this.session;
    }

    public void login(WebIdentity identity) {

    }

    public void logout() {

    }


}
