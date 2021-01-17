package br.gov.sp.fatec.presensor.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "horarios_disciplinas")
public class HorarioDisciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sigla_disciplina")
    private Disciplina disciplina;

    @ManyToOne
    @JoinColumn(name = "uuid_beacon_sala")
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "id_dia_semana")
    private DiaSemana diaSemana;

    @Column(name = "horario_inicio", nullable = false)
    private Time horarioInicio;

    @Column(name = "horario_fim", nullable = false)
    private Time horarioFim;

}
