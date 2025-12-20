package com.savan.apicallstrategy.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CryptoInfoServiceImpl implements CryptoInfoService {

    @Autowired
    private CryptoInfoRepo cryptoInfoRepo;

    @Override
    public CryptoInfo create(CryptoInfo cryptoInfo) {
        return cryptoInfoRepo.save(cryptoInfo);
    }

    @Override
    public CryptoInfo findByCryptoStrategyAndClient(String cryptoStrategy, String client) {
        return cryptoInfoRepo.findByCryptoStrategyAndClient(cryptoStrategy, client);
    }

    @Override
    public List<CryptoInfo> readAll() {
        return cryptoInfoRepo.findAll();
    }

    @Override
    public String update(CryptoInfo cryptoInfo) {
        Optional<CryptoInfo> existingKeyCertificateInfo = cryptoInfoRepo.findById(cryptoInfo.getId());
        if(existingKeyCertificateInfo.isPresent()){
            cryptoInfoRepo.save(cryptoInfo);
            return "Record updated successfully";
        }
        return "Record doesn't exists";
    }

    @Override
    public String deleteById(Integer id) {
        Optional<CryptoInfo> existingKeyCertificateInfo = cryptoInfoRepo.findById(id);
        if(existingKeyCertificateInfo.isPresent()){
            cryptoInfoRepo.deleteById(id);
            return "Record deleted successfully";
        }
        return "Record doesn't exists";
    }
}
