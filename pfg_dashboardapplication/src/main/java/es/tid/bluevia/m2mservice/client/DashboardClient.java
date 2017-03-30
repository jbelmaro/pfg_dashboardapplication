package es.tid.bluevia.m2mservice.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author b.jmbo
 */
public class DashboardClient extends Client {
    /**
     * Logging system.
     * 
     * @author b.jmbo
     */
    private static Logger logger = LoggerFactory.getLogger(DashboardClient.class);

    public DashboardClient(String url, String userName, String userPassw) {
        super(url, userName, userPassw);
    }

    public String get(String path) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        String responseContent = null;
        HttpGet httpget = new HttpGet(path);
        HttpResponse response = null;

        try {
            response = httpclient.execute(httpget);
            httpget.addHeader("Accept", "application/json");
            DashboardClient.logger.debug("executing request" + httpget.getRequestLine());
            HttpEntity received = response.getEntity();
            int status = response.getStatusLine().getStatusCode();
            DashboardClient.logger.debug("----------------------------------------");
            DashboardClient.logger.debug(response.getStatusLine().toString());

            if (received != null) {
                DashboardClient.logger.debug("Response content length: " + received.getContentLength());
                DashboardClient.logger.debug("----------------------------------------");
                DashboardClient.logger.debug("Response content:");
                responseContent = EntityUtils.toString(received);
                DashboardClient.logger.debug(responseContent);
            }
            if (status != 200) {
                DashboardClient.logger.error("Error Status different to 200 received " + responseContent);
                throw new Exception(responseContent);
            }

        } catch (Exception e) {
            DashboardClient.logger.error("Error:" + e.toString(), e);
            try {
                throw new Exception("Error Getting Elements: " + e.toString());
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        return responseContent;

    }
}
