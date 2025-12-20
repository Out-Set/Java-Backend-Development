package com.savan.apicallstrategy.controller;

import com.savan.apicallstrategy.entity.CryptoInfo;
import com.savan.apicallstrategy.entity.CryptoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/Key-certificate-info")
public class CryptoInfoController {

    @Autowired
    private CryptoInfoService cryptoInfoService;

    @PostMapping
    public CryptoInfo create(@RequestBody CryptoInfo cryptoInfo) {
        return cryptoInfoService.create(cryptoInfo);
    }

    @GetMapping("/strategy-name/{cryptoStrategy}/client/{client}")
    public CryptoInfo findByCryptoStrategyAndClient(@PathVariable String cryptoStrategy, @PathVariable String client) {
        return cryptoInfoService.findByCryptoStrategyAndClient(cryptoStrategy, client);
    }

    @GetMapping
    public List<CryptoInfo> readAll() {
        return cryptoInfoService.readAll();
    }

    @PutMapping
    public String update(@RequestBody CryptoInfo cryptoInfo) {
        return cryptoInfoService.update(cryptoInfo);
    }

    @DeleteMapping("/id/{id}")
    public String deleteById(@PathVariable Integer id) {
        return cryptoInfoService.deleteById(id);
    }
}
