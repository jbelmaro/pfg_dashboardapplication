package es.tid.bluevia.m2mservice.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    @RequestMapping("/doLogin")
    public ModelAndView getInformes() {
        ModelAndView mV = new ModelAndView("login");
        return mV;
    }

}
