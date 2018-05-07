package qq.web.core;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import qq.web.controller.ErrorController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ModelAndView handleOther(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        return ErrorController.handleException(request, response, ex);
    }

}
