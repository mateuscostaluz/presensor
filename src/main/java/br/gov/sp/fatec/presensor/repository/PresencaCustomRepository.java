package br.gov.sp.fatec.presensor.repository;

import br.gov.sp.fatec.presensor.model.Presenca;
import lombok.AllArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Repository
public class PresencaCustomRepository {

    private final EntityManager em;

    public List<Presenca> find(String disciplina, Integer sala, LocalDate dataPresenca) {

        String query = "SELECT p FROM presencas p WHERE p.id IN (" +
                "SELECT p.id FROM presencas p " +
                "JOIN horarios_disciplinas hd ON p.id_horario_disciplina = hd.id " +
                "JOIN salas s ON hd.uuid_beacon_sala = s.uuid_beacon";

        String condicao = " WHERE ";

        if(disciplina != null) {
            query += condicao + "hd.sigla_disciplina = :disciplina";
            condicao = " AND ";
        }

        if(sala != null) {
            query += condicao + "s.numero = :sala";
            condicao = " AND ";
        }

        if(dataPresenca != null) {
            query += condicao + "p.data_presenca = :dataPresenca";
        }

        query += ")";

        var q = em.createQuery(query, Presenca.class);

        if(disciplina != null) {
            q.setParameter("disciplina", disciplina);
        }

        if(sala != null) {
            q.setParameter("sala", sala);
        }

        if(dataPresenca != null) {
            q.setParameter("data_presenca", dataPresenca);
        }

        return q.getResultList();
    }

}
