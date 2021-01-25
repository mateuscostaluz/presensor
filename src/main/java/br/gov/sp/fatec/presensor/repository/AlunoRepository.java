package br.gov.sp.fatec.presensor.repository;

import br.gov.sp.fatec.presensor.model.Aluno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends CrudRepository<Aluno, Long> {

    @Query(value = "SELECT * FROM alunos " +
                   "WHERE email = :email ", nativeQuery = true)
    Aluno findByEmail(
            @Param("email") String email
    );

}
