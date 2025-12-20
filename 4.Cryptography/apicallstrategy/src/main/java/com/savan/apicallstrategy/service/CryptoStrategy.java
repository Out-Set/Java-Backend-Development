package com.savan.apicallstrategy.service;

import com.savan.apicallstrategy.model.CryptoRequest;

public interface CryptoStrategy {
    Object process(CryptoRequest cryptoRequest) throws Exception;
}
