package br.gov.sp.fatec.presensor.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "dias_semana")
public class DiaSemana {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "dia", length = 13, nullable = false)
    private String dia;

}
