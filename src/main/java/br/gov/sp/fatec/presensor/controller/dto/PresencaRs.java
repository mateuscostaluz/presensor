package br.gov.sp.fatec.presensor.controller.dto;

import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import br.gov.sp.fatec.presensor.model.Presenca;
import br.gov.sp.fatec.presensor.model.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@Setter
public class PresencaRs {

    private Long id;
    private Usuario usuario;
    private HorarioDisciplina horarioDisciplina;
    private LocalDate data;

    public static PresencaRs converter(Presenca p) {
        PresencaRs presencaRs = new PresencaRs();
        presencaRs.setUsuario(p.getUsuario());
        presencaRs.setHorarioDisciplina(p.getHorarioDisciplina());
        presencaRs.setData(p.getData());
        return presencaRs;
    }

}
