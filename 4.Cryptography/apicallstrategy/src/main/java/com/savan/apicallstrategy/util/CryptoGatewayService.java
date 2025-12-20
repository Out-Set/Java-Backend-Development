package com.savan.apicallstrategy.util;

import com.savan.apicallstrategy.factory.CryptoStrategyFactory;
import com.savan.apicallstrategy.model.CryptoRequest;
import com.savan.apicallstrategy.service.CryptoStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CryptoGatewayService {

    @Autowired
    private CryptoStrategyFactory cryptoStrategyFactory;

    public Object process(CryptoRequest cryptoRequest) throws Exception {
        CryptoStrategy cryptoStrategy = cryptoStrategyFactory.getCryptoStrategy(cryptoRequest.getCryptoStrategy());
        return cryptoStrategy.process(cryptoRequest);
    }
}
