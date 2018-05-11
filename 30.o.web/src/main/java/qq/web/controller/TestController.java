package qq.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author qq
 * @date 2018/5/11
 */
@Controller("test-controller")
@RequestMapping("/test")
public class TestController extends _ControllerBase {

    @GetMapping()
    public ModelAndView index(){
        return super.tryView("test-index",null);
    }

}
