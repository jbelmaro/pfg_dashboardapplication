package es.tid.bluevia.m2mservice;

import java.io.File;

/**
 * Test application.
 * 
 * @author agc327
 * 
 */
public class ClientTest {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // String url = "http://bable01.hi.inet:8080/BlueviaAPI/SMSServlet";
        String url = "http://bable01.hi.inet:8080/BlueviaAPI/MMSServlet";
        File file = new File("D:\\4CAAST\\apistest\\src\\main\\resources\\iconos.jpg");
        // SMSClient client = new SMSClient(url);
        // client.sendSMS("4917604022357", "4917604022357", "SMS de Prueba");

    }
}