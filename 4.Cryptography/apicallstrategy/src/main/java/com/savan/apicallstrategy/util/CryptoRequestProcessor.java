package com.savan.apicallstrategy.util;

import com.savan.apicallstrategy.entity.CryptoInfo;
import com.savan.apicallstrategy.entity.CryptoInfoService;
import com.savan.apicallstrategy.model.CryptoRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.savan.apicallstrategy.constants.CryptoConstants.*;

@Slf4j
@Component
public class CryptoRequestProcessor {

    @Autowired
    private CryptoGatewayService cryptoGatewayService;

    @Autowired
    private CryptoInfoService cryptoInfoService;

    // Encryption
    public void performEncryption(Exchange exchange) throws Exception {
        String client = exchange.getMessage().getHeader(CLIENT, String.class);
        String cryptoStrategy = exchange.getMessage().getHeader(CRYPTO_STRATEGY, String.class);
        log.info("CLIENT: {} & CRYPTO_STRATEGY: {}", client, cryptoStrategy);
        CryptoInfo cryptoInfo = cryptoInfoService.findByCryptoStrategyAndClient(cryptoStrategy, client);
        log.info("cryptoInfo: {}", cryptoInfo);
        String requestBody = exchange.getMessage().getBody(String.class);
        log.info("Initial Request-Body before api call: {}", requestBody);
        if(cryptoInfo != null){
            boolean optedDecryption = false;
            CryptoRequest cryptoRequest = new CryptoRequest();
            cryptoRequest.setData(requestBody);
            cryptoRequest.setClient(cryptoInfo.getClient());
            if(cryptoInfo.getEncryptionOpted()) {
                switch (cryptoStrategy) {
                    case SYMMETRIC_CRYPTO -> {
                        cryptoRequest.setAesKey(cryptoInfo.getAesKey());
                        cryptoRequest.setCryptoStrategy(SYMMETRIC_CRYPTO);
                        cryptoRequest.setServiceType(SYMMETRIC_ENCRYPTION_SERVICE);
                        requestBody = cryptoGatewayService.process(cryptoRequest).toString();
                    }
                    case ASYMMETRIC_CRYPTO -> {
                        cryptoRequest.setPublicCertPath(cryptoInfo.getPublicCertPath());
                        cryptoRequest.setCryptoStrategy(ASYMMETRIC_CRYPTO);
                        cryptoRequest.setServiceType(ASYMMETRIC_ENCRYPTION_SERVICE);
                        requestBody = cryptoGatewayService.process(cryptoRequest).toString();
                    }
                    case HYBRID_CRYPTO -> {
                        cryptoRequest.setPublicCertPath(cryptoInfo.getPublicCertPath());
                        cryptoRequest.setCryptoStrategy(HYBRID_CRYPTO);
                        cryptoRequest.setServiceType(HYBRID_ENCRYPTION_SERVICE);
                        requestBody = cryptoGatewayService.process(cryptoRequest).toString();
                    }
                    case ASYMMETRIC_CRYPTO_P12 -> {
                        cryptoRequest.setP12Path(cryptoInfo.getP12Path());
                        cryptoRequest.setP12Password(cryptoInfo.getP12Password());
                        cryptoRequest.setCryptoStrategy(ASYMMETRIC_CRYPTO_P12);
                        cryptoRequest.setServiceType(ASYMMETRIC_P12_API_CLIENT_SERVICE);
                        requestBody = cryptoGatewayService.process(cryptoRequest).toString();
                    }
                }
            }
            if(cryptoInfo.getDecryptionOpted()){
                optedDecryption = true;
                cryptoRequest.setPrivateKeyPath(cryptoInfo.getPrivateKeyPath());
                exchange.setProperty(CRYPTO_REQUEST, cryptoRequest);
            }
            exchange.setProperty(OPTED_DECRYPTION, optedDecryption);
            exchange.getMessage().setBody(requestBody);
        }
    }

    // Decryption
    public void preformDecryption(Exchange exchange) throws Exception {
        String responseBody = exchange.getMessage().getBody(String.class);
        log.info("Response-Body after api call: {}", responseBody);
        Boolean optedDecryption = exchange.getProperty(OPTED_DECRYPTION, Boolean.class);
        if(optedDecryption){
            CryptoRequest cryptoRequest = exchange.getProperty(CRYPTO_REQUEST, CryptoRequest.class);
            cryptoRequest.setData(responseBody);
            String cryptoStrategy = cryptoRequest.getCryptoStrategy();
            switch (cryptoStrategy) {
                case SYMMETRIC_CRYPTO -> {
                    cryptoRequest.setCryptoStrategy(SYMMETRIC_CRYPTO);
                    cryptoRequest.setServiceType(SYMMETRIC_DECRYPTION_SERVICE);
                    responseBody = cryptoGatewayService.process(cryptoRequest).toString();
                }
                case ASYMMETRIC_CRYPTO -> {
                    cryptoRequest.setCryptoStrategy(ASYMMETRIC_CRYPTO);
                    cryptoRequest.setServiceType(ASYMMETRIC_DECRYPTION_SERVICE);
                    responseBody = cryptoGatewayService.process(cryptoRequest).toString();
                }
                case HYBRID_CRYPTO -> {
                    cryptoRequest.setCryptoStrategy(HYBRID_CRYPTO);
                    cryptoRequest.setServiceType(HYBRID_DECRYPTION_SERVICE);
                    responseBody = cryptoGatewayService.process(cryptoRequest).toString();
                }
                case ASYMMETRIC_CRYPTO_P12 -> {
                    cryptoRequest.setCryptoStrategy(ASYMMETRIC_CRYPTO_P12);
                    cryptoRequest.setServiceType(ASYMMETRIC_P12_API_CLIENT_SERVICE);
                    responseBody = cryptoGatewayService.process(cryptoRequest).toString();
                }
            }
        }
        exchange.getMessage().setBody(responseBody);
    }
}
