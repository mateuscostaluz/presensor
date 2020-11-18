package br.gov.sp.fatec.presensor.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "presencas")
public class Presenca {

    @ManyToOne
    @JoinColumn(name = "ra_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_horario_disciplina")
    private HorarioDisciplina horarioDisciplina;

    @Column(name = "data", nullable = false)
    private LocalDate data;

}
