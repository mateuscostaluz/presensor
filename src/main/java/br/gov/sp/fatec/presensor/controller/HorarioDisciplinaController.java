package br.gov.sp.fatec.presensor.controller;


import br.gov.sp.fatec.presensor.controller.dto.HorarioDisciplinaRs;
import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import br.gov.sp.fatec.presensor.repository.HorarioDisciplinaRepository;
import br.gov.sp.fatec.presensor.services.DateTimeServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(horarioDisciplinasRs, HttpStatus.OK);
        }
    }

    @GetMapping("/atual")
    public ResponseEntity<HorarioDisciplina> findHorarioDisciplinaByDiaSemanaAndHorario(
            @RequestParam(value = "beacon") String beacon) {

        HorarioDisciplina horarioDisciplinaRs = horarioDisciplinaRepository
                                                .findHorarioDisciplinaByDiaSemanaAndHorarioNamedParams(
                                                DateTimeServices.getDayOfWeek(),
                                                DateTimeServices.getLocalTime(),
                                                beacon);

        if(horarioDisciplinaRs != null) {
            return new ResponseEntity(HorarioDisciplinaRs.converter(horarioDisciplinaRs), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

}
