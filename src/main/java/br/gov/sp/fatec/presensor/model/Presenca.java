package br.gov.sp.fatec.presensor.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "presencas")
public class Presenca implements Serializable {

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
