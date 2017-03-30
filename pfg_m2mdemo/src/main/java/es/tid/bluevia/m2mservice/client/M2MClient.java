package es.tid.bluevia.m2mservice.client;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author b.jmbo
 * 
 */
public class M2MClient extends Client {
    /**
     * Logging system.
     */
    private static Logger logger = LoggerFactory.getLogger(M2MClient.class);

    public M2MClient(String url, String userName, String userPassw) {
        super(url, userName, userPassw);
    }

    // DELETE
    public HttpResponse delete(String request, String path) throws Exception {
        M2MClient.logger.debug("delete");
        System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
        DefaultHttpClient httpclient = new DefaultHttpClient();

        M2MClient.logger.error("Url to Delete:" + url + path);
        HttpResponse response = null;
        try {
            response = delete(userName, userPassw, request, url + path, httpclient);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "DELETE");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type");
            response.setHeader("Access-Control-Max-Age", "86400");
        } catch (NullPointerException e) {
            M2MClient.logger.error(e.getMessage());
        }
        return response;
    }

    // POST

    public HttpResponse create(String request, String path) throws Exception {
        M2MClient.logger.debug("create");
        System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
        DefaultHttpClient httpclient = new DefaultHttpClient();

        M2MClient.logger.error("Url to create:" + url + path);
        HttpResponse response = null;

        try {
            response = createRequest(userName, userPassw, request, url + path, httpclient);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type");
            response.setHeader("Access-Control-Max-Age", "86400");
        } catch (NullPointerException e) {
            M2MClient.logger.error(e.getMessage());
        }
        return response;
    }

    // GET
    public String getListBean(String path) throws Exception {
        M2MClient.logger.debug("get");
        System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
        DefaultHttpClient httpclient = new DefaultHttpClient();

        SSLContext sslContext = getSSLContext("/usr/lib/jvm/jre-1.6.0-openjdk.x86_64/lib/security/teslacerts.jks");
        SSLSocketFactory sf = new SSLSocketFactory(sslContext);
        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme scheme = new Scheme("https", sf, 443);
        httpclient.getConnectionManager().getSchemeRegistry().register(scheme);

        String responseContent = null;
        M2MClient.logger.error("Url to Get:" + url + path);
        HttpGet httpget = new HttpGet(url + path);
        HttpResponse response = httpclient.execute(httpget);
        try {

            M2MClient.logger.debug("executing request" + httpget.getRequestLine());
            HttpEntity received = response.getEntity();
            int status = response.getStatusLine().getStatusCode();
            M2MClient.logger.debug("----------------------------------------");
            M2MClient.logger.debug(response.getStatusLine().toString());

            if (received != null) {
                M2MClient.logger.debug("Response content length: " + received.getContentLength());
                M2MClient.logger.debug("----------------------------------------");
                M2MClient.logger.debug("Response content:");
                responseContent = EntityUtils.toString(received);
                M2MClient.logger.debug(responseContent);
            }
            if (status != 200) {
                M2MClient.logger.error("Error Status different to 200 received " + responseContent);
                throw new Exception(responseContent);
            }

        } catch (Exception e) {
            M2MClient.logger.error("Error:" + e.toString(), e);
            throw new Exception("Error Getting Elements: " + e.toString());
        }

        return responseContent;

    }

    public String getBean(String path, String beanID) throws Exception {
        M2MClient.logger.debug("get");
        System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
        DefaultHttpClient httpclient = new DefaultHttpClient();

        SSLContext sslContext = getSSLContext("/usr/lib/jvm/jre-1.6.0-openjdk.x86_64/lib/security/teslacerts.jks");
        SSLSocketFactory sf = new SSLSocketFactory(sslContext);
        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme scheme = new Scheme("https", sf, 443);
        httpclient.getConnectionManager().getSchemeRegistry().register(scheme);

        String responseContent = null;
        M2MClient.logger.debug("Url:" + url + path + "/" + beanID);
        HttpGet httpget = new HttpGet(url + path + "/" + beanID);
        HttpResponse response = httpclient.execute(httpget);
        try {

            M2MClient.logger.debug("executing request" + httpget.getRequestLine());
            HttpEntity received = response.getEntity();
            int status = response.getStatusLine().getStatusCode();
            M2MClient.logger.debug("----------------------------------------");
            M2MClient.logger.debug(response.getStatusLine().toString());

            if (received != null) {
                M2MClient.logger.debug("Response content length: " + received.getContentLength());
                M2MClient.logger.debug("----------------------------------------");
                M2MClient.logger.debug("Response content:");
                responseContent = EntityUtils.toString(received);
                M2MClient.logger.debug(responseContent);
            }
            if (status != 200) {
                M2MClient.logger.error("Error Status different to 200 received " + responseContent);
                throw new Exception(responseContent);
            }

        } catch (Exception e) {
            M2MClient.logger.error("Error:" + e.toString(), e);
            throw new Exception("Error Getting Elements: " + e.toString());
        }

        return responseContent;

    }

    public String getDataBean(String path, String beanID, String properties) throws Exception {
        M2MClient.logger.debug("get");
        System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
        DefaultHttpClient httpclient = new DefaultHttpClient();

        SSLContext sslContext = getSSLContext("/usr/lib/jvm/jre-1.6.0-openjdk.x86_64/lib/security/teslacerts.jks");
        SSLSocketFactory sf = new SSLSocketFactory(sslContext);
        sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Scheme scheme = new Scheme("https", sf, 443);
        httpclient.getConnectionManager().getSchemeRegistry().register(scheme);

        String responseContent = null;
        M2MClient.logger.debug("Url:" + url + path + "/" + beanID + "/data?" + properties);
        HttpGet httpget = new HttpGet(url + path + "/" + beanID + "/data?" + properties);
        HttpResponse response = httpclient.execute(httpget);
        try {

            M2MClient.logger.debug("executing request" + httpget.getRequestLine());
            HttpEntity received = response.getEntity();
            int status = response.getStatusLine().getStatusCode();
            M2MClient.logger.debug("----------------------------------------");
            M2MClient.logger.debug(response.getStatusLine().toString());

            if (received != null) {
                M2MClient.logger.debug("Response content length: " + received.getContentLength());
                M2MClient.logger.debug("----------------------------------------");
                M2MClient.logger.debug("Response content:");
                responseContent = EntityUtils.toString(received);
                M2MClient.logger.debug(responseContent);
            }
            if (status != 200) {
                M2MClient.logger.error("Error Status different to 200 received " + responseContent);
                throw new Exception(responseContent);
            }

        } catch (Exception e) {
            M2MClient.logger.error("Error:" + e.toString(), e);
            throw new Exception("Error Getting Elements: " + e.toString());
        }

        return responseContent;

    }

    public String getDataService(String properties) throws Exception {
        M2MClient.logger.debug("get");
        System.setProperty("sun.security.ssl.allowUnsafeRenegotiation", "true");
        DefaultHttpClient httpclient = new DefaultHttpClient();

        SSLContext sslContext = getSSLContext("/usr/lib/jvm/jre-1.6.0-openjdk.x86_64/lib/security/teslacerts.jks");
        SSLSocketFactory sf = new SSLSocketFactory(sslContext);
        sf.setHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        Scheme scheme = new Scheme("https", sf, 443);
        httpclient.getConnectionManager().getSchemeRegistry().register(scheme);

        String responseContent = null;
        M2MClient.logger.debug("Url:" + url + "/data/" + properties);
        HttpGet httpget = new HttpGet(url + "/data/" + properties);
        HttpResponse response = httpclient.execute(httpget);
        try {

            M2MClient.logger.debug("executing request" + httpget.getRequestLine());
            HttpEntity received = response.getEntity();
            int status = response.getStatusLine().getStatusCode();
            M2MClient.logger.debug("----------------------------------------");
            M2MClient.logger.debug(response.getStatusLine().toString());

            if (received != null) {
                M2MClient.logger.debug("Response content length: " + received.getContentLength());
                M2MClient.logger.debug("----------------------------------------");
                M2MClient.logger.debug("Response content:");
                responseContent = EntityUtils.toString(received);
                M2MClient.logger.debug(responseContent);
            }
            if (status != 200) {
                M2MClient.logger.error("Error Status different to 200 received " + responseContent);
                throw new Exception(responseContent);
            }

        } catch (Exception e) {
            M2MClient.logger.error("Error:" + e.toString(), e);
            throw new Exception("Error Getting Elements: " + e.toString());
        }

        return responseContent;

    }

}
