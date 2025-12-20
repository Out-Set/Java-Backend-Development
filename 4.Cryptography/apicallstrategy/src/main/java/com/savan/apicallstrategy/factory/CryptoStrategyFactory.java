package com.savan.apicallstrategy.factory;

import com.savan.apicallstrategy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.savan.apicallstrategy.constants.CryptoConstants.*;

@Component
public class CryptoStrategyFactory {

    private final Map<String, CryptoStrategy> cryptoStrategyMap = new HashMap<>();

    @Autowired
    public CryptoStrategyFactory(
            SymmetricCrypto symmetricCrypto,
            AsymmetricCrypto asymmetricCrypto,
            HybridCrypto hybridCrypto,
            P12ApiClientService p12ApiClientService
            // Add more Strategy
    ) {
        cryptoStrategyMap.put(SYMMETRIC_CRYPTO, symmetricCrypto);
        cryptoStrategyMap.put(ASYMMETRIC_CRYPTO, asymmetricCrypto);
        cryptoStrategyMap.put(HYBRID_CRYPTO, hybridCrypto);
        cryptoStrategyMap.put(ASYMMETRIC_CRYPTO_P12, p12ApiClientService);
        // Add more Strategy
    }

    public CryptoStrategy getCryptoStrategy(String cryptoStrategyName) {
        CryptoStrategy strategy = cryptoStrategyMap.get(cryptoStrategyName);
        if (strategy == null)
            throw new IllegalArgumentException("Unsupported method type: " + cryptoStrategyName);
        return strategy;
    }
}
