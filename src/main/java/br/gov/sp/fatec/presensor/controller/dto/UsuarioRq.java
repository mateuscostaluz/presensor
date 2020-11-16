package br.gov.sp.fatec.presensor.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRq {

    private Long ra;
    private String email;
    private String senha;

}
