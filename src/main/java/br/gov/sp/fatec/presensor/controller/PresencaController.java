package br.gov.sp.fatec.presensor.controller;

import br.gov.sp.fatec.presensor.controller.dto.PresencaRs;
import br.gov.sp.fatec.presensor.model.Aluno;
import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import br.gov.sp.fatec.presensor.model.Presenca;
import br.gov.sp.fatec.presensor.repository.AlunoRepository;
import br.gov.sp.fatec.presensor.repository.HorarioDisciplinaRepository;
import br.gov.sp.fatec.presensor.repository.PresencaCustomRepository;
import br.gov.sp.fatec.presensor.repository.PresencaRepository;
import br.gov.sp.fatec.presensor.services.DateTimeServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/presenca")
public class PresencaController {

    private final AlunoRepository alunoRepository;
    private final HorarioDisciplinaRepository horarioDisciplinaRepository;

    private final PresencaRepository presencaRepository;
    private final PresencaCustomRepository presencaCustomRepository;

    @GetMapping("/")
    public List<PresencaRs> findAll() {
        List<Presenca> presencas = presencaRepository.findAll();
        return presencas
               .stream()
               .map(PresencaRs::converter)
               .collect(Collectors.toList());
    }

    @GetMapping("")
    public List<PresencaRs> findByFilter(
            @RequestParam(value = "disciplina", required = false) String disciplina,
            @RequestParam(value = "sala", required = false) Integer sala,
            @RequestParam(value = "data", required = false) String data) {
        return presencaCustomRepository.find(disciplina, sala, LocalDate.parse(data))
               .stream()
               .map(PresencaRs::converter)
               .collect(Collectors.toList());
    }

    @RequestMapping(path = "/{raAluno}/{idHorarioDisciplina}", method = RequestMethod.POST)
    public void savePresenca(@PathVariable("raAluno") Long raAluno, @PathVariable("idHorarioDisciplina") Long idHorarioDisciplina) throws Exception {

        Optional<Aluno> aluno = alunoRepository.findById(raAluno);

        if (!aluno.isPresent()) {
            throw new Exception("Usuário não encontrado");
        }

        Optional<HorarioDisciplina> horarioDisciplina = horarioDisciplinaRepository.findById(idHorarioDisciplina);

        if (!horarioDisciplina.isPresent()) {
            throw new Exception("Disciplina não encontrada");
        }

        LocalDate data = DateTimeServices.getLocalDate();

        Presenca presenca = presencaRepository
                .findPresencaByRaAlunoAndIdHorarioDisciplinaAndData
                        (raAluno, idHorarioDisciplina, data);

        if (presenca == null) {
            Presenca presencaSave = new Presenca();

            presencaSave.setAluno(aluno.get());
            presencaSave.setHorarioDisciplina(horarioDisciplina.get());
            presencaSave.setData(data);
            presencaRepository.save(presencaSave);
        } else {
            throw new Exception("Usuário já registrado");
        }

    }

}
