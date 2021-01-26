package br.gov.sp.fatec.presensor.repository;

import br.gov.sp.fatec.presensor.model.Presenca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, Long> {

    @Query(value = "SELECT * FROM presencas " +
                   "WHERE ra_aluno = :ra_aluno " +
                   "AND id_horario_disciplina = :id_horario_disciplina " +
                   "AND data_presenca = :data", nativeQuery = true)
    Presenca findByRaAlunoAndIdHorarioDisciplinaAndData(
            @Param("ra_aluno") Long raUsuario,
            @Param("id_horario_disciplina") Long idHorarioDisciplina,
            @Param("data") LocalDate data
    );

    @Query(value = "SELECT * FROM presencas p " +
                   "WHERE p.id IN (" +
                       "SELECT p.id FROM presencas p " +
                       "JOIN horarios_disciplinas hd ON p.id_horario_disciplina = hd.id " +
                       "JOIN salas s ON hd.uuid_beacon_sala = s.uuid_beacon " +
                       "WHERE s.numero = :numero_sala " +
                       "AND hd.sigla_disciplina = :sigla_disciplina " +
                       "AND p.data_presenca = :data" + ")", nativeQuery = true)
    List<Presenca> findBySiglaDisciplinaAndNumeroSalaAndData(
            @Param("numero_sala") Integer numeroSala,
            @Param("sigla_disciplina") String siglaDisciplina,
            @Param("data") LocalDate data
    );

}
