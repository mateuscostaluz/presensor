package br.gov.sp.fatec.presensor.controller.dto;

import br.gov.sp.fatec.presensor.model.Aluno;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlunoRs {

    private Long ra;
    private String email;
    private String nome;

    public static AlunoRs converter(Aluno u) {
        AlunoRs alunoRs = new AlunoRs();
        alunoRs.setRa(u.getRa());
        alunoRs.setEmail(u.getEmail());
        alunoRs.setNome(u.getNome());
        return alunoRs;
    }

}
