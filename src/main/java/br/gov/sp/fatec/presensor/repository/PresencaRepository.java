package br.gov.sp.fatec.presensor.repository;

import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import br.gov.sp.fatec.presensor.model.Presenca;
import br.gov.sp.fatec.presensor.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, Long> {

    @Query(value = "SELECT * FROM presencas " +
            "WHERE ra_usuario = :usuario " +
            "AND id_horario_disciplina = :horarioDisciplina " +
            "AND DATE(data_hora) = :data", nativeQuery = true)
    Presenca findPresencaByUsuarioAndHorarioDisciplinaAndDataHora (
            @Param("usuario") Usuario usuario,
            @Param("horarioDisciplina") HorarioDisciplina horarioDisciplina,
            @Param("data") LocalDate data
    );

}
