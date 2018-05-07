package qq.web.controller;

import org.springframework.web.servlet.ModelAndView;
import qq.infrastructure.AppContext;
import qq.infrastructure.logging.LogHelper;
import qq.util.extensions.Action1;
import qq.util.extensions.KnownException;
import qq.util.extensions.ThrowableAction;
import qq.util.extensions.ThrowableFunction1;
import qq.util.helper.ExceptionHelper;
import qq.util.helper.HttpHelper;
import qq.util.helper.RequestHelper;
import qq.util.web.StandardJsonResult;
import qq.web.viewModel.LayoutViewModel;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class _ControllerBase {

    protected String getErrorViewPath() {
        return "/modules/_shared/error.jsp";
    }

    protected String getAreaPathPrefix() {
        return null;
    }

    protected <T> StandardJsonResult<T> tryJson(ThrowableFunction1<T> func) {
        StandardJsonResult result = new StandardJsonResult();
        try {
            result.setValue(func.apply());
            result.setSuccess(true);
        } catch (KnownException ex) {
            result.fail(ex.getMessage(), ex.getCode());
        } catch (Throwable ex) {
            result.fail(this.getUserMessage(ex), "-1");
            LogHelper.log("_ControllerBase.tryJson", ex);
        }
        return result;
    }

    protected StandardJsonResult tryJson(ThrowableAction action) {
        StandardJsonResult result = new StandardJsonResult();
        try {
            action.apply();
            result.setSuccess(true);
        } catch (KnownException ex) {
            result.fail(ex.getMessage(), ex.getCode());
        } catch (Throwable ex) {
            result.fail(this.getUserMessage(ex), "-1");
            LogHelper.log("_ControllerBase.tryJson", ex);
        }
        return result;
    }

    protected ModelAndView tryView(String path, ThrowableFunction1<Object> getModel) {
        return this.tryView(path, getModel, null);
    }

    protected ModelAndView tryView(String path, ThrowableFunction1<Object> getModel, Action1<ModelAndView> mvBuilder) {
        ModelAndView mv = new ModelAndView();
        String viewName = path.endsWith(".jsp") ? this.getAreaPathPrefix() + path : path;
        try {
            mv.setViewName(viewName);
            if (getModel != null) {
                mv.addObject("model", getModel.apply());
            }
            if (mvBuilder != null) {
                mvBuilder.apply(mv);
            }
            return mv;
        } catch (Throwable ex) {
            if (ex instanceof KnownException) {
                if (RequestHelper.isAjax()) {
                    return this.writeToResponse(ex.getMessage());
                }
                return this.error(ex.getMessage());
            }
            LogHelper.log("_ControllerBase.tryView", ex);
            if (RequestHelper.isAjax()) {
                return this.writeToResponse(this.getUserMessage(ex));
            }
            return this.error(this.getUserMessage(ex));
        }
    }


    private ModelAndView writeToResponse(String message) {
        try {
            HttpServletResponse response = HttpHelper.getCurrentResponse();
            response.setStatus(210);
            response.setHeader("Content-Type", "application/json;charset=utf-8");
            response.getWriter().write(message);
        } catch (IOException e) {
            LogHelper.log("response.write", e);
        }
        return null;
    }

    private String getUserMessage(Throwable ex) {
        return AppContext.getAppConfig().isTestServer() ? ExceptionHelper.getFullHtmlMessage(ex) : "服务器未知错误，请刷新页面重试";
    }

    protected ModelAndView error(String message) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName(this.getErrorViewPath());

        LayoutViewModel model = new LayoutViewModel("出错啦");
        model.setError(message);

        mv.addObject("model", model);
        return mv;
    }

    protected void validate(boolean valueIsValid, String errorMessage) {
        if (!valueIsValid) {
            throw new KnownException(errorMessage);
        }
    }

}
