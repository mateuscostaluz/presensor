package br.gov.sp.fatec.presensor.repository;

import br.gov.sp.fatec.presensor.model.Disciplina;
import br.gov.sp.fatec.presensor.model.Presenca;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@Repository
public class PresencaCustomRepository {

    private final DisciplinaRepository disciplinaRepository;
    private final EntityManager em;

    public List<Presenca> find(String disciplina, Integer sala, Date dataPresenca) {

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
            Disciplina disciplinaObject = disciplinaRepository.findBySigla(disciplina);
            q.setParameter("disciplina", disciplinaObject);
        }

        if(sala != null) {
            q.setParameter("sala", sala);
        }

        if(dataPresenca != null) {
            q.setParameter("dataPresenca", dataPresenca);
        }

        System.out.println(q.unwrap(org.hibernate.Query.class).getQueryString());
        System.out.println("Disciplina " + q.getParameterValue("disciplina"));
        System.out.println("Sala " + q.getParameterValue("sala"));
        System.out.println("Data " + q.getParameterValue("dataPresenca"));

        return q.getResultList();
    }

}
