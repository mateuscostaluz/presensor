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

    @JoinColumn(name = "ra_usuario", unique = true, nullable = false)
    private Usuario usuario;

    @JoinColumn(name = "id_horario_disciplina", unique = true, nullable = false)
    private HorarioDisciplina horarioDisciplina;

    @Column(name = "data", unique = true, nullable = false)
    private LocalDate data;

}
