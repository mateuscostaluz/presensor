package br.gov.sp.fatec.presensor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class PresencaId implements Serializable {

    private Usuario usuario;
    private HorarioDisciplina horarioDisciplina;
    private LocalDate data;

}
