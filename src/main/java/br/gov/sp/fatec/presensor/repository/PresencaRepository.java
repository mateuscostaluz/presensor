package br.gov.sp.fatec.presensor.repository;

import br.gov.sp.fatec.presensor.model.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, Presenca> {

    @Query(value = "SELECT * FROM presencas " +
                   "WHERE ra_usuario = :ra_usuario " +
                   "AND id_horario_disciplina = :id_horario_disciplina " +
                   "AND data = :data", nativeQuery = true)
    Presenca findPresencaByRaUsuarioAndIdHorarioDisciplinaAndData(
            @Param("ra_usuario") Long raUsuario,
            @Param("id_horario_disciplina") Long idHorarioDisciplina,
            @Param("data") LocalDate data
    );

}
