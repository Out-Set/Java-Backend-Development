package com.savan.apicallstrategy.service;

import com.savan.apicallstrategy.model.CryptoRequest;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.net.http.HttpClient;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.time.Duration;

@Service
public class P12ApiClientService implements CryptoStrategy {

    @Override
    public HttpClient process(CryptoRequest request) throws Exception {
        String p12Path = request.getP12Path();
        String password = request.getP12Password();

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(new FileInputStream(p12Path), password.toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(keyStore, password.toCharArray());

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(keyStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        return HttpClient.newBuilder()
                .sslContext(sslContext)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }
}
