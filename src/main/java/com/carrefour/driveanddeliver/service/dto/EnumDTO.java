package com.carrefour.driveanddeliver.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class EnumDTO implements Serializable {
    
    private String key;
    
    private String value;
}
