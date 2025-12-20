package com.savan.apicallstrategy.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class CibilCryptoService {

    @Autowired
    private CibilCryptoUtils cibilCryptoUtils;

    @Autowired
    private ObjectMapper objectMapper;

    public String encryptPayload(String payload, String publicCertPath) {
        Map<String, Object> finalPayload = new HashMap<>();
        try {
            // Load PublicKey
            PublicKey publicKey = cibilCryptoUtils.loadPublicKey(publicCertPath);

            // 1. key: Generate 32-byte random number.
            byte[] key = cibilCryptoUtils.generateRandomBytes(32);

            // 2. iv: Generate 16-byte random number.
            byte[] iv = cibilCryptoUtils.generateRandomBytes(16);

            // 3. encrypted_data: Encrypt the data using key and using
            // Supported Symmetric Key Encryption Algorithm and iv – initialization vector.
            String encryptedData = cibilCryptoUtils.aesEncryptPayload(payload, key, iv);

            // 4. IV + encrypted_data:  After encryption, prepend IV to the ciphertext for transmission.
            byte[] encryptedDataBytes = Base64.getDecoder().decode(encryptedData);
            byte[] dataIv = new byte[iv.length + encryptedDataBytes.length];
            System.arraycopy(iv, 0, dataIv, 0, iv.length);
            System.arraycopy(encryptedDataBytes, 0, dataIv, iv.length, encryptedDataBytes.length);

            // Encode into Base64 the combined IV + ciphertext
            String encryptedDataIv = Base64.getEncoder().encodeToString(dataIv); //  Base64 encoded

            // 5. encrypted_key:  Encrypt the key using Supported Asymmetric Key Encryption Algorithm
            // and using TransUnion CIBIL’s Public certificate.
            String encryptedKey = cibilCryptoUtils.rsaEncryptKey(key, publicKey); // Base64 encoded

            // Prepare final payload
            finalPayload.put("EncryptedKey", encryptedKey);
            finalPayload.put("EncryptedData", encryptedDataIv);

            return objectMapper.writeValueAsString(finalPayload);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


    public Object decryptResponse(String response, String privateKeyPath) {

        String resp = """
                {
                    "EncryptedKey": "nQBV7+HwARDPUisTLWKq7C0qIQ0AB32UwC+jsYPKa5NoL3FXGHjPwk0LScjo1KErnjkemooXZCFq08MpGT8YJmAtbNbf07bbOBX331lRmjtbRWu3fr8I52hWkTh2kxk0CzlOJhK9trRgj+MB+07kmK5zQFSrA1y5Zslk3JxECD2Nr3tL+yYWTXgDAlFHp6gpd9FpKeWDzP/iypFsee91koqbxJ9eui7tBZlSB78V/ZUmlQNbQ/mDQZPyXTX0p7Y7GzS71UHOZLHLVstVgajw0HXbUHxqzEZ8wCrwpNMJZtAAdK9IUrr7he5BkwBkRe5326ERyHsBZYZijZhhDFQjSQ==",
                    "EncryptedData": "Ev3guL1j9foNENe8duk/60vD+fNneKHDCa3gNF4YBNiDVvr765s9KLUyTKH9F7IT5+e/ZLoVNnpJIaoj4zNtzSZTlIxJlKQ2qkLuUaaUg57n0YSK5SKpPB6blsFeOnrcNhE6+tGyKexuK8fP/l0/AY4D4k9wqjsPsU03biMNIr2crt1FLU1zo9c39FeHvyZH"
                }
                """;
        try {
            // Convert response into map so that we can read
            Map<String, String> responseMap = objectMapper.readValue(resp, new TypeReference<>() {});

            // Load PrivateKey
            PrivateKey privateKey = cibilCryptoUtils.loadPrivateKey(privateKeyPath);

            // 1. decrypted_key: Fetch the Encrypted Key from response and decrypt it
            // using private key with Supported Asymmetric-Key-Encryption-Algorithm.
            byte[] decryptedAesKey = cibilCryptoUtils.rsaDecryptKey(responseMap.get("EncryptedKey"), privateKey);

            // 2. iv: Get the IV (starting 16 bytes of EncryptedData).
            byte[] encryptedData = Base64.getDecoder().decode(responseMap.get("EncryptedData"));

            byte[] iv = new byte[16];
            System.arraycopy(encryptedData, 0, iv, 0, 16);

            byte[] ciphertext = new byte[encryptedData.length - 16];
            System.arraycopy(encryptedData, 16, ciphertext, 0, ciphertext.length);

            // 3. decrypted_data: Fetch the Encrypted Data from response and decrypt it
            // using decrypted key, IV and using Supported Symmetric-Key-Encryption-Algorithm.
            // 4. decrypted_response: Decode from base64 the decrypted data and will get the response.
            return cibilCryptoUtils.aesDecryptPayload(decryptedAesKey, iv, ciphertext);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
