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

    public List<Presenca> find(String disciplina, Integer sala, LocalDate data) {

        String query = "SELECT ps FROM public.presencas ps WHERE ps.id IN (" +
                           "SELECT ps.id FROM public.presencas p " +
                           "JOIN public.horarios_disciplinas hd ON p.id_horario_disciplina = hd.id " +
                           "JOIN public.salas s ON hd.uuid_beacon_sala = s.uuid_beacon";

        String condicao = " WHERE ";

        if(disciplina != null) {
            query += condicao + "hd.sigla_disciplina = :disciplina";
            condicao = " AND ";
        }

        if(sala != null) {
            query += condicao + "s.numero = :sala";
            condicao = " AND ";
        }

        if(data != null) {
            query += condicao + "p.data_presenca = :data_presenca";
        }

        query += ")";

        System.out.println("QUERY ORIGINAL AQUI: ");
        System.out.println(query);
        System.out.println();

        TypedQuery<Presenca> q = em.createQuery(query, Presenca.class);
        
        System.out.println("TYPED QUERY AQUI: ");
        System.out.println(q.toString());
        System.out.println();

        if(disciplina != null) {
            q.setParameter("disciplina", disciplina);
        }

        if(sala != null) {
            q.setParameter("sala", sala);
        }

        if(data != null) {
            q.setParameter("data_presenca", data);
        }

        System.out.println("TYPED QUERY AQUI DEPOIS DE PARAMETRIZADA: ");
        System.out.println(q.toString());
        System.out.println(q.getResultList().toString());

        return q.getResultList();
    }

}
