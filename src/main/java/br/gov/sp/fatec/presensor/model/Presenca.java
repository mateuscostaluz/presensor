package br.gov.sp.fatec.presensor.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "presencas")
public class Presenca {

    @Column(name = "ra_usuario", length = 20, nullable = false)
    private Long raUsuario;

    @Column(name = "id_horario_disciplina", length = 20, nullable = false)
    private Long idHorarioDisciplina;

    @Column(name = "data", nullable = false)
    private LocalDate data;

}
