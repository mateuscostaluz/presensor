package br.gov.sp.fatec.presensor.dto;

import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HorarioDisciplinaRs {

    private String siglaDisciplina;
    private String nomeDisciplina;
    private Integer numeroSala;
    private String diaSemana;
    private String horarioInicio;
    private String horarioFim;

    public static HorarioDisciplinaRs converter(HorarioDisciplina hd) {
        HorarioDisciplinaRs horarioDisciplinaRs = new HorarioDisciplinaRs();
        horarioDisciplinaRs.setSiglaDisciplina(hd.getDisciplina().getSigla());
        horarioDisciplinaRs.setNomeDisciplina(hd.getDisciplina().getNome());
        horarioDisciplinaRs.setDiaSemana(hd.getDiaSemana().getDia());
        horarioDisciplinaRs.setNumeroSala(hd.getSala().getNumero());
        horarioDisciplinaRs.setHorarioInicio(hd.getHorarioInicio().toString());
        horarioDisciplinaRs.setHorarioFim(hd.getHorarioFim().toString());
        return horarioDisciplinaRs;
    }

}
