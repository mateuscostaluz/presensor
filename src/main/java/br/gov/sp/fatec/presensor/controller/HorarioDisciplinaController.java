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
    public ResponseEntity<List> findAll() {
        List<HorarioDisciplina> horarioDisciplinas = horarioDisciplinaRepository.findAll();

        List<HorarioDisciplinaRs> horarioDisciplinaRs = horarioDisciplinas
                                                        .stream()
                                                        .map(HorarioDisciplinaRs::converter)
                                                        .collect(Collectors.toList());

        if(horarioDisciplinaRs != null) {
            return new ResponseEntity(horarioDisciplinaRs, HttpStatus.OK);)
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

        HorarioDisciplinaRs horarioDisciplinaRs = HorarioDisciplinaRs.converter(horarioDisciplina);

        if(horarioDisciplina != null) {
            return new ResponseEntity(horarioDisciplinaRs, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

}
