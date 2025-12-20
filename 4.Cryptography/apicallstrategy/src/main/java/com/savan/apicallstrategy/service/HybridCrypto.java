package com.savan.apicallstrategy.service;

import com.savan.apicallstrategy.model.CryptoRequest;
import com.savan.apicallstrategy.util.CibilCryptoService;
import com.savan.apicallstrategy.util.ICICICryptoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.savan.apicallstrategy.constants.CryptoConstants.*;

@Slf4j
@Component
public class HybridCrypto implements CryptoStrategy {

    @Autowired
    private ICICICryptoService iciciCryptoService;

    @Autowired
    private CibilCryptoService cibilCryptoService;

    @Override
    public String process(CryptoRequest cryptoRequest) throws Exception {
        String publicCertPath = cryptoRequest.getPublicCertPath();
        String privateKeyPath = cryptoRequest.getPrivateKeyPath();
        String data = cryptoRequest.getData();

        switch (cryptoRequest.getServiceType()) {
            case HYBRID_ENCRYPTION_SERVICE -> {
                log.info("Inside HYBRID_ENCRYPTION_SERVICE");
                switch (cryptoRequest.getClient()) {
                    case ICICI -> {
                        log.info("Encrypting ICICI Request");
                        return iciciCryptoService.encryptPayload(data, publicCertPath);
                    }
                    case CIBIL -> {
                        log.info("Encrypting CIBIL Request");
                        return cibilCryptoService.encryptPayload(data, publicCertPath);
                    }
                    case APIFLOW -> {
                        log.info("Encrypting APIFLOW Request");
                        return "Encrypting APIFLOW Request";
                    }
                    default -> {
                        return "Client not supported";
                    }
                }
            }
            case HYBRID_DECRYPTION_SERVICE -> {
                log.info("Inside HYBRID_DECRYPTION_SERVICE");
                switch (cryptoRequest.getClient()) {
                    case ICICI -> {
                        log.info("Decrypting ICICI Request");
                        return iciciCryptoService.decryptResponse(data, privateKeyPath).toString();
                    }
                    case CIBIL -> {
                        log.info("Decrypting CIBIL Request");
                        return cibilCryptoService.decryptResponse(data, privateKeyPath).toString();
                    }
                    case APIFLOW -> {
                        log.info("Decrypting APIFLOW Request");
                        return "Decrypting APIFLOW Request";
                    }
                    default -> {
                        return "Client not supported";
                    }
                }
            }
            default -> {
                return "ServiceType not found";
            }
        }
    }
}
