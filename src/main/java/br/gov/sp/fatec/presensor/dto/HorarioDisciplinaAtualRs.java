package br.gov.sp.fatec.presensor.dto;

import br.gov.sp.fatec.presensor.model.DiaSemana;
import br.gov.sp.fatec.presensor.model.Disciplina;
import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import br.gov.sp.fatec.presensor.model.Sala;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
public class HorarioDisciplinaAtualRs {

    private Long id;
    private Disciplina disciplina;
    private Sala sala;
    private DiaSemana diaSemana;
    private Time horarioInicio;
    private Time horarioFim;

    public static HorarioDisciplinaAtualRs converter(HorarioDisciplina hd) {
        HorarioDisciplinaAtualRs horarioDisciplinaAtualRs = new HorarioDisciplinaAtualRs();
        horarioDisciplinaAtualRs.setId(hd.getId());
        horarioDisciplinaAtualRs.setDisciplina(hd.getDisciplina());
        horarioDisciplinaAtualRs.setSala(hd.getSala());
        horarioDisciplinaAtualRs.setDiaSemana(hd.getDiaSemana());
        horarioDisciplinaAtualRs.setHorarioInicio(hd.getHorarioInicio());
        horarioDisciplinaAtualRs.setHorarioFim(hd.getHorarioFim());
        return horarioDisciplinaAtualRs;
    }

}
