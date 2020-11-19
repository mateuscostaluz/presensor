package br.gov.sp.fatec.presensor.repository;

import br.gov.sp.fatec.presensor.model.Presenca;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PresencaRepository extends CrudRepository<Presenca, PresencaId> {
}
