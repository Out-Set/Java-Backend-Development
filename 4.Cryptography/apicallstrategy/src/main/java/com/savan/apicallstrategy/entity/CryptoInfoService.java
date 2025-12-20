package com.savan.apicallstrategy.entity;

import java.util.List;

public interface CryptoInfoService {

    CryptoInfo create(CryptoInfo cryptoInfo);

    CryptoInfo findByCryptoStrategyAndClient(String cryptoStrategy, String client);

    List<CryptoInfo> readAll();

    String update(CryptoInfo cryptoInfo);

    String deleteById(Integer id);
}
