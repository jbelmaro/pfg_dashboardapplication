package es.tid.bluevia.m2mservice.security;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class ReloadableX509TrustManager
    implements X509TrustManager {
    private final String trustStorePath;
    private X509TrustManager trustManager;

    public X509TrustManager getTrustManager() {
        return trustManager;
    }

    public void setTrustManager(X509TrustManager trustManager) {
        this.trustManager = trustManager;
    }

    private final List<Certificate> tempCertList = new ArrayList<Certificate>();

    public ReloadableX509TrustManager(String tspath)
        throws Exception {
        this.trustStorePath = tspath;
        System.out.println("!");
        reloadTrustManager();
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain,
        String authType) throws CertificateException {
        try {
            trustManager.checkServerTrusted(chain, authType);
        } catch (CertificateException cx) {
            addServerCertAndReload(chain[0], true);
            trustManager.checkServerTrusted(chain, authType);
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        X509Certificate[] issuers = trustManager.getAcceptedIssuers();
        return issuers;
    }

    private void reloadTrustManager() throws Exception {

        // load keystore from specified cert store (or default)
        KeyStore ts = KeyStore.getInstance("jks");
        InputStream in = new FileInputStream(trustStorePath);
        try {
            ts.load(in, "globserv01".toCharArray());
        } finally {
            in.close();
        }

        // add all temporary certs to KeyStore (ts)
        for (Certificate cert : tempCertList) {
            ts.setCertificateEntry(UUID.randomUUID().toString(), cert);
        }

        // initialize a new TMF with the ts we just loaded
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(
            TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ts);

        // acquire X509 trust manager from factory
        TrustManager tms[] = tmf.getTrustManagers();
        System.out.println("CERTIFICADO: " + tmf.getProvider().getName());

        for (int i = 0; i < tms.length; i++) {
            if (tms[i] instanceof X509TrustManager) {
                trustManager = (X509TrustManager) tms[i];
                System.out.println("CERTIFICADOTRUST: " + trustManager.getAcceptedIssuers()[0].getSigAlgName());

                return;
            }
        }

        throw new NoSuchAlgorithmException(
            "No X509TrustManager in TrustManagerFactory");

    }

    private void addServerCertAndReload(Certificate cert,
        boolean permanent) {
        try {
            if (permanent) {
                // import the cert into file trust store
                // Google "java keytool source" or just ...
                Runtime.getRuntime().exec("keytool -importcert ...");
            } else {
                tempCertList.add(cert);
            }
            System.out.println("!!");

            reloadTrustManager();
        } catch (Exception ex) { /* ... */
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        // TODO Auto-generated method stub

    }

}