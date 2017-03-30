package es.tid.bluevia.m2mservice.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @RequestMapping("/doIndex")
    public ModelAndView getIndex() {

        ModelAndView mV = new ModelAndView("contenidoIndex");

        return mV;
    }
}
