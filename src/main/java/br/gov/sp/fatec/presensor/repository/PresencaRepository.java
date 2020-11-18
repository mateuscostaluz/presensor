package br.gov.sp.fatec.presensor.repository;

import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import br.gov.sp.fatec.presensor.model.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, Long> {

    @Query(value = "SELECT * FROM presencas " +
            "WHERE ra_usuario = :ra_usuario " +
            "AND id_horario_disciplina = :id_horario_disciplina " +
            "AND DATE(data_hora) = :localDate", nativeQuery = true)
    Presenca findPresencaByUsuarioAndHorarioDisciplinaAndDataHora (
            @Param("raUsuario") Long raUsuario,
            @Param("idHorario") Long idHorario,
            @Param("idHorario") LocalDate localDate
    );

}
