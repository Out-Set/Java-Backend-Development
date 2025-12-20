package com.savan.apicallstrategy.service;

import com.savan.apicallstrategy.model.CryptoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static com.savan.apicallstrategy.constants.CryptoConstants.*;

@Slf4j
@Component
public class SymmetricCrypto implements CryptoStrategy {

    private static final String ALGORITHM = "AES";

    @Override
    public String process(CryptoRequest cryptoRequest) throws Exception {
        String aesKey = cryptoRequest.getAesKey();
        String data = cryptoRequest.getData();

        switch (cryptoRequest.getServiceType()) {
            case SYMMETRIC_ENCRYPTION_SERVICE -> {
                log.info("Inside SYMMETRIC_ENCRYPTION_SERVICE");
                SecretKeySpec secretKey = new SecretKeySpec(aesKey.getBytes(), ALGORITHM);
                Cipher cipher = Cipher.getInstance(ALGORITHM);

                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] encrypted = cipher.doFinal(data.getBytes());
                return Base64.getEncoder().encodeToString(encrypted);
            }
            case SYMMETRIC_DECRYPTION_SERVICE -> {
                log.info("Inside SYMMETRIC_DECRYPTION_SERVICE");
                SecretKeySpec secretKey = new SecretKeySpec(aesKey.getBytes(), ALGORITHM);
                Cipher cipher = Cipher.getInstance(ALGORITHM);

                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                byte[] decoded = Base64.getDecoder().decode(data);
                return new String(cipher.doFinal(decoded));
            }
            default -> {
                return "ServiceType not found";
            }
        }
    }
}
