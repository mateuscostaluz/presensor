package br.gov.sp.fatec.presensor.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "presencas")
@IdClass(PresencaId.class)
public class Presenca {

    @ManyToOne
    @Id
    @JoinColumn(name = "ra_usuario")
    private Usuario usuario;

    @ManyToOne
    @Id
    @JoinColumn(name = "id_horario_disciplina")
    private HorarioDisciplina horarioDisciplina;

    @Id
    @Column(name = "data", nullable = false)
    private LocalDate data;

}
