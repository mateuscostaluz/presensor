package br.gov.sp.fatec.presensor.controller.dto;

import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import br.gov.sp.fatec.presensor.model.Presenca;
import br.gov.sp.fatec.presensor.model.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PresencaRs {

    private Long id;
    private Usuario usuario;
    private HorarioDisciplina horarioDisciplina;
    private Timestamp dataHora;

    public static PresencaRs converter(Presenca p) {
        PresencaRs presencaRs = new PresencaRs();
        presencaRs.setId(p.getId());
        presencaRs.setUsuario(p.getUsuario());
        presencaRs.setHorarioDisciplina(p.getHorarioDisciplina());
        presencaRs.setDataHora(p.getDataHora());
        return presencaRs;
    }

}
