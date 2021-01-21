package br.gov.sp.fatec.presensor.repository;

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
                   "AND data = :data", nativeQuery = true)
    Presenca findPresencaByRaUsuarioAndIdHorarioDisciplinaAndData(
            @Param("ra_usuario") Long raUsuario,
            @Param("id_horario_disciplina") Long idHorarioDisciplina,
            @Param("data") LocalDate data
    );

    @Query(value = "SELECT * FROM presencas p " +
                   "WHERE p.id in (" +
                       "SELECT p.id from presencas p " +
                       "JOIN horarios_disciplinas hd ON p.id_horario_disciplina = hd.id " +
                       "JOIN salas s on hd.uuid_beacon_sala = s.uuid_beacon " +
                       "WHERE s.numero = :sala " +
                       "AND hd.sigla_disciplina = :disciplina " +
                       "AND p.data = :data)", nativeQuery = true)
    Presenca findPresencaByDisciplinaAndSalaAndData(
            @Param("disciplina") String disciplina,
            @Param("sala") Integer sala,
            @Param("data") LocalDate data
    );

}
