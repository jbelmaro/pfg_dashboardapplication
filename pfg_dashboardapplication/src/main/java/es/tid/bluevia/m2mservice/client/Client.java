package es.tid.bluevia.m2mservice.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.tid.bluevia.m2mservice.api.Bean;

public abstract class Client {
    /**
     * Logging system.
     */
    private static Logger logger = LoggerFactory.getLogger(Client.class);

    protected String url;
    protected String userName;
    protected String userPassw;

    public Client(String url, String userName, String userPassw) {
        this.url = url;
        this.userName = userName;
        this.userPassw = userPassw;
    }

    /**
     * Get Basic Authentication header.
     * 
     * @param consumerKey
     * @param consumerSecret
     * @return
     */
    public String getBasic(String consumerKey, String consumerSecret) {
        String pair = consumerKey + ":" + consumerSecret;
        String encoded = new String(Base64.encodeBase64(pair.getBytes()));
        String authentication = "Basic " + encoded;
        Client.logger.debug("Authentication:" + authentication);
        return authentication;
    }

    /**
     * Parse request object to JSON.
     * 
     * @param request
     * @return JSON String
     * @throws GRETAException
     */
    protected String parseRequest2JSON(Object request) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        ByteArrayOutputStream baosRequest = new ByteArrayOutputStream();
        try {
            mapper.writeValue(baosRequest, request);
            return baosRequest.toString();
        } catch (Exception e) {
            Client.logger.error("Error:" + e.toString());
            throw new Exception("Wrong request " + request + ".Error: " + e.getMessage());
        } finally {
            if (baosRequest != null) {
                try {
                    baosRequest.close();
                } catch (IOException e) {
                    Client.logger.error("Error closing ByteOutputStream.Message:" + e.getMessage());
                }
            }
        }
    }

    protected HttpResponse createRequest(String userName, String userPassw, Bean request, String path,
        DefaultHttpClient httpclient)
        throws Exception {
        HttpPost httppost = new HttpPost(url + path);
        String basicAuth = getBasic(userName, userPassw);
        httppost.addHeader("Authorization", basicAuth);

        // create body.
        String body = parseRequest2JSON(request);
        Client.logger.debug(body);
        StringEntity sended = new StringEntity(body);
        sended.setChunked(true);
        // add entity to send
        httppost.setEntity(sended);
        Client.logger.debug("executing request" + httppost.getRequestLine());
        return httpclient.execute(httppost);
    }

}