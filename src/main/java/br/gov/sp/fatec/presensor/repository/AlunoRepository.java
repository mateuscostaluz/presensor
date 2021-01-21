package br.gov.sp.fatec.presensor.repository;

import br.gov.sp.fatec.presensor.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Query(value = "SELECT * FROM alunos " +
                   "WHERE email = :email " +
                   "AND senha = :senha ", nativeQuery = true)
    Aluno findAlunoByEmailAndSenha(
            @Param("email") String email,
            @Param("senha") String senha
    );

}
