package qq.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import qq.infrastructure.AppContext;
import qq.infrastructure.logging.LogHelper;
import qq.util.extensions.KnownException;
import qq.util.helper.ExceptionHelper;
import qq.util.helper.JsonHelper;
import qq.util.helper.RequestHelper;
import qq.util.web.StandardJsonResult;
import qq.web.viewModel.LayoutViewModel;
import qq.web.viewModel.MessageViewModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("web-error")
@RequestMapping("/error")
public class ErrorController {
    @RequestMapping("/404")
    public ModelAndView e404(HttpServletRequest request, HttpServletResponse response) {
        return this.exception(request, response);
    }


    @RequestMapping(value = "/exception")
    public ModelAndView exception(HttpServletRequest request, HttpServletResponse response) {
        return handleException(request, response, RequestHelper.getException(request));
    }

    public static ModelAndView handleException(HttpServletRequest request, HttpServletResponse response, Throwable ex) {
        if (AppContext.getAppConfig().isPrintStackTrace() && ex != null) {
            ex.printStackTrace();
        }
        String requestUri = RequestHelper.getRequestUri(request);
        try {
            if (response.getStatus() == 404) {
                LogHelper.log("http.error.404", requestUri);
            } else {
                String fullMessage = requestUri + "\r\n<br/>" + ExceptionHelper.getFullHtmlMessage(ex);
                LogHelper.log("http.error." + response.getStatus(), fullMessage);
            }
            String userMessage = null;
            if (ex == null) {
                userMessage = getUserMessage(response.getStatus());
            } else {
                if (ex instanceof KnownException) {
                    userMessage = ex.getMessage();
                } else {
                    userMessage = AppContext.getAppConfig().isTestServer() ? ExceptionHelper.getFullHtmlMessage(ex) : getUserMessage(response.getStatus());
                }
            }

            if (RequestHelper.returnJson(request)) {
                StandardJsonResult jsonResult = new StandardJsonResult();
                jsonResult.fail(userMessage);
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(JsonHelper.serialize(jsonResult));
                return new ModelAndView();
            }
            return errorView(userMessage, requestUri);
        } catch (Exception ex2) {
            LogHelper.log("ErrorController.exception", ex2);
        }
        return errorView("对不起，服务器出错了，请重试", requestUri);
    }

    private static ModelAndView errorView(String message, String url) {
        String viewName = "pc-error";
        LayoutViewModel model = new LayoutViewModel("出错啦");
        if (url != null) {
            if (url.startsWith("/admin")) {
                viewName = "admin-error";
            } else //if (url.startsWith("/app") || url.startsWith("/dealer"))
            {
                viewName = "app-message";
                model = new MessageViewModel(model.getTitle());
            }
        }
        ModelAndView mv = new ModelAndView(viewName);
        model.setError(message);
        mv.addObject("model", model);
        return mv;
    }

    private static String getUserMessage(int status) {
        if (status >= 500) {
            return "服务器内部错误（" + status + "）";
        }
        if (status == 404) {
            return "您请求的页面（或资源）不存在，请检查资源URL地址是否正确";
        }
        if (status == 401) {
            return "您的权限不足，请尝试登陆或注销后重新登陆";
        }
        if (status == 403) {
            return "禁止访问403";
        }
        return "未知错误";
    }


}
