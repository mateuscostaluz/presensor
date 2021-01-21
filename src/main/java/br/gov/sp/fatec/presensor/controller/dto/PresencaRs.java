package br.gov.sp.fatec.presensor.controller.dto;

import br.gov.sp.fatec.presensor.model.Presenca;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PresencaRs {

    private Long id_presenca;

    private Long ra_aluno;
    private String nome_aluno;

    private String sigla_disciplina;
    private String nome_disciplina;

    private String uuid_beacon_sala;
    private Integer numero_sala;

    private String dia_semana;

    private LocalDate data_presenca;

    public static PresencaRs converter(Presenca p) {

        PresencaRs presencaRs = new PresencaRs();

        presencaRs.setId_presenca(p.getId());

        presencaRs.setRa_aluno(p.getAluno().getRa());
        presencaRs.setNome_aluno(p.getAluno().getNome());

        presencaRs.setSigla_disciplina(p.getHorarioDisciplina().getDisciplina().getSigla());
        presencaRs.setNome_disciplina(p.getHorarioDisciplina().getDisciplina().getNome());

        presencaRs.setUuid_beacon_sala(p.getHorarioDisciplina().getSala().getUuidBeacon());
        presencaRs.setNumero_sala(p.getHorarioDisciplina().getSala().getNumero());

        presencaRs.setDia_semana(p.getHorarioDisciplina().getDiaSemana().getDia());

        presencaRs.setData_presenca(p.getData());

        return presencaRs;
    }

}
