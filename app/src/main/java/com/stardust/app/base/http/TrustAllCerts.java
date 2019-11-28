package com.stardust.app.base.http;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * Created by bestw on 2017/10/14.
 */

public class TrustAllCerts implements X509TrustManager{
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//        try{
//
//            checkClientTrusted(chain, authType);
//
//        }catch(CertificateException e) {
//
//            e.printStackTrace();
//
//        }

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//        try{
//
//            checkServerTrusted(chain, authType);
//
//        }catch(CertificateException e) {
//
//            e.printStackTrace();
//
//        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
