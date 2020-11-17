package br.gov.sp.fatec.presensor.repository;

import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioDisciplinaRepository extends JpaRepository<HorarioDisciplina, Long> {

    @Query(value = "SELECT * FROM horario_disciplinas " +
                   "WHERE id_dia_semana = :diaSemana " +
                   "AND horario_inicio <= :horario " +
                   "AND horario_fim >= :horario", nativeQuery = true)
    HorarioDisciplina findHorarioDisciplinaByDiaSemanaAndHorarioNamedParams(
            @Param("diaSemana") Integer diaSemana,
            @Param("horario") String horario
    );

}
