package br.gov.sp.fatec.presensor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlunoRq {

    private Long ra;
    private String email;
    private String senha;
    private String nome;

}
