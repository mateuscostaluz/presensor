package br.gov.sp.fatec.presensor.repository;

import br.gov.sp.fatec.presensor.model.Disciplina;
import br.gov.sp.fatec.presensor.model.Presenca;
import br.gov.sp.fatec.presensor.model.Sala;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Repository
public class PresencaCustomRepository {

    private final DisciplinaRepository disciplinaRepository;
    private final SalaRepository salaRepository;
    private final EntityManager em;

    public List<Presenca> findBySiglaDisciplinaAndNumeroSalaAndData(Integer sala, String disciplina, LocalDate dataPresenca) {

        String query = "SELECT p FROM Presenca p";

        if(disciplina != null) {
            query += " JOIN HorarioDisciplina hd ON p.horarioDisciplina = hd.id";
        }

        if(sala != null) {
            query += " JOIN Sala s ON hd.sala = s.uuidBeacon";
        }

        String condicao = " WHERE ";

        if(disciplina != null) {
            query += condicao + "hd.disciplina = :disciplina";
            condicao = " AND ";
        }

        if(sala != null) {
            query += condicao + "s.numero = :sala";
            condicao = " AND ";
        }

        if(dataPresenca != null) {
            query += condicao + "p.dataPresenca = :dataPresenca";
        }

        TypedQuery<Presenca> q = em.createQuery(query, Presenca.class);

        if(disciplina != null) {
            Disciplina disciplinaObject = disciplinaRepository.findBySigla(disciplina);
            q.setParameter("disciplina", disciplinaObject);
        }

        if(sala != null) {
            Sala salaObject = salaRepository.findByNumero(sala);
            q.setParameter("sala", salaObject.getNumero());
        }

        if(dataPresenca != null) {
            q.setParameter("dataPresenca", dataPresenca);
        }

        return q.getResultList();
    }
}
