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

    private Long raUsuario;
    private Long idHorarioDisciplina;
    private LocalDate data;

}
