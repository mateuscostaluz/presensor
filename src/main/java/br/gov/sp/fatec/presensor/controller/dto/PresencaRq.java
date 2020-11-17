package br.gov.sp.fatec.presensor.controller.dto;

import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import br.gov.sp.fatec.presensor.model.Usuario;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
public class PresencaRq {

    private Usuario usuario;
    private HorarioDisciplina horarioDisciplina;
    private Timestamp dataHora;

}
