package es.tid.bluevia.m2mservice.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class InformesController {
    @RequestMapping("/doInformes")
    public ModelAndView getInformes() {
        ModelAndView mV = new ModelAndView("informes");
        return mV;
    }

}
