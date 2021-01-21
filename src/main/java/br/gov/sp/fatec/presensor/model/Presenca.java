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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ra_aluno")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "id_horario_disciplina", unique = true, nullable = false)
    private HorarioDisciplina horarioDisciplina;

    @Column(name = "data_presenca", unique = true, nullable = false)
    private LocalDate dataPresenca;

}
