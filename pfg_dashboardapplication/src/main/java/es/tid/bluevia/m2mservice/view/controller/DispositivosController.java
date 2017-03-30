package es.tid.bluevia.m2mservice.view.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.tid.bluevia.m2mservice.api.AssetListBean;
import es.tid.bluevia.m2mservice.api.Bean;
import es.tid.bluevia.m2mservice.api.DeviceListbean;
import es.tid.bluevia.m2mservice.api.ModelListBean;
import es.tid.bluevia.m2mservice.client.DashboardClient;
import es.tid.bluevia.m2mservice.utils.PropertiesUtils;

@Controller
public class DispositivosController {

    private static Logger logger = LoggerFactory.getLogger(DispositivosController.class);
    private final String userName = PropertiesUtils.readProperty("m2m.userName");
    private final String userPassw = PropertiesUtils.readProperty("m2m.userPassw");
    private final String basicURL = PropertiesUtils.readProperty("m2m.url.basic");

    @RequestMapping("/doDispositivos")
    public ModelAndView getDispositivos() {

        ModelAndView mV = new ModelAndView("dispositivos");
        DashboardClient client = new DashboardClient(basicURL, userName, userPassw);
        Bean b = null;
        ObjectMapper mapper = new ObjectMapper();
        String respuesta = client.get("http://10.95.14.162:8080/M2MService/M2MRestService/models");
        b = null;
        mapper = new ObjectMapper();
        try {
            b = mapper.readValue(respuesta, ModelListBean.class);
            DispositivosController.logger.debug(mapper.writeValueAsString(b));
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mV.addObject("models", b);

        respuesta = client.get("http://10.95.14.162:8080/M2MService/M2MRestService/assets");
        try {
            b = mapper.readValue(respuesta, AssetListBean.class);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Bean d = null;

        if (((AssetListBean) b).getCount() != 0) {
            respuesta = client.get("http://10.95.14.162:8080/M2MService/M2MRestService/devices");

            try {
                d = mapper.readValue(respuesta, DeviceListbean.class);
            } catch (JsonParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JsonMappingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mV.addObject("devices", d);
        }
        else {
            mV.addObject("devices", b);
        }

        return mV;
    }
}
