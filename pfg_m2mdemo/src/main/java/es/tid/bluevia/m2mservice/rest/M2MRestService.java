package es.tid.bluevia.m2mservice.rest;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import net.opengis.gml.v_3_1_1.TimeInstantType;
import net.opengis.sos.v_1_0_0.InsertObservation;
import net.opengis.sos.v_1_0_0.ObjectFactory;
import net.opengis.swe.v_1_0_1.QuantityPropertyType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.tid.bluevia.m2mservice.api.AssetBean;
import es.tid.bluevia.m2mservice.api.AssetDetailBean;
import es.tid.bluevia.m2mservice.api.AssetListBean;
import es.tid.bluevia.m2mservice.api.Bean;
import es.tid.bluevia.m2mservice.api.DeviceDetailBean;
import es.tid.bluevia.m2mservice.api.DeviceListbean;
import es.tid.bluevia.m2mservice.api.ModelBean;
import es.tid.bluevia.m2mservice.api.ModelDetailBean;
import es.tid.bluevia.m2mservice.api.ModelListBean;
import es.tid.bluevia.m2mservice.api.SubscriptionDetail;
import es.tid.bluevia.m2mservice.api.SubscriptionList;
import es.tid.bluevia.m2mservice.api.auxbeans.DataDetailList;
import es.tid.bluevia.m2mservice.api.auxbeans.RegistroAlerta;
import es.tid.bluevia.m2mservice.client.M2MClient;
import es.tid.bluevia.m2mservice.client.SMSClient;
import es.tid.bluevia.m2mservice.utils.PropertiesUtils;
import es.tid.bluevia.m2mservice.utils.RegistroAlertasSingleton;

@WebService(serviceName = "M2MRestService", name = "M2MRestService")
@Produces("application/json")
public class M2MRestService {
    public static final String RESOURCE = "/M2MRestService";
    private static Logger logger = LoggerFactory.getLogger(M2MRestService.class);
    private final String userName = PropertiesUtils.readProperty("m2m.userName");
    private final String userPassw = PropertiesUtils.readProperty("m2m.userPassw");
    private final String basicURL = PropertiesUtils.readProperty("m2m.url.basic");
    private final String modelPath = PropertiesUtils.readProperty("models.path");
    private final String devicePath = PropertiesUtils.readProperty("devices.path");
    private final String assetPath = PropertiesUtils.readProperty("assets.path");
    private final String subscriptionPath = PropertiesUtils.readProperty("subscriptions.path");
    private final String subscriptionDeletePath = PropertiesUtils.readProperty("subscriptionsDelete.path");
    private final String smsurl = PropertiesUtils.readProperty("sms.url");
    private final String sender = PropertiesUtils.readProperty("sms.sender");
    private final String phoneNumber = PropertiesUtils.readProperty("sms.phoneNumber");

    @WebMethod
    @GET
    @Path("/subscriptions")
    public Response getListSubscription() throws Exception {
        ResponseBuilder rb = Response.status(Response.Status.OK.getStatusCode());
        M2MClient m2mClient = new M2MClient(basicURL, userName, userPassw);
        // rb.entity(m2mClient.getListBean(assetPath));

        Bean b = null;
        ObjectMapper mapper = new ObjectMapper();
        b = mapper.readValue(m2mClient.getListBean(subscriptionPath), SubscriptionList.class);
        rb.entity(b);

        return rb.build();
    }

    @WebMethod
    @GET
    @Path("/assets")
    public Response getListAssets() throws Exception {
        ResponseBuilder rb = Response.status(Response.Status.OK.getStatusCode());
        M2MClient m2mClient = new M2MClient(basicURL, userName, userPassw);
        // rb.entity(m2mClient.getListBean(assetPath));

        Bean b = null;
        ObjectMapper mapper = new ObjectMapper();
        b = mapper.readValue(m2mClient.getListBean(assetPath), AssetListBean.class);
        rb.entity(b);

        return rb.build();
    }

    @WebMethod
    @GET
    @Path("/devices")
    public Response getListDevices() throws Exception {
        ResponseBuilder rb = Response.status(Response.Status.OK.getStatusCode());
        M2MClient m2mClient = new M2MClient(basicURL, userName, userPassw);
        // rb.entity(m2mClient.getListBean(devicePath));
        Bean b = null;
        ObjectMapper mapper = new ObjectMapper();
        b = mapper.readValue(m2mClient.getListBean(devicePath), DeviceListbean.class);
        rb.entity(b);

        return rb.build();
    }

    @WebMethod
    @GET
    @Path("/models")
    public Response getListModels() throws Exception {
        ResponseBuilder rb = Response.status(Response.Status.OK.getStatusCode());
        M2MClient m2mClient = new M2MClient(basicURL, userName, userPassw);
        // rb.entity(m2mClient.getListBean(modelPath));

        Bean b = null;
        ObjectMapper mapper = new ObjectMapper();
        b = mapper.readValue(m2mClient.getListBean(modelPath), ModelListBean.class);
        rb.entity(b);

        return rb.build();
    }

    @WebMethod
    @GET
    @Path("/subscriptions/{subscriptionId}")
    public Response getSubscription(@PathParam("subscriptionId") final String subscriptionId) throws Exception {
        ResponseBuilder rb = Response.status(Response.Status.OK.getStatusCode());
        M2MClient m2mClient = new M2MClient(basicURL, userName, userPassw);
        // rb.entity(m2mClient.getBean(assetPath, assetId));

        Bean b = null;
        ObjectMapper mapper = new ObjectMapper();
        b = mapper.readValue(m2mClient.getBean("/subscriptions", subscriptionId), SubscriptionDetail.class);
        rb.entity(b);

        return rb.build();
    }

    @WebMethod
    @GET
    @Path("/assets/{assetId}")
    public Response getAsset(@PathParam("assetId") final String assetId) throws Exception {
        ResponseBuilder rb = Response.status(Response.Status.OK.getStatusCode());
        M2MClient m2mClient = new M2MClient(basicURL, userName, userPassw);
        // rb.entity(m2mClient.getBean(assetPath, assetId));

        Bean b = null;
        ObjectMapper mapper = new ObjectMapper();
        b = mapper.readValue(m2mClient.getBean(assetPath, assetId), AssetDetailBean.class);
        rb.entity(b);

        return rb.build();
    }

    @WebMethod
    @GET
    @Path("/devices/{deviceId}")
    public Response getDevice(@PathParam("deviceId") final String deviceId) throws Exception {
        ResponseBuilder rb = Response.status(Response.Status.OK.getStatusCode());
        M2MClient m2mClient = new M2MClient(basicURL, userName, userPassw);
        // rb.entity(m2mClient.getBean(devicePath, deviceId));

        Bean b = null;
        ObjectMapper mapper = new ObjectMapper();
        b = mapper.readValue(m2mClient.getBean(devicePath, deviceId), DeviceDetailBean.class);
        rb.entity(b);

        return rb.build();
    }

    @WebMethod
    @GET
    @Path("/models/{modelId}")
    public Response getModel(@PathParam("modelId") final String modelId) throws Exception {
        ResponseBuilder rb = Response.status(Response.Status.OK.getStatusCode());
        M2MClient m2mClient = new M2MClient(basicURL, userName, userPassw);
        // rb.entity(m2mClient.getBean(modelPath, modelId));

        Bean b = null;
        ObjectMapper mapper = new ObjectMapper();
        b = mapper.readValue(m2mClient.getBean(modelPath, modelId), ModelDetailBean.class);
        rb.entity(b);
        return rb.build();
    }

    @WebMethod
    @GET
    @Path("/assets/{assetId}/data")
    public Response getAssetData(@PathParam("assetId") final String assetId,
        @QueryParam("interval") String interval, @QueryParam("sortBy") String sortBy,
        @QueryParam("attribute") String attribute, @QueryParam("value") String value
        , @QueryParam("scope") String scope, @QueryParam("property") String property
        , @QueryParam("param") String param, @QueryParam("name") String name
        , @QueryParam("limit") String limit, @Context UriInfo ui) throws Exception {
        ResponseBuilder rb = Response.status(Response.Status.OK.getStatusCode());
        M2MClient m2mClient = new M2MClient(basicURL, userName, userPassw);
        StringBuilder sb = new StringBuilder();

        MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        Set<Entry<String, List<String>>> parameterC = queryParams.entrySet();
        int count = parameterC.size();
        for (Entry<String, List<String>> parameter : queryParams.entrySet()) {
            String k = parameter.getKey();
            String v = parameter.getValue().iterator().next();
            count--;
            sb.append(k);
            sb.append("=");
            sb.append(URLEncoder.encode(v, "UTF-8"));
            if (count > 0) {
                sb.append("&");
            }
        }
        System.err.println("QUERY PARAMETERS: " + sb.toString());
        // m2mClient.getDataBean(assetPath, assetId, sb.toString());
        Bean b = null;
        ObjectMapper mapper = new ObjectMapper();
        b = mapper.readValue(m2mClient.getDataBean(assetPath, assetId, sb.toString()), DataDetailList.class);
        rb.entity(b);
        return rb.build();
    }

