package com.savan.apicallstrategy.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoInfoRepo extends JpaRepository<CryptoInfo, Integer> {

    CryptoInfo findByCryptoStrategyAndClient(String cryptoStrategy, String client);
}
