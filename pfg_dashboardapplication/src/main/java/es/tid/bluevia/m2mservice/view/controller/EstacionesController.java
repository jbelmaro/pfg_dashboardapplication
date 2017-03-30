package es.tid.bluevia.m2mservice.view.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import es.tid.bluevia.m2mservice.api.DeviceBean;
import es.tid.bluevia.m2mservice.api.auxbeans.DeviceProps;

@Controller
public class EstacionesController {

    @RequestMapping("/doEstaciones")
    public ModelAndView getEstaciones() {

        ModelAndView mV = new ModelAndView("estaciones");
        String atributo = "estaciones";
        mV.addObject("atributo", atributo);
        List<DeviceBean> devices = new ArrayList<DeviceBean>();
        for (int i = 0; i < 20; i++) {
            DeviceBean db = new DeviceBean();
            db.setName("elemento " + i);
            DeviceProps dp = new DeviceProps();
            db.setDeviceProps(dp);
            db.getDeviceProps().setSerialNumber(i);
            db.setModel("modelo " + i);
            devices.add(db);
        }
        mV.addObject("devices", devices);
        return mV;
    }
}
