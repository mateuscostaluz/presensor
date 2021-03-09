package br.gov.sp.fatec.presensor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Response {

    private Object data;
    private Integer status;
    private String message;

}
