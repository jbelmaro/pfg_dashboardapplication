package es.tid.bluevia.m2mservice.client;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Scanner;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    public Client(String url) {
        this.url = url;
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

    protected HttpResponse createRequest(String userName, String userPassw, String request, String path,
        DefaultHttpClient httpclient)
        throws Exception {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(path);
        HttpResponse response = null;
        Scanner in = null;
        String salida = "";
        try
        {
            StringEntity Sentity = new StringEntity(request);
            httppost.setEntity(Sentity);
            httppost.addHeader("Accept", " application/json");
            httppost.addHeader("Content-Type", " application/json");
            SSLContext sslContext = getSSLContext("/usr/lib/jvm/jre-1.6.0-openjdk.x86_64/lib/security/teslacerts.jks");
            SSLSocketFactory sf = new SSLSocketFactory(sslContext);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Scheme scheme = new Scheme("https", sf, 443);
            httpClient.getConnectionManager().getSchemeRegistry().register(scheme);
            response = httpClient.execute(httppost);
            // System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            in = new Scanner(entity.getContent());
            while (in.hasNext())
            {
                salida += in.next();
                System.out.println(in.next());
                Client.logger.error("POST REQUEST: " + request);
            }
        } catch (Exception e) {
            Client.logger.error("Error:" + e.toString());
            throw new Exception("Wrong request " + request + ".Error: " + e.getMessage());
        } finally
        {
            in.close();
        }
        return response;
    }

    protected HttpResponse delete(String userName, String userPassw, String requestToDelete, String path,
        DefaultHttpClient httpclient)
        throws Exception {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        Client.logger.error("URL TO DELETE: " + path + "/" + requestToDelete + "?force=1");
        HttpDelete httpdelete = new HttpDelete(path + "/" + requestToDelete + "?force=1");
        HttpResponse response = null;
        Scanner in = null;
        try
        {
            httpdelete.addHeader("Accept", " application/json");
            httpdelete.addHeader("Content-Type", " application/json");
            SSLContext sslContext = getSSLContext("/usr/lib/jvm/jre-1.6.0-openjdk.x86_64/lib/security/teslacerts.jks");
            SSLSocketFactory sf = new SSLSocketFactory(sslContext);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            Scheme scheme = new Scheme("https", sf, 443);
            httpClient.getConnectionManager().getSchemeRegistry().register(scheme);
            response = httpClient.execute(httpdelete);
            Client.logger.error("STATUS: " + response.getStatusLine().getStatusCode());

        } catch (Exception e) {
            Client.logger.error("Error:" + e.toString());
            throw new Exception(".Error: " + e.getMessage());
        } finally
        {
            in.close();
        }
        return response;
    }

    public SSLContext getSSLContext(String tspath)
        throws Exception {
        KeyStore ks = KeyStore.getInstance("jks");
        InputStream in = new FileInputStream(tspath);
        try {
            ks.load(in, "globserv01".toCharArray());
        } finally {
            in.close();
        }
        KeyManagerFactory keyManagerF =
            KeyManagerFactory.getInstance("SunX509");
        keyManagerF.init(ks, "globserv01".toCharArray());

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerF.getKeyManagers(), null, new SecureRandom());
        return sslContext;
    }
}