    @WebMethod
    @GET
    @Path("/devices/{deviceId}/data")
    public Response getDeviceData(@PathParam("deviceId") final String deviceId,
        @QueryParam("interval") String interval, @QueryParam("sortBy") String sortBy,
        @QueryParam("attribute") String attribute, @QueryParam("value") String value
        , @QueryParam("scope") String scope, @QueryParam("property") String property
        , @QueryParam("param") String param, @QueryParam("name") String name
        , @QueryParam("limit") String limit, @Context UriInfo ui) throws Exception {
        ResponseBuilder rb = Response.status(Response.Status.OK.getStatusCode());
        M2MClient m2mClient = new M2MClient(basicURL, userName, userPassw);
        StringBuilder sb = new StringBuilder();
        sb.append("?");
        MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        Set<Entry<String, List<String>>> parameterC = queryParams.entrySet();
        int count = parameterC.size();
        for (Entry<String, List<String>> parameter : queryParams.entrySet()) {
            String k = parameter.getKey();
            String v = parameter.getValue().iterator().next();
            count--;
            sb.append(k);
            sb.append("=");
            sb.append(v);
            if (count > 0) {
                sb.append("&");
            }
        }
        System.err.println("QUERY PARAMETERS: " + sb.toString());
        // rb.entity(m2mClient.getDataBean(devicePath, deviceId, sb.toString()));

        Bean b = null;
        ObjectMapper mapper = new ObjectMapper();
        b = mapper.readValue(m2mClient.getDataBean(devicePath, deviceId, sb.toString()), DataDetailList.class);
        rb.entity(b);

        return rb.build();
    }

    @WebMethod
    @GET
    @Path("/models/{modelId}/data")
    public Response getModelData(@PathParam("modelId") final String modelId,
        @QueryParam("interval") String interval, @QueryParam("sortBy") String sortBy,
        @QueryParam("attribute") String attribute, @QueryParam("value") String value
        , @QueryParam("scope") String scope, @QueryParam("property") String property
        , @QueryParam("param") String param, @QueryParam("name") String name
        , @QueryParam("limit") String limit, @Context UriInfo ui) throws Exception {
        ResponseBuilder rb = Response.status(Response.Status.OK.getStatusCode());
        M2MClient m2mClient = new M2MClient(basicURL, userName, userPassw);
        StringBuilder sb = new StringBuilder();
        sb.append("?");
        MultivaluedMap<String, String> queryParams = ui.getQueryParameters();
        Set<Entry<String, List<String>>> parameterC = queryParams.entrySet();
        int count = parameterC.size();
        for (Entry<String, List<String>> parameter : queryParams.entrySet()) {
            String k = parameter.getKey();
            String v = parameter.getValue().iterator().next();
            count--;
            sb.append(k);
            sb.append("=");
            sb.append(v);
            if (count > 0) {
                sb.append("&");
            }
        }
        System.err.println("QUERY PARAMETERS: " + sb.toString());
        rb.entity(m2mClient.getDataBean(modelPath, modelId, sb.toString()));
        return rb.build();
    }

    @WebMethod
    @POST
    @Path("/assets")
    @Consumes("application/json")
    public Response createAsset(AssetBean b) throws Exception {
        ResponseBuilder rb = Response.status(Response.Status.CREATED.getStatusCode());

        ObjectMapper mapper = new ObjectMapper();
        ByteArrayOutputStream baosRequest = new ByteArrayOutputStream();
        mapper.writeValue(baosRequest, b);
        M2MClient m2mClient = new M2MClient(basicURL, userName, userPassw);
        // m2mClient.create(b, assetPath);
        M2MRestService.logger.debug("Object: " + baosRequest.toString());
        rb.entity(b);
        rb.header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS, DELETE")
            .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Session").allow("OPTIONS");
        return rb.build();
    }

    @WebMethod
    @POST
    @Path("/devices")
    @Consumes("application/json")
    public Response addDevice(String b) throws Exception {
        ResponseBuilder rb = Response.status(Response.Status.OK.getStatusCode());
        M2MRestService.logger.debug("Object: " + b);
        M2MClient m2mClient = new M2MClient(basicURL, userName, userPassw);
        m2mClient.create(b, devicePath);
        rb.entity(b);
        rb.header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS, DELETE")
            .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Session").allow("OPTIONS");
        return rb.build();
    }

    @WebMethod
    @POST
    @Path("/models")
    @Consumes("application/json")
    public Response createDevice(ModelBean b) throws Exception {
        ResponseBuilder rb = Response.status(Response.Status.CREATED.getStatusCode());

        ObjectMapper mapper = new ObjectMapper();
        ByteArrayOutputStream baosRequest = new ByteArrayOutputStream();
        mapper.writeValue(baosRequest, b);
        M2MClient m2mClient = new M2MClient(basicURL, userName, userPassw);
        // m2mClient.create(b, modelPath);
        M2MRestService.logger.debug("Object: " + baosRequest.toString());
        rb.entity(b);
        rb.header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS, DELETE")
            .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Session").allow("OPTIONS");
        return rb.build();
    }

