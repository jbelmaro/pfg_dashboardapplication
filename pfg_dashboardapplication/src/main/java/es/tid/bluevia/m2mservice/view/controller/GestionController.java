package es.tid.bluevia.m2mservice.view.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.QueryParam;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.tid.bluevia.m2mservice.api.AssetDetailBean;
import es.tid.bluevia.m2mservice.api.auxbeans.DataDetailList;
import es.tid.bluevia.m2mservice.api.auxbeans.DatosInforme;
import es.tid.bluevia.m2mservice.client.DashboardClient;
import es.tid.bluevia.m2mservice.utils.PropertiesUtils;

@Controller
@RequestMapping("/gestion")
public class GestionController {
    private static Logger logger = LoggerFactory.getLogger(GestionController.class);
    private final String userName = PropertiesUtils.readProperty("m2m.userName");
    private final String userPassw = PropertiesUtils.readProperty("m2m.userPassw");
    private final String basicURL = PropertiesUtils.readProperty("m2m.url.basic");
    private HttpClient client;

    @RequestMapping(value = "/informes", method = RequestMethod.GET)
    public ModelAndView gestion() {

        ModelAndView mV = new ModelAndView("informes");
        String atributo = "informes";
        mV.addObject("atributo", atributo);

        return mV;
    }

    @RequestMapping(value = "/getHistorico/", method = RequestMethod.POST, consumes = {"text/plain", "application/*"})
    public void getHistorico(HttpServletResponse response, HttpServletRequest request) {
        BufferedReader in;
        String[] array = null;
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Session");
        try {
            in = new BufferedReader(
                new InputStreamReader(request.getInputStream()));

            String inputLine;
            StringBuffer body = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                body.append(inputLine);
            }
            in.close();
            // print result
            GestionController.logger.debug("RECIBIDO: " + URLDecoder.decode(body.toString(), "UTF-8").split("=")[0]);
            array = URLDecoder.decode(body.toString(), "UTF-8").split("=")[0].split(",");

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        File salida = new File("historico.xlsx");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(salida);
        } catch (FileNotFoundException e1) {

            e1.printStackTrace();
        }

        // Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Historico");

        // This data needs to be written (Object[])
        Map<Integer, Object[]> tabla = new TreeMap<Integer, Object[]>();
        int indexTabla = 0;
        tabla.put(indexTabla, new Object[]{"FECHA", "VALOR"});
        indexTabla++;
        // Iterate over data and write to sheet

