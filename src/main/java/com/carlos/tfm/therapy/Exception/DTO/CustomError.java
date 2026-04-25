package com.carlos.tfm.therapy.Exception.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomError {

    private String timestamp;
    private Integer httpCode;
    private String message;
}