    @WebMethod
    @POST
    @Path("/subscriptions")
    @Consumes("application/json")
    public Response addAlert(String b) throws Exception {
        ResponseBuilder rb = Response.status(Response.Status.OK.getStatusCode());
        M2MRestService.logger.debug("Object: " + b);
        M2MClient m2mClient = new M2MClient(basicURL, userName, userPassw);
        m2mClient.create(b, subscriptionPath);
        rb.entity(b);
        rb.header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS, DELETE")
            .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Session").allow("OPTIONS");
        return rb.build();
    }

    @WebMethod
    @GET
    @Path("/status")
    public Response getStatus() throws Exception {
        ResponseBuilder rb = Response.status(Response.Status.OK.getStatusCode());
        String status = "OK";
        rb.entity(status);
        return rb.build();
    }

    @WebMethod
    @POST
    @RolesAllowed("ROLE_ADMIN")
    @Path("/status")
    @Consumes("application/json")
    public Response checkStatus(String b) throws Exception {
        ResponseBuilder rb = Response.status(Response.Status.OK.getStatusCode());
        M2MRestService.logger.debug("Object: " + b);
        rb.entity(b);
        return rb.allow("OPTIONS").build();
    }

    @WebMethod
    @POST
    @Path("/alerts")
    @Consumes("text/xml")
    public void receiveAlert(@RequestBody String b) throws Exception {
        M2MRestService.logger.debug("Object: " + b);

        JAXBContext jaxbContext = JAXBContext.newInstance(InsertObservation.class, ObjectFactory.class,
            net.opengis.om.v_1_0_0.ObjectFactory.class,
            net.opengis.gml.v_3_1_1.ObjectFactory.class, net.opengis.ows.v_1_1_0.ObjectFactory.class,
            net.opengis.swe.v_1_0_1.ObjectFactory.class, net.opengis.sensorml.v_1_0_1.ObjectFactory.class,
            net.opengis.sos.v_1_0_0.filter.v_1_1_0.ObjectFactory.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        StringReader reader = new StringReader(b);
        InsertObservation insertObservation = (InsertObservation) unmarshaller.unmarshal(reader);
        QuantityPropertyType q = (QuantityPropertyType) insertObservation.getObservation().getResult();
        TimeInstantType time = (TimeInstantType) insertObservation.getObservation().getSamplingTime().getTimeObject()
            .getValue();
        RegistroAlertasSingleton registro = RegistroAlertasSingleton.getInstance();
        RegistroAlerta alerta = new RegistroAlerta();
        alerta.setDeviceId(insertObservation.getAssignedSensorId());
        alerta.setFechaRegistro(time.getTimePosition().getValue());
        alerta.setParametro(q.getQuantity().getDefinition());
        alerta.setValor(String.valueOf(q.getQuantity().getValue()));
        registro.getRegistro().add(0, alerta);
        // Enviar SMS Administrador
        SMSClient smsClient = new SMSClient(smsurl);
        // check telephone number?
        String mensaje = "Alerta recibida:\nDispositivo: "
            + alerta.getDeviceId()
            + " Fecha: "
            + alerta.getFechaRegistro()
            + " Parametro: "
            + alerta.getParametro()
            + " Valor: "
            + alerta.getValor();
        smsClient.sendSMS(sender, phoneNumber,
            mensaje);
    }

    @WebMethod
    @GET
    @Path("/alerts")
    public Response getAlerts() throws Exception {
        ResponseBuilder rb = Response.status(Response.Status.OK.getStatusCode());
        RegistroAlertasSingleton registro = RegistroAlertasSingleton.getInstance();
        rb.entity(registro.getRegistro());
        return rb.build();
    }

    @WebMethod
    @DELETE
    @Path("/assets/{assetId}")
    @Consumes("application/json")
    public Response deleteDevice(@PathParam("assetId") final String assetId) throws Exception {
        M2MClient m2mClient = new M2MClient(basicURL, userName, userPassw);
        m2mClient.delete(assetId, assetPath);
        ResponseBuilder rb = Response.status(Response.Status.NO_CONTENT);
        rb.header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS, DELETE")
            .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Session").allow("OPTIONS");
        return rb.build();
    }

    @WebMethod
    @DELETE
    @Path("/subscriptions/{subscriptionsId}")
    @Consumes("application/json")
    public Response deletesubscription(@PathParam("subscriptionsId") final String subscriptionsId) throws Exception {
        M2MClient m2mClient = new M2MClient(basicURL, userName, userPassw);
        m2mClient.delete(subscriptionsId, subscriptionDeletePath);
        ResponseBuilder rb = Response.status(Response.Status.NO_CONTENT);
        rb.header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS, DELETE")
            .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Session").allow("OPTIONS");
        return rb.build();
    }
}
