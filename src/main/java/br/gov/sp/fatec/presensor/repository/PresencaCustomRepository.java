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

    public List<Presenca> find(String disciplina, Integer sala, LocalDate dataPresenca) {

        String query = "SELECT ps FROM Presenca ps WHERE ps.id IN (" +
                           "SELECT ps.id FROM Presenca p " +
                           "JOIN HorarioDisciplina hd ON p.horarioDisciplina = hd.id " +
                           "JOIN Sala s ON hd.sala = s.uuidBeacon";

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

        query += ")";

        TypedQuery<Presenca> q = em.createQuery(query, Presenca.class);

        if(disciplina != null) {
            Disciplina disciplinaObject = disciplinaRepository.findDisciplinaByBySiglaNamedParam(disciplina);
            q.setParameter("disciplina", disciplinaObject);
        }

        if(sala != null) {
            Sala salaObject = salaRepository.findSalaByNumeroNamedParam(sala);
            q.setParameter("sala", salaObject);
        }

        if(dataPresenca != null) {
            q.setParameter("dataPresenca", dataPresenca);
        }

        return q.getResultList();
    }

}
