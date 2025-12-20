package com.savan.apicallstrategy.service;

import com.savan.apicallstrategy.model.CryptoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

import static com.savan.apicallstrategy.constants.CryptoConstants.*;

@Slf4j
@Component
public class AsymmetricCrypto implements CryptoStrategy {

    @Override
    public String process(CryptoRequest cryptoRequest) throws Exception {
        String publicCertPath = cryptoRequest.getPublicCertPath();
        String privateKeyPath = cryptoRequest.getPrivateKeyPath();
        String data = cryptoRequest.getData();

        switch (cryptoRequest.getServiceType()) {
            case ASYMMETRIC_ENCRYPTION_SERVICE -> {
                log.info("Inside ASYMMETRIC_ENCRYPTION_SERVICE");
                FileInputStream fis = new FileInputStream(publicCertPath);
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                X509Certificate cert = (X509Certificate) cf.generateCertificate(fis);

                PublicKey publicKey = cert.getPublicKey();

                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                byte[] encrypted = cipher.doFinal(data.getBytes());

                return Base64.getEncoder().encodeToString(encrypted);
            }
            case ASYMMETRIC_DECRYPTION_SERVICE -> {
                log.info("Inside ASYMMETRIC_DECRYPTION_SERVICE");
                // Decryption through .p12 file
                String p12Path = cryptoRequest.getP12Path();             // path to .p12 file
                String password = cryptoRequest.getP12Password();        // p12 password
                String encryptedData = cryptoRequest.getData();          // Base64 encrypted string
                if(p12Path != null) {
                    log.info("Performing decryption through PKCS12 file");
                    KeyStore keyStore = KeyStore.getInstance("PKCS12");
                    keyStore.load(new FileInputStream(p12Path), password.toCharArray());

                    // Assuming first alias
                    String alias = keyStore.aliases().nextElement();
                    PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());
                    System.out.println("privateKey: " + privateKey.toString());

                    Cipher cipher = Cipher.getInstance("RSA");
                    cipher.init(Cipher.DECRYPT_MODE, privateKey);

                    byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
                    return new String(decryptedBytes);
                }
                // Decryption through .pem file
                log.info("Performing decryption through .pem file");
                return "Performing decryption through .pem file";
            }
            default -> {
                return "ServiceType not found";
            }
        }
    }
}
