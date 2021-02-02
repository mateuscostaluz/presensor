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
public class HorarioDisciplinaRs {

    private Long id;
    private Disciplina disciplina;
    private Sala sala;
    private DiaSemana diaSemana;
    private Time horarioInicio;
    private Time horarioFim;

    public static HorarioDisciplinaRs converter(HorarioDisciplina hd) {
        HorarioDisciplinaRs horarioDisciplinaRs = new HorarioDisciplinaRs();
        horarioDisciplinaRs.setId(hd.getId());
        horarioDisciplinaRs.setDisciplina(hd.getDisciplina());
        horarioDisciplinaRs.setSala(hd.getSala());
        horarioDisciplinaRs.setDiaSemana(hd.getDiaSemana());
        horarioDisciplinaRs.setHorarioInicio(hd.getHorarioInicio());
        horarioDisciplinaRs.setHorarioFim(hd.getHorarioFim());
        return horarioDisciplinaRs;
    }

}
