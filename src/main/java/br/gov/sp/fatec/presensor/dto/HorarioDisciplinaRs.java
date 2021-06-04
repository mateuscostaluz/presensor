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
    private String siglaDisciplina;
    private String nomeDisciplina;
    private String numeroSala;
    private String diaSemana;
    private String horarioInicio;
    private String horarioFim;

    public static HorarioDisciplinaRs converter(HorarioDisciplina hd) {
        HorarioDisciplinaRs horarioDisciplinaRs = new HorarioDisciplinaRs();
        horarioDisciplinaRs.setId(hd.getId());
        horarioDisciplinaRs.setSiglaDisciplina(hd.getDisciplina().getSigla());
        horarioDisciplinaRs.setNomeDisciplina(hd.getDisciplina().getNome());
        horarioDisciplinaRs.setDiaSemana(hd.getDiaSemana().getDia());
        horarioDisciplinaRs.setNumeroSala(hd.getDiaSemana().getDia());
        horarioDisciplinaRs.setHorarioInicio(hd.getHorarioInicio().toString());
        horarioDisciplinaRs.setHorarioFim(hd.getHorarioFim().toString());
        return horarioDisciplinaRs;
    }

}
