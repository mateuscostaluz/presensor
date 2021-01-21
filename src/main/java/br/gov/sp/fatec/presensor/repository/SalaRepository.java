package br.gov.sp.fatec.presensor.repository;

import br.gov.sp.fatec.presensor.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaRepository extends JpaRepository<Sala, String> {

    @Query(value = "SELECT * FROM salas WHERE numero = :sala", nativeQuery = true)
    Sala findSalaByNumeroNamedParam(
            @Param("sala") Integer sala
    );

}
