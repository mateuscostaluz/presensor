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
@Table(name = "disciplinas")
public class Disciplina {

    @Id
    @Column(name = "sigla", length = 6, nullable = false)
    private String sigla;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

}
