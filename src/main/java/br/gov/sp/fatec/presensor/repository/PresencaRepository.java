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
    Presenca findPresencaByRaAlunoAndIdHorarioDisciplinaAndData(
            @Param("ra_aluno") Long raUsuario,
            @Param("id_horario_disciplina") Long idHorarioDisciplina,
            @Param("data") LocalDate data
    );

}
