package com.spring.scheduler.DynamicCron.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiArgs {

    private String argType;
    private String argValueType;
    private String argKey;
    private String argValue;
}
