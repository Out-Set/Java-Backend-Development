package com.savan.quartz.genericparam;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericParameterDto {

    private Long id;
    private String code;
    private String name;
    @JsonProperty("dType")
    private String dType;
    private String parentCode;
    private String description;
    private Boolean defaultFlag;
    private String createdBy;
}