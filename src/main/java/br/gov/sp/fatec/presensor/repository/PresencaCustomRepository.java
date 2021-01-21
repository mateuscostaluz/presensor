package br.gov.sp.fatec.presensor.repository;

import br.gov.sp.fatec.presensor.model.Presenca;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Repository
public class PresencaCustomRepository {

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
            query += condicao + "s.sala = :sala";
            condicao = " AND ";
        }

        if(dataPresenca != null) {
            query += condicao + "p.dataPresenca = :dataPresenca";
        }

        query += ")";

        TypedQuery<Presenca> q = em.createQuery(query, Presenca.class);

        if(disciplina != null) {
            q.setParameter("disciplina", disciplina);
        }

        if(sala != null) {
            q.setParameter("sala", sala);
        }

        if(dataPresenca != null) {
            q.setParameter("dataPresenca", dataPresenca);
        }

        return q.getResultList();
    }

}
