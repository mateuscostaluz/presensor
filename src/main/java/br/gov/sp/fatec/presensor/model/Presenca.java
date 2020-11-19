package br.gov.sp.fatec.presensor.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@IdClass(PresencaId.class)
@Table(name = "presencas")
public class Presenca {

    @Id
    @Column(name = "ra_usuario", length = 20, nullable = false)
    private Long raUsuario;

    @Id
    @Column(name = "id_horario_disciplina", length = 20, nullable = false)
    private Long idHorarioDisciplina;

    @Id
    @Column(name = "data", nullable = false)
    private LocalDate data;

}
