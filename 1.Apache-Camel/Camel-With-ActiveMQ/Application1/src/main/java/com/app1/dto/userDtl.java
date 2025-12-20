package com.app1.dto;

import lombok.*;
import org.springframework.core.env.ConfigurableEnvironment;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class userDtl {

    private Long id;

    private String name;

    private String add;

}
