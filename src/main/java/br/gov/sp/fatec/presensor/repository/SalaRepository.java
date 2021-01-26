package br.gov.sp.fatec.presensor.repository;

import br.gov.sp.fatec.presensor.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaRepository extends JpaRepository<Sala, String> {

    Sala findByNumero(Integer numero);

}
