package com.savan.apicallstrategy.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CryptoRequest {

    private String data;
    private String client;
    private String aesKey;
    private String p12Path;
    private String p12Password;
    private String publicCertPath;
    private String privateKeyPath;
    private String cryptoStrategy;
    private String serviceType;
}

