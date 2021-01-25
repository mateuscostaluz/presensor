package br.gov.sp.fatec.presensor.repository;

import br.gov.sp.fatec.presensor.model.Aluno;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends CrudRepository<Aluno, Long> {

    Aluno findByEmail(String email);

}
