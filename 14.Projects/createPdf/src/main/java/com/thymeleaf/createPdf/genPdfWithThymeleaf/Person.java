package com.thymeleaf.createPdf.genPdfWithThymeleaf;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Person {

    private String name;
    private String email;
    private String phoneNumber;
    private String address;
}
