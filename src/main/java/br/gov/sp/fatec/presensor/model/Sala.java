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
@Table(name = "salas")
public class Sala {

    @Id
    @Column(name = "uuid_beacon", length = 36, nullable = false)
    private String uuidBeacon;

    @Column(name = "numero", length = 11, nullable = false)
    private Integer numero;

}
