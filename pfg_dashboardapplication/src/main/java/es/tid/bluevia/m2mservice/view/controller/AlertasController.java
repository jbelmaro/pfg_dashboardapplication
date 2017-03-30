package es.tid.bluevia.m2mservice.view.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.tid.bluevia.m2mservice.api.AssetListBean;
import es.tid.bluevia.m2mservice.api.Bean;
import es.tid.bluevia.m2mservice.api.ModelDetailBean;
import es.tid.bluevia.m2mservice.api.SubscriptionList;
import es.tid.bluevia.m2mservice.client.DashboardClient;
import es.tid.bluevia.m2mservice.utils.PropertiesUtils;

@Controller
public class AlertasController {
    private static Logger logger = LoggerFactory.getLogger(AlertasController.class);
    private final String userName = PropertiesUtils.readProperty("m2m.userName");
    private final String userPassw = PropertiesUtils.readProperty("m2m.userPassw");
    private final String basicURL = PropertiesUtils.readProperty("m2m.url.basic");

    @RequestMapping("/doAlertas")
    public ModelAndView getAlertas() {
        ModelAndView mV = new ModelAndView("alertas");
        DashboardClient client = new DashboardClient(basicURL, userName, userPassw);

        Bean b = null;
        ObjectMapper mapper = new ObjectMapper();
        String respuesta = client.get("http://10.95.14.162:8080/M2MService/M2MRestService/assets");
        Bean c = null;
        mapper = new ObjectMapper();
        try {
            c = mapper.readValue(respuesta, AssetListBean.class);
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

        mV.addObject("assets", c);

        respuesta = client.get("http://10.95.14.162:8080/M2MService/M2MRestService/models/estmeteo");

        Bean m = null;
        mapper = new ObjectMapper();
        try {
            m = mapper.readValue(respuesta, ModelDetailBean.class);
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

        mV.addObject("estmeteo_model", m);

        respuesta = client.get("http://10.95.14.162:8080/M2MService/M2MRestService/models/contriego");

        mapper = new ObjectMapper();
        try {
            m = mapper.readValue(respuesta, ModelDetailBean.class);
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

        mV.addObject("contriego_model", m);

        respuesta = client.get("http://10.95.14.162:8080/M2MService/M2MRestService/models/medpart");

        mapper = new ObjectMapper();
        try {
            m = mapper.readValue(respuesta, ModelDetailBean.class);
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

        mV.addObject("medpart", m);

        respuesta = client.get("http://10.95.14.162:8080/M2MService/M2MRestService/subscriptions");

        try {
            b = mapper.readValue(respuesta, SubscriptionList.class);
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

        mV.addObject("alerts", b);

        try {
            System.out.println(mapper.writeValueAsString(b));
            AlertasController.logger.debug(mapper.writeValueAsString(b));
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return mV;
    }

}
