package br.gov.sp.fatec.presensor.repository;

import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioDisciplinaRepository extends JpaRepository<HorarioDisciplina, Long> {
}
