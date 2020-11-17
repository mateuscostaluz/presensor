package br.gov.sp.fatec.presensor.controller;

import br.gov.sp.fatec.presensor.controller.dto.PresencaRq;
import br.gov.sp.fatec.presensor.controller.dto.PresencaRs;
import br.gov.sp.fatec.presensor.model.Presenca;
import br.gov.sp.fatec.presensor.repository.PresencaRepository;
import br.gov.sp.fatec.presensor.services.DateTimeServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/presenca")
public class PresencaController {

    private final PresencaRepository presencaRepository;

    @GetMapping("/")
    public List<PresencaRs> findAll() {
        List<Presenca> presencas = presencaRepository.findAll();
        return presencas
                .stream()
                .map(PresencaRs::converter)
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    public void savePresenca(@Valid @RequestBody PresencaRq presencaRq) {
        Presenca presenca = new Presenca();

        presenca.setUsuario(presencaRq.getUsuario());
        presenca.setHorarioDisciplina(presencaRq.getHorarioDisciplina());
        presenca.setDataHora(DateTimeServices.geTimestamp());
        presencaRepository.save(presenca);
    }

}
