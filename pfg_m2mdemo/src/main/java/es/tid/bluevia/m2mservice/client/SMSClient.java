package es.tid.bluevia.m2mservice.client;

import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author agc327
 *
 */
public class SMSClient extends Client {
	/**
     * Logging system.
     */
    private static Logger logger = LoggerFactory.getLogger(SMSClient.class);
	
	public SMSClient (String url) {		
		super( url);
	}
	
	/**
	 * Send SMS.
	 * @param sender
	 * @param addressee
	 * @param message	 
	 * @throws Exception
	 */
	public  void sendSMS(String sender, String addressee, String message) throws Exception {
		logger.debug("sendSMS");
		logger.debug("sender:" + sender);
		logger.debug("addressee:" + addressee);		
		logger.debug("message:" + message);
		
		DefaultHttpClient httpclient = new DefaultHttpClient();		        
		try {
			logger.debug("Url:" + url);
            HttpPost httppost = new HttpPost(url);
            
            ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("from", sender));
            postParameters.add(new BasicNameValuePair("to", addressee));
            postParameters.add(new BasicNameValuePair("message", message));

            httppost.setEntity(new UrlEncodedFormEntity(postParameters));            
            logger.debug("executing request" + httppost.getRequestLine());                                                        
            //send MMS
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity received = response.getEntity();
            int status = response.getStatusLine().getStatusCode();
            logger.debug("----------------------------------------");
            logger.debug(response.getStatusLine().toString());
            String resonseContent = "";
            if (received != null) {
            	logger.debug("Response content length: " + received.getContentLength());
            	logger.debug("----------------------------------------");
            	logger.debug("Response content:");
            	resonseContent = EntityUtils.toString(received);
            	logger.debug(resonseContent);
            }
            if (status != 201){
            	logger.error("Error Status different to 200 received " + resonseContent);
    			//throw new Exception (resonseContent);
            }                       
		}catch (Exception e){
			logger.error("Error:" + e.toString() , e);
			throw new Exception ("Error Sendind SMS: " + e.toString());
		}finally {
            httpclient.getConnectionManager().shutdown();
        }
	}

}
