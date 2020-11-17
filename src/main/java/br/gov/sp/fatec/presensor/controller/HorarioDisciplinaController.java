package br.gov.sp.fatec.presensor.controller;


import br.gov.sp.fatec.presensor.controller.dto.HorarioDisciplinaRs;
import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import br.gov.sp.fatec.presensor.repository.HorarioDisciplinaRepository;
import br.gov.sp.fatec.presensor.services.DateTimeServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
    public List<HorarioDisciplinaRs> findAll() {
        List<HorarioDisciplina> horarioDisciplinas = horarioDisciplinaRepository.findAll();
        return horarioDisciplinas
                .stream()
                .map(HorarioDisciplinaRs::converter)
                .collect(Collectors.toList());
    }

    @GetMapping("/atual")
    public HorarioDisciplinaRs findHorarioDisciplinaByDiaSemanaAndHorario() throws Exception {
        HorarioDisciplina horarioDisciplina = horarioDisciplinaRepository
                                                .findHorarioDisciplinaByDiaSemanaAndHorarioNamedParams(
                                                  DateTimeServices.getDayOfWeek(),
                                                  DateTimeServices.getDateTimeFormatted());

        if(horarioDisciplina != null) {
            return HorarioDisciplinaRs.converter(horarioDisciplina);
        } else {
            throw new Exception("Sem aula no momento");
        }
    }

}
