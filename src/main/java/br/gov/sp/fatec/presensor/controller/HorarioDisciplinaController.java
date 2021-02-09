package br.gov.sp.fatec.presensor.controller;


import br.gov.sp.fatec.presensor.dto.HorarioDisciplinaRs;
import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import br.gov.sp.fatec.presensor.repository.HorarioDisciplinaRepository;
import br.gov.sp.fatec.presensor.service.DateTimeServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/horario")
public class HorarioDisciplinaController {

    @Autowired
    private final HorarioDisciplinaRepository horarioDisciplinaRepository;

    @GetMapping("/atual")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<HorarioDisciplina> findHorarioDisciplinaByDiaSemanaAndHorario(@RequestParam(value = "beacon") String beacon) {

        HorarioDisciplina horarioDisciplinaRs = horarioDisciplinaRepository
                .findByDiaSemanaAndHorarioNamedParams(
                        DateTimeServices.getDayOfWeek(),
                        DateTimeServices.getLocalTime(),
                        beacon);

        if(horarioDisciplinaRs != null) {
            return new ResponseEntity(HorarioDisciplinaRs.converter(horarioDisciplinaRs), HttpStatus.OK);
        }

        return new ResponseEntity("Sem aula no momento", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/")
    public ResponseEntity<List<HorarioDisciplinaRs>> findAll() {
        List<HorarioDisciplina> horarioDisciplinas = horarioDisciplinaRepository.findAll();

        List<HorarioDisciplinaRs> horarioDisciplinasRs = horarioDisciplinas
                                                         .stream()
                                                         .map(HorarioDisciplinaRs::converter)
                                                         .collect(Collectors.toList());

        if(horarioDisciplinasRs.isEmpty()) {
            return new ResponseEntity("Não existem horários de disciplinas cadastrados no sistema", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(horarioDisciplinasRs, HttpStatus.OK);
    }

}
