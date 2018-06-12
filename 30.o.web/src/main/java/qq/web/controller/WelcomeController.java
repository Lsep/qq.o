package qq.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author qq
 * @date 2018/6/12
 */
@Controller
@RequestMapping("/")
public class WelcomeController extends _ControllerBase {

    @RequestMapping("")
    public ModelAndView welcome() {
        return super.tryView("welcome", null);
    }
}
