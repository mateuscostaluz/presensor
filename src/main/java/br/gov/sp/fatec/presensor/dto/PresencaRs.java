package br.gov.sp.fatec.presensor.dto;

import br.gov.sp.fatec.presensor.model.Presenca;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PresencaRs {

    @JsonProperty(value = "id_presenca")
    private Long idPresenca;

    @JsonProperty(value = "ra_aluno")
    private Long raAluno;
    @JsonProperty(value = "nome_aluno")
    private String nomeAluno;

    @JsonProperty(value = "sigla_disciplina")
    private String siglaDisciplina;
    @JsonProperty(value = "nome_disciplina")
    private String nomeDisciplina;

    @JsonProperty(value = "numero_sala")
    private Integer numeroSala;

    @JsonProperty(value = "dia_semana")
    private String diaSemana;

    @JsonProperty(value = "data_presenca")
    private String dataPresenca;

    public static PresencaRs converter(Presenca p) {

        PresencaRs presencaRs = new PresencaRs();

        presencaRs.setIdPresenca(p.getId());

        presencaRs.setRaAluno(p.getAluno().getRa());
        presencaRs.setNomeAluno(p.getAluno().getNome());

        presencaRs.setSiglaDisciplina(p.getHorarioDisciplina().getDisciplina().getSigla());
        presencaRs.setNomeDisciplina(p.getHorarioDisciplina().getDisciplina().getNome());

        presencaRs.setNumeroSala(p.getHorarioDisciplina().getSala().getNumero());

        presencaRs.setDiaSemana(p.getHorarioDisciplina().getDiaSemana().getDia());

        presencaRs.setDataPresenca(p.getDataPresenca().toString());

        return presencaRs;
    }

}