        GestionController.logger.debug(" " + array.length);
        int i = 0;
        while (i < array.length) {

            if (i < (array.length - 1)) {
                tabla.put(indexTabla, new Object[]{array[i], array[i + 1]});
                GestionController.logger.debug(tabla.get(indexTabla)[0] + " : " + tabla.get(indexTabla)[1]);
                indexTabla++;
            }
            i = i + 2;
        }
        int rownum = 0;
        GestionController.logger.debug("Tabla: " + tabla.size());
        for (int j = 0; j < tabla.size(); j++)
        {
            Object[] objArr = tabla.get(j);
            GestionController.logger.debug(objArr[0] + " : " + objArr[1]);

            Row row = sheet.createRow(rownum++);
            int cellnum = 0;
            for (Object obj : objArr)
            {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Integer) {
                    cell.setCellValue((Integer) obj);
                }
            }
        }

        try
        {
            // Write the workbook in file system

            workbook.write(out);
            out.close();
            GestionController.logger.debug("historico.xlsx written successfully on disk.");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        response.setContentType("application/xlsx");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + salida.getName() + "\"");
        try {
            // get your file as InputStream
            InputStream is = new FileInputStream(salida);
            // copy it to response's OutputStream
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            throw new RuntimeException("IOError writing file to output stream");
        }

    }

    @RequestMapping(value = "/informes/{location}/{device}/{param}/{interval}", method = RequestMethod.GET)
    public void getDataInterval(HttpServletResponse response, @PathVariable("location") String location,
        @PathVariable("device") String device,
        @PathVariable("param") String param, @PathVariable("interval") String interval) {

        DashboardClient client = new DashboardClient(basicURL, userName, userPassw);
        DataDetailList b = null;
        ObjectMapper mapper = new ObjectMapper();

        String intervalQuery = null;
        String query = null;
        try {
            intervalQuery = "interval=" + URLEncoder.encode(interval, "UTF-8") + "&";
        } catch (UnsupportedEncodingException e1) {

            e1.printStackTrace();
        }

        ArrayList<String> datosRespuesta = new ArrayList<String>();

        ArrayList<String> datosParametros = new ArrayList<String>();
        String datosEnvio = "[";
        GestionController.logger.debug("Device: " + device);
        if (device.equals("medpart")) {

            String[] splitted = param.split(",");
            for (int i = 0; i < splitted.length; i++) {
                datosParametros = new ArrayList<String>();
                query = intervalQuery + "attribute=" + splitted[i];
                GestionController.logger.debug("Query Gases: " + query);
                // String url = "http://10.95.14.162:8080/M2MService/M2MRestService/assets/estmeteo1/data?"

                String url = "http://10.95.14.162:8080/M2MService/M2MRestService/assets/" + device + location +
                    "/data?"
                    + query;

                try {
                    b = mapper.readValue(
                        client.get(url),
                        DataDetailList.class);
                } catch (JsonParseException e) {

                    e.printStackTrace();
                } catch (JsonMappingException e) {

                    e.printStackTrace();
                } catch (IOException e) {

                    e.printStackTrace();
                }
                if ((b != null) && (b.getCount() >= 0)) {

                    DatosInforme datos = new DatosInforme();
                    String datosCreados = "[";
                    for (int j = 0; j < b.getCount(); j++) {
                        SimpleDateFormat formatPrevio = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        SimpleDateFormat formatFinal = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                        try {
                            datos.setFecha(formatFinal.format(formatPrevio.parse(b.getData()[j].getSt())));
                        } catch (ParseException e) {

                            e.printStackTrace();
                        }
                        // DecimalFormat df = new DecimalFormat("#.##");
                        datos.setValor(b.getData()[j].getMs().getV());
                        datosParametros.add("[\"" + datos.getFecha() + "\"," + datos.getValor() + "]");
                    }
                    for (int k = 0; k < b.getCount(); k++) {
                        if (k < (b.getCount() - 1)) {
                            datosCreados += datosParametros.get(k) + ",";
                        } else {
                            datosCreados += datosParametros.get(k);
                        }
                    }
                    datosCreados += "]";
                    datosRespuesta.add(datosCreados);

                } else {
                    response.setContentType("application/json");
                    try {
                        response.sendError(400, "No hay datos disponibles");
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }
            response.setContentType("application/json");

            for (int h = 0; h < datosRespuesta.size(); h++) {
                if (h < (datosRespuesta.size() - 1)) {
                    datosEnvio += datosRespuesta.get(h) + ",";
                } else {
                    datosEnvio += datosRespuesta.get(h);
                }
            }
            datosEnvio += "]";
            if (datosRespuesta.size() > 1) {
                try {
                    response.getWriter().write(datosEnvio);
                } catch (IOException e) {

                    e.printStackTrace();
                }

            }
        } else {
            query = intervalQuery + "attribute=" + param;
            GestionController.logger.debug("Query: " + query);

            String url = "http://10.95.14.162:8080/M2MService/M2MRestService/assets/" + device + location
                + "/data?"
                + query;
            GestionController.logger.debug("URL TO QUERY: " + url);
            try {
                b = mapper.readValue(
                    client.get(url),
                    DataDetailList.class);
            } catch (JsonParseException e) {

                e.printStackTrace();
            } catch (JsonMappingException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
            if ((b != null) && (b.getCount() >= 0)) {

                DatosInforme datos = new DatosInforme();
                String datosCreados = "[";
                for (int i = 0; i < b.getCount(); i++) {
                    SimpleDateFormat formatPrevio = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    SimpleDateFormat formatFinal = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    try {
                        datos.setFecha(formatFinal.format(formatPrevio.parse(b.getData()[i].getSt())));
                    } catch (ParseException e) {

                        e.printStackTrace();
                    }
                    // DecimalFormat df = new DecimalFormat("#.##");
                    datos.setValor(b.getData()[i].getMs().getV());
                    if (i < (b.getCount() - 1)) {
                        datosCreados += ("[\"" + datos.getFecha() + "\"," + datos.getValor() + "],");
                    } else {
                        datosCreados += ("[\"" + datos.getFecha() + "\"," + datos.getValor() + "]");
                    }
                }
                datosCreados += "]";
                response.setContentType("application/json");
                try {
                    response.getWriter().write(datosCreados);
                } catch (IOException e) {

                    e.printStackTrace();
                }
            } else {
                response.setContentType("application/json");
                try {
                    response.sendError(400, "No hay datos disponibles");
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }

    }

    @RequestMapping(value = "/informes/{location}/{device}/{param}/", method = RequestMethod.GET)
    public void getData(HttpServletResponse response, @PathVariable("location") String location,
        @PathVariable("device") String device,
        @PathVariable("param") String param) {

        DashboardClient client = new DashboardClient(basicURL, userName, userPassw);
        DataDetailList b = null;
        ObjectMapper mapper = new ObjectMapper();

        String query = null;
        ArrayList<String> datosRespuesta = new ArrayList<String>();

        ArrayList<String> datosParametros = new ArrayList<String>();
        String datosEnvio = "[";
        GestionController.logger.debug("Device: " + device);
        if (device.equals("medpart")) {

            String[] splitted = param.split(",");
            for (int i = 0; i < splitted.length; i++) {
                datosParametros = new ArrayList<String>();
                query = "attribute=" + splitted[i];
                GestionController.logger.debug("Query Gases: " + query);
                // String url = "http://10.95.14.162:8080/M2MService/M2MRestService/assets/estmeteo1/data?"

                String url = "http://10.95.14.162:8080/M2MService/M2MRestService/assets/" + device + location +
                    "/data?"
                    + query;

                try {
                    b = mapper.readValue(
                        client.get(url),
                        DataDetailList.class);
                } catch (JsonParseException e) {

                    e.printStackTrace();
                } catch (JsonMappingException e) {

                    e.printStackTrace();
                } catch (IOException e) {

                    e.printStackTrace();
                }
                if ((b != null) && (b.getCount() > 0)) {

                    DatosInforme datos = new DatosInforme();
                    String datosCreados = "[";
                    for (int j = 0; j < b.getCount(); j++) {
                        SimpleDateFormat formatPrevio = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        SimpleDateFormat formatFinal = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                        try {
                            datos.setFecha(formatFinal.format(formatPrevio.parse(b.getData()[j].getSt())));
                        } catch (ParseException e) {

                            e.printStackTrace();
                        }
                        // DecimalFormat df = new DecimalFormat("#.##");
                        datos.setValor(b.getData()[j].getMs().getV());
                        datosParametros.add("[\"" + datos.getFecha() + "\"," + datos.getValor() + "]");
                    }
                    for (int k = 0; k < b.getCount(); k++) {
                        if (k < (b.getCount() - 1)) {
                            datosCreados += datosParametros.get(k) + ",";
                        } else {
                            datosCreados += datosParametros.get(k);
                        }
                    }
                    datosCreados += "]";
                    datosRespuesta.add(datosCreados);

                } else {
                    response.setContentType("application/json");
                    try {
                        response.sendError(400, "No hay datos disponibles");
                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }
            response.setContentType("application/json");

            for (int h = 0; h < datosRespuesta.size(); h++) {
                if (h < (datosRespuesta.size() - 1)) {
                    datosEnvio += datosRespuesta.get(h) + ",";
                } else {
                    datosEnvio += datosRespuesta.get(h);
                }
            }
            datosEnvio += "]";
            if (datosRespuesta.size() > 1) {
                try {
                    response.getWriter().write(datosEnvio);
                } catch (IOException e) {

                    e.printStackTrace();
                }
            } else {
                try {
                    response.getWriter().write(datosRespuesta.get(0));
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        } else {
            query = "attribute=" + param;
            GestionController.logger.debug("Query: " + query);
            String url = "http://10.95.14.162:8080/M2MService/M2MRestService/assets/" + device + location + "/data?"
                + query;

            try {
                b = mapper.readValue(
                    client.get(url),
                    DataDetailList.class);
            } catch (JsonParseException e) {

                e.printStackTrace();
            } catch (JsonMappingException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
            if ((b != null) && (b.getCount() > 0)) {

                DatosInforme datos = new DatosInforme();
                String datosCreados = "[";
                for (int i = 0; i < b.getCount(); i++) {
                    SimpleDateFormat formatPrevio = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    SimpleDateFormat formatFinal = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    try {
                        datos.setFecha(formatFinal.format(formatPrevio.parse(b.getData()[i].getSt())));
                    } catch (ParseException e) {

                        e.printStackTrace();
                    }
                    // DecimalFormat df = new DecimalFormat("#.##");
                    datos.setValor(b.getData()[i].getMs().getV());
                    if (i < (b.getCount() - 1)) {
                        datosCreados += ("[\"" + datos.getFecha() + "\"," + datos.getValor() + "],");
                    } else {
                        datosCreados += ("[\"" + datos.getFecha() + "\"," + datos.getValor() + "]");
                    }
                }
                datosCreados += "]";
                response.setContentType("application/json");
                try {
                    response.getWriter().write(datosCreados);
                } catch (IOException e) {

                    e.printStackTrace();
                }
            } else {
                response.setContentType("application/json");
                try {
                    response.sendError(400, "No hay datos disponibles");
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }

    }

    @RequestMapping(value = "/getGases/{location}/{device}", method = RequestMethod.GET)
    public void getDataGases(HttpServletResponse response, @PathVariable("location") String location,
        @PathVariable("device") String device) {

        DashboardClient client = new DashboardClient(basicURL, userName, userPassw);
        AssetDetailBean b = null;
        ObjectMapper mapper = new ObjectMapper();
        String url = "http://10.95.14.162:8080/M2MService/M2MRestService/assets/" + device + location;

        try {
            b = mapper.readValue(
                client.get(url),
                AssetDetailBean.class);
        } catch (JsonParseException e) {

            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        if ((b != null) && (b.getData().getSensorData().length > 0)) {
            List<DatosInforme> datos = new ArrayList<DatosInforme>();
            String datosCreados = "[";

            for (int i = 0; i < b.getData().getSensorData().length; i++) {
                DatosInforme elemento = new DatosInforme();

                if (b.getData().getSensorData()[i].getMs().getP().equals("NO2Concentration")) {
                    elemento.setFecha("NO2");
                    elemento.setValor(b.getData().getSensorData()[i].getMs().getV());
                    datos.add(elemento);
                } else if (b.getData().getSensorData()[i].getMs().getP().equals("NOConcentration")) {
                    elemento.setFecha("NO");
                    elemento.setValor(b.getData().getSensorData()[i].getMs().getV());
                    datos.add(elemento);
                } else if (b.getData().getSensorData()[i].getMs().getP().equals("CO2Concentration")) {
                    elemento.setFecha("CO2");
                    elemento.setValor(b.getData().getSensorData()[i].getMs().getV());
                    datos.add(elemento);
                } else if (b.getData().getSensorData()[i].getMs().getP().equals("COConcentration")) {
                    elemento.setFecha("CO");
                    elemento.setValor(b.getData().getSensorData()[i].getMs().getV());
                    datos.add(elemento);
                } else if (b.getData().getSensorData()[i].getMs().getP().equals("CH4Concentration")) {
                    elemento.setFecha("CH4");
                    elemento.setValor(b.getData().getSensorData()[i].getMs().getV());
                    datos.add(elemento);
                } else if (b.getData().getSensorData()[i].getMs().getP().equals("O2Concentration")) {
                    elemento.setFecha("O2");
                    elemento.setValor(b.getData().getSensorData()[i].getMs().getV());
                    datos.add(elemento);
                } else if (b.getData().getSensorData()[i].getMs().getP().equals("O3Concentration")) {
                    elemento.setFecha("O3");
                    elemento.setValor(b.getData().getSensorData()[i].getMs().getV());
                    datos.add(elemento);
                } else if (b.getData().getSensorData()[i].getMs().getP().equals("GasConcentration")) {
                    elemento.setFecha("Gas");
                    elemento.setValor(b.getData().getSensorData()[i].getMs().getV());
                    datos.add(elemento);
                }

            }

            Iterator<DatosInforme> iterator = datos.iterator();
            int j = 0;
            while (iterator.hasNext()) {
                DatosInforme elementoLista = iterator.next();
                if (j < (datos.size() - 1)) {
                    datosCreados += ("[\"" + elementoLista.getFecha() + "\"," + elementoLista.getValor() + "],");
                } else {
                    datosCreados += ("[\"" + elementoLista.getFecha() + "\"," + elementoLista.getValor() + "]");
                }
                j++;
            }

            datosCreados += "]";
            GestionController.logger.debug(datosCreados);
            response.setContentType("application/json");
            try {
                response.getWriter().write(datosCreados);
            } catch (IOException e) {

                e.printStackTrace();
            }
        } else {
            response.setContentType("application/json");
            try {
                response.sendError(400, "No hay datos disponibles");
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

    }

    @RequestMapping(value = "/devices/", method = RequestMethod.POST,
        headers = {"content-type=application/json", "token=dashboardapplication"})
    public void addDevice(HttpServletResponse response, @RequestBody String datos) {

        String url = "http://10.95.14.162:8080/M2MService/M2MRestService/devices";
        HttpPost post;
        // add header

        HttpResponse r = null;
        BufferedReader rd = null;

        StringBuffer result = new StringBuffer();
        String line = "";
        post = new HttpPost(url);
        // add header
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Connection", "keep-alive");
        try {
            post.setEntity(new StringEntity(datos));
        } catch (UnsupportedEncodingException e3) {

            e3.printStackTrace();
        }

        try {
            r = client.execute(post);
        } catch (ClientProtocolException e2) {

            e2.printStackTrace();
        } catch (IOException e2) {

            e2.printStackTrace();
        }
        GestionController.logger.debug("\nSending 'POST' request to URL : " + url);
        GestionController.logger.debug("Post parameters : " + datos);
        GestionController.logger.debug("Response Code : " +
            r.getStatusLine().getStatusCode());

        try {
            rd = new BufferedReader(
                new InputStreamReader(r.getEntity().getContent()));
        } catch (IllegalStateException e1) {

            e1.printStackTrace();
        } catch (IOException e1) {

            e1.printStackTrace();
        }

        try {
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }

        GestionController.logger.debug(result.toString());
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600000");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Session");
            response.getWriter().write(result.toString());
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/alerts/", method = RequestMethod.POST,
        headers = {"content-type=application/json", "token=dashboardapplication"})
    public void addAlert(HttpServletResponse response, @RequestBody String datos) {

        String url = "http://10.95.14.162:8080/M2MService/M2MRestService/subscriptions";
        HttpPost post;
        // add header

        HttpResponse r = null;
        BufferedReader rd = null;

        StringBuffer result = new StringBuffer();
        String line = "";
        post = new HttpPost(url);
        // add header
        post.setHeader("Content-Type", "application/json");
        post.setHeader("Connection", "keep-alive");

        try {
            post.setEntity(new StringEntity(datos));
        } catch (UnsupportedEncodingException e3) {

            e3.printStackTrace();
        }

        try {
            r = client.execute(post);
        } catch (ClientProtocolException e2) {

            e2.printStackTrace();
        } catch (IOException e2) {

            e2.printStackTrace();
        }
        GestionController.logger.debug("\nSending 'POST' request to URL : " + url);
        GestionController.logger.debug("Post parameters : " + datos);
        GestionController.logger.debug("Response Code : " +
            r.getStatusLine().getStatusCode());

        try {
            rd = new BufferedReader(
                new InputStreamReader(r.getEntity().getContent()));
        } catch (IllegalStateException e1) {

            e1.printStackTrace();
        } catch (IOException e1) {

            e1.printStackTrace();
        }

        try {
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }

        GestionController.logger.debug(result.toString());
        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "36000");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Session");
            response.getWriter().write(result.toString());

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/alerts/{alertId}", method = RequestMethod.DELETE, headers = {"token=dashboardapplication"})
    public void deleteAlert(HttpServletResponse response, @PathVariable("alertId") String alertId) {

        String url = "http://10.95.14.162:8080/M2MService/M2MRestService/subscriptions/" + alertId;
        HttpDelete delete;
        // add header

        HttpResponse r = null;
        delete = new HttpDelete(url);
        // add header
        delete.setHeader("Content-Type", "application/json");
        delete.setHeader("Connection", "keep-alive");

        try {
            r = client.execute(delete);
        } catch (ClientProtocolException e2) {

            e2.printStackTrace();
        } catch (IOException e2) {

            e2.printStackTrace();
        }
        GestionController.logger.debug("\nSending 'DELETE' request to URL : " + url);
        GestionController.logger.debug("Delete parameters : " + alertId);
        GestionController.logger.debug("Response Code : " +
            r.getStatusLine().getStatusCode());

        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "36000");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Session");
            response.getWriter().write("RESPONSE CODE: " + r.getStatusLine().getStatusCode());
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/devices/{deviceId}", method = RequestMethod.DELETE, headers = {"token=dashboardapplication"})
    public void deleteDevice(HttpServletResponse response, @PathVariable("deviceId") String alertId) {

        String url = "http://10.95.14.162:8080/M2MService/M2MRestService/assets/" + alertId;
        HttpDelete delete;
        // add header

        HttpResponse r = null;
        delete = new HttpDelete(url);
        // add header
        delete.setHeader("Content-Type", "application/json");
        delete.setHeader("Connection", "keep-alive");

        try {
            r = client.execute(delete);
        } catch (ClientProtocolException e2) {

            e2.printStackTrace();
        } catch (IOException e2) {

            e2.printStackTrace();
        }
        GestionController.logger.debug("\nSending 'DELETE' request to URL : " + url);
        GestionController.logger.debug("Delete parameters : " + alertId);
        GestionController.logger.debug("Response Code : " +
            r.getStatusLine().getStatusCode());

        try {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "36000");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Session");
            response.getWriter().write("RESPONSE CODE: " + r.getStatusLine().getStatusCode());
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(HttpServletResponse response, @QueryParam("username") String username,
        @QueryParam("password") String password) {
        String login = "http://10.95.14.162:8080/M2MService/j_spring_security_check?j_username=" + username + "&j_password=" + password;
        client = new DefaultHttpClient();

        HttpPost post = new HttpPost(login);
        // add header

        HttpResponse r = null;
        try {
            r = client.execute(post);
        } catch (ClientProtocolException e2) {

            e2.printStackTrace();
        } catch (IOException e2) {

            e2.printStackTrace();
        }
        GestionController.logger.debug("\nSending 'POST' request to URL : " + login);
        GestionController.logger.debug("Response Code : " +
            r.getStatusLine().getStatusCode());

        BufferedReader rd = null;
        try {
            rd = new BufferedReader(
                new InputStreamReader(r.getEntity().getContent()));
        } catch (IllegalStateException e1) {

            e1.printStackTrace();
        } catch (IOException e1) {

            e1.printStackTrace();
        }

        StringBuffer result = new StringBuffer();
        String line = "";
        try {
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }

        GestionController.logger.debug(result.toString());
        try {
            response.getWriter().write(result.toString());
            response.setStatus(r.getStatusLine().getStatusCode());
        } catch (IOException e) {

            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public void logout(HttpServletResponse response) {
        String logout = "http://10.95.14.162:8080/M2MService/j_spring_security_logout";
        client = new DefaultHttpClient();

        HttpPost post = new HttpPost(logout);
        // add header

        HttpResponse r = null;
        try {
            r = client.execute(post);
        } catch (ClientProtocolException e2) {

            e2.printStackTrace();
        } catch (IOException e2) {

            e2.printStackTrace();
        }
        GestionController.logger.debug("\nSending 'POST' request to URL : " + logout);
        GestionController.logger.debug("Response Code : " +
            r.getStatusLine().getStatusCode());

        BufferedReader rd = null;
        try {
            rd = new BufferedReader(
                new InputStreamReader(r.getEntity().getContent()));
        } catch (IllegalStateException e1) {

            e1.printStackTrace();
        } catch (IOException e1) {

            e1.printStackTrace();
        }

        StringBuffer result = new StringBuffer();
        String line = "";
        try {
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }

        GestionController.logger.debug(result.toString());
        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}
