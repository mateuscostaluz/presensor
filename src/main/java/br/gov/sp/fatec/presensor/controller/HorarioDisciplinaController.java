package br.gov.sp.fatec.presensor.controller;


import br.gov.sp.fatec.presensor.controller.dto.HorarioDisciplinaRs;
import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import br.gov.sp.fatec.presensor.repository.HorarioDisciplinaRepository;
import br.gov.sp.fatec.presensor.services.DateTimeServices;
import lombok.AllArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/horario")
public class HorarioDisciplinaController {

    private final HorarioDisciplinaRepository horarioDisciplinaRepository;

    @GetMapping("/")
    public ResponseEntity<Object> findAll() {
        List<HorarioDisciplina> horarioDisciplinasList = horarioDisciplinaRepository.findAll();

        List<JSONObject> horarioDisciplinas = new ArrayList<JSONObject>();
        for (HorarioDisciplina hd : horarioDisciplinasList) {
            JSONObject horarioDisciplina = new JSONObject();
            horarioDisciplina.put("id", hd.getId());
            horarioDisciplina.put("nome_disciplina", hd.getDisciplina().getNome());
            horarioDisciplina.put("numero_sala", hd.getSala().getNumero());
            horarioDisciplina.put("dia_semana", hd.getDiaSemana().getDia());
            horarioDisciplina.put("inicio", hd.getHorarioInicio());
            horarioDisciplina.put("fim", hd.getHorarioFim());
            horarioDisciplinas.add(horarioDisciplina);
        }

        if(horarioDisciplinas != null) {
            return new ResponseEntity<>(horarioDisciplinas, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/atual/{beacon}")
    public ResponseEntity<HorarioDisciplinaRs> findHorarioDisciplinaByDiaSemanaAndHorario(@PathVariable("beacon") String beacon) {
        HorarioDisciplina horarioDisciplina = horarioDisciplinaRepository
                .findHorarioDisciplinaByDiaSemanaAndHorarioNamedParams(
                DateTimeServices.getDayOfWeek(),
                DateTimeServices.getLocalTime(),
                beacon);

        if(horarioDisciplina != null) {
            return new ResponseEntity(horarioDisciplina, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

}
