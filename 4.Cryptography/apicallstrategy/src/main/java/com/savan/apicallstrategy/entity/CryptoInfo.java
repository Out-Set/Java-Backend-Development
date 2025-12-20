package com.savan.apicallstrategy.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cryptography_info")
public class CryptoInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String client;
    private String aesKey;
    private String p12Path;
    private String p12Password;
    private String publicCertPath;
    private String privateKeyPath;

    private Boolean encryptionOpted;
    private Boolean decryptionOpted;
    private String cryptoStrategy;
}
