package br.gov.sp.fatec.presensor.controller;


import br.gov.sp.fatec.presensor.controller.dto.HorarioDisciplinaRs;
import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import br.gov.sp.fatec.presensor.repository.HorarioDisciplinaRepository;
import br.gov.sp.fatec.presensor.services.DateTimeServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/horario")
public class HorarioDisciplinaController {

    private final HorarioDisciplinaRepository horarioDisciplinaRepository;

    @GetMapping("/")
    public ResponseEntity<List<HorarioDisciplinaRs>> findAll() {
        List<HorarioDisciplina> horarioDisciplinas = horarioDisciplinaRepository.findAll();

        List<HorarioDisciplinaRs> horarioDisciplinasRs = horarioDisciplinas
                                                         .stream()
                                                         .map(HorarioDisciplinaRs::converter)
                                                         .collect(Collectors.toList());

        if(horarioDisciplinasRs.isEmpty()) {
            return new ResponseEntity(horarioDisciplinasRs, HttpStatus.OK);
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
            return new ResponseEntity(HorarioDisciplinaRs.converter(horarioDisciplina), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

}
