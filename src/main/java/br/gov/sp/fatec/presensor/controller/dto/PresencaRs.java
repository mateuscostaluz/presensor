package br.gov.sp.fatec.presensor.controller.dto;

import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import br.gov.sp.fatec.presensor.model.Presenca;
import br.gov.sp.fatec.presensor.model.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PresencaRs {

    private Long raUsuario;
    private Long idHorarioDisciplina;
    private LocalDate data;

    public static PresencaRs converter(Presenca p) {
        PresencaRs presencaRs = new PresencaRs();
        presencaRs.setRaUsuario(p.getRaUsuario());
        presencaRs.setIdHorarioDisciplina(p.getIdHorarioDisciplina());
        presencaRs.setData(p.getData());
        return presencaRs;
    }

}
