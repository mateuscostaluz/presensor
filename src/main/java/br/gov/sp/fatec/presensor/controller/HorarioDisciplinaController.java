package br.gov.sp.fatec.presensor.controller;


import br.gov.sp.fatec.presensor.dto.HorarioDisciplinaAtualRs;
import br.gov.sp.fatec.presensor.dto.HorarioDisciplinaRs;
import br.gov.sp.fatec.presensor.dto.Response;
import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import br.gov.sp.fatec.presensor.repository.HorarioDisciplinaRepository;
import br.gov.sp.fatec.presensor.service.DateTimeServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public Response findHorarioDisciplinaByDiaSemanaAndHorario(@RequestParam(value = "beacon") String beacon) {

        HorarioDisciplina horarioDisciplinaRs = horarioDisciplinaRepository
                .findByDiaSemanaAndHorarioNamedParams(
                DateTimeServices.getDayOfWeek(),
                DateTimeServices.getLocalTime(),
                beacon);

        if(horarioDisciplinaRs != null) {
            return new Response(HorarioDisciplinaAtualRs.converter(horarioDisciplinaRs), HttpStatus.OK.value(), null);
        }

        return new Response(null, HttpStatus.NOT_FOUND.value(), "Sem aula no momento.");
    }

    @GetMapping("/")
    public Response findAll() {
        List<HorarioDisciplina> horarioDisciplinas = horarioDisciplinaRepository.findAll();

        List<HorarioDisciplinaRs> horarioDisciplinasRs = horarioDisciplinas
                                                         .stream()
                                                         .map(HorarioDisciplinaRs::converter)
                                                         .collect(Collectors.toList());

        if(horarioDisciplinasRs.isEmpty()) {
            return new Response(null, HttpStatus.NO_CONTENT.value(), "Não existem horários de disciplinas cadastrados no sistema.");
        }

        return new Response(horarioDisciplinasRs, HttpStatus.OK.value(), null);
    }

}
