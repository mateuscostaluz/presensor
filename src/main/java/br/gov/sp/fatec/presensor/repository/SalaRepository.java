package br.gov.sp.fatec.presensor.repository;

import br.gov.sp.fatec.presensor.model.Aluno;
import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import br.gov.sp.fatec.presensor.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;

@Repository
public interface SalaRepository extends JpaRepository<Aluno, Long> {

    @Query(value = "SELECT * FROM salas " +
            "WHERE numero = :numero", nativeQuery = true)
    Sala findSalaByNumeroNamedParams(
            @Param("numero") Integer numero
    );

}
