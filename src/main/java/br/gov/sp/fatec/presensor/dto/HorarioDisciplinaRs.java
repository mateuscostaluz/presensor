package br.gov.sp.fatec.presensor.dto;

import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HorarioDisciplinaRs {

    @JsonProperty(value = "id_horario_disciplina")
    private Long id;
    @JsonProperty(value = "sigla_disciplina")
    private String siglaDisciplina;
    @JsonProperty(value = "nome_disciplina")
    private String nomeDisciplina;
    @JsonProperty(value = "numero_sala")
    private Integer numeroSala;
    @JsonProperty(value = "dia_semana")
    private String diaSemana;
    @JsonProperty(value = "horario_inicio_disciplina")
    private String horarioInicio;
    @JsonProperty(value = "horario_fim_disciplina")
    private String horarioFim;

    public static HorarioDisciplinaRs converter(HorarioDisciplina hd) {
        HorarioDisciplinaRs horarioDisciplinaRs = new HorarioDisciplinaRs();
        horarioDisciplinaRs.setId(hd.getId());
        horarioDisciplinaRs.setSiglaDisciplina(hd.getDisciplina().getSigla());
        horarioDisciplinaRs.setNomeDisciplina(hd.getDisciplina().getNome());
        horarioDisciplinaRs.setDiaSemana(hd.getDiaSemana().getDia());
        horarioDisciplinaRs.setNumeroSala(hd.getSala().getNumero());
        horarioDisciplinaRs.setHorarioInicio(hd.getHorarioInicio().toString());
        horarioDisciplinaRs.setHorarioFim(hd.getHorarioFim().toString());
        return horarioDisciplinaRs;
    }

}
