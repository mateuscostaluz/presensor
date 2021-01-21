package br.gov.sp.fatec.presensor.repository;

import br.gov.sp.fatec.presensor.model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, String> {

    @Query(value = "SELECT * FROM disciplinas WHERE sigla = :sigla", nativeQuery = true)
    Disciplina findDisciplinaByBySiglaNamedParam(
            @Param("sigla") String sigla
    );

}
