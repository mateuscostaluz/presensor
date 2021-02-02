package br.gov.sp.fatec.presensor.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "alunos")
public class Aluno {

    @Id
    @Column(name = "ra", length = 20, nullable = false)
    private Long ra;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "senha", length = 100, nullable = false)
    private String senha;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "role", length = 11, nullable = false)
    private String role;

    public List<Role> getRoles() {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.valueOf(role));

        return roles;
    }

}
