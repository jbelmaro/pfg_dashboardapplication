package es.tid.bluevia.m2mservice.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtils {
    private static final Logger log = LoggerFactory.getLogger(PropertiesUtils.class);

    /**
     * Read property.
     * 
     * @param property
     *            the property
     * 
     * @return the string
     */
    public static String readProperty(String property) {
        PropertiesUtils.log.info("Into readProperty method");
        String val = null;
        try {
            Properties prop = new Properties();
            InputStream is = null;
            try {
                prop.load(PropertiesUtils.class.getResourceAsStream("/app.properties"));
            } catch (IOException ioe) {
                PropertiesUtils.log.error(ioe.getMessage(), ioe);
                ;
            }
            val = prop.getProperty(property);
            PropertiesUtils.log.info("value property " + property + "  " + val);
        } catch (java.security.AccessControlException ex) {
            PropertiesUtils.log.error("NO se pudo leer la propiedad: java.security.policy");
            return "";
        }
        return val;
    }

    public String readExternalProperty(String property) {
        PropertiesUtils.log.info("Into readProperty method");
        String val = null;
        try {
            Properties prop = new Properties();
            InputStream is = null;
            try {
                prop.load(new FileInputStream("./NubaConf/app.properties"));
            } catch (IOException ioe) {
                PropertiesUtils.log.error(ioe.getMessage(), ioe);
            }
            val = prop.getProperty(property);
            PropertiesUtils.log.info("value property " + property + "  " + val);
        } catch (java.security.AccessControlException ex) {
            PropertiesUtils.log.error("NO se pudo leer la propiedad: java.security.policy");
            return "";
        }
        return val;
    }

    /**
     * Set External property
     * 
     * @param property
     * @param value
     */
    public void setExternalProperty(String property, String value) {
        PropertiesUtils.log.info("Into readProperty method");
        String val = null;
        try {
            Properties prop = new Properties();
            InputStream is = null;
            try {
                prop.load(new FileInputStream("./NubaConf/app.properties"));
                prop.setProperty(property, value);
                PropertiesUtils.log.debug("save properties");
                OutputStream outputStream = new FileOutputStream("./NubaConf/app.properties");
                prop.store(outputStream, "");
            } catch (IOException ioe) {
                PropertiesUtils.log.error(ioe.getMessage(), ioe);
            }

        } catch (java.security.AccessControlException ex) {
            PropertiesUtils.log.error("Not possible to read the property: java.security.policy");
        }
    }

}
