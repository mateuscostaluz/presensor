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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private final PresencaCustomRepository presencaCustomRepository;
    private final PresencaRepository presencaRepository;

    @GetMapping("/")
    public ResponseEntity<List<PresencaRs>> findAll() {
        List<Presenca> presencas = presencaRepository.findAll();

        List<PresencaRs> presencasRs = presencas
                                       .stream()
                                       .map(PresencaRs::converter)
                                       .collect(Collectors.toList());

        if(presencasRs.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(presencasRs, HttpStatus.OK);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<PresencaRs>> findByFilter(
            @RequestParam(value = "disciplina", required = false) String disciplina,
            @RequestParam(value = "sala", required = false) Integer sala,
            @RequestParam(value = "data", required = false) String dataPresenca) {
        List<Presenca> presencas;

        if(dataPresenca != null) {
            presencas = presencaCustomRepository.find(disciplina, sala, LocalDate.parse(dataPresenca));
        } else {
            presencas = presencaCustomRepository.find(disciplina, sala, null);
        }

        List<PresencaRs> presencasRs = presencas
                                       .stream()
                                       .map(PresencaRs::converter)
                                       .collect(Collectors.toList());

        if(presencasRs.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(presencasRs, HttpStatus.OK);
        }
    }

    @RequestMapping(path = "/{raAluno}/{idHorarioDisciplina}", method = RequestMethod.POST)
    public ResponseEntity<String> savePresenca(@PathVariable("raAluno") Long raAluno, @PathVariable("idHorarioDisciplina") Long idHorarioDisciplina) {

        Optional<Aluno> aluno = alunoRepository.findById(raAluno);

        if (!aluno.isPresent()) {
            return new ResponseEntity("Aluno não encontrado", HttpStatus.NOT_FOUND);
        }

        Optional<HorarioDisciplina> horarioDisciplina = horarioDisciplinaRepository.findById(idHorarioDisciplina);

        if (!horarioDisciplina.isPresent()) {
            return new ResponseEntity("Disciplina não encontrada", HttpStatus.NOT_FOUND);
        }

        LocalDate dataPresenca = DateTimeServices.getLocalDate();

        Presenca presenca = presencaRepository
                .findPresencaByRaAlunoAndIdHorarioDisciplinaAndData
                        (raAluno, idHorarioDisciplina, dataPresenca);

        if (presenca == null) {
            Presenca presencaSave = new Presenca();

            presencaSave.setAluno(aluno.get());
            presencaSave.setHorarioDisciplina(horarioDisciplina.get());
            presencaSave.setDataPresenca(dataPresenca);
            presencaRepository.save(presencaSave);
        } else {
            return new ResponseEntity("Aluno já registrado nesta aula", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Presença registrada", HttpStatus.OK);

    }

}
