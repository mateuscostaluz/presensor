package br.gov.sp.fatec.presensor.controller;

import br.gov.sp.fatec.presensor.controller.dto.PresencaRs;
import br.gov.sp.fatec.presensor.model.Aluno;
import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import br.gov.sp.fatec.presensor.model.Presenca;
import br.gov.sp.fatec.presensor.repository.AlunoRepository;
import br.gov.sp.fatec.presensor.repository.HorarioDisciplinaRepository;
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

    private final PresencaRepository presencaRepository;

    @GetMapping("/")
    public ResponseEntity<List<PresencaRs>> findAll() {
        List<Presenca> presencas = presencaRepository.findAll();

        List<PresencaRs> presencasRs = presencas
                                       .stream()
                                       .map(PresencaRs::converter)
                                       .collect(Collectors.toList());

        if(presencasRs != null) {
            return new ResponseEntity(presencasRs, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<PresencaRs>> findByFilter(
            @RequestParam(value = "disciplina", required = false) String disciplina,
            @RequestParam(value = "sala", required = false) Integer sala,
            @RequestParam(value = "data_presenca", required = false) String data) {
        List<Presenca> presencas = presencaRepository.findPresencaByDisciplinaAndSalaAndData(disciplina, sala, LocalDate.parse(data));

        List<PresencaRs> presencasRs = presencas
                                       .stream()
                                       .map(PresencaRs::converter)
                                       .collect(Collectors.toList());

        if(presencasRs != null) {
            return new ResponseEntity(presencasRs, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(path = "/{raAluno}/{idHorarioDisciplina}", method = RequestMethod.POST)
    public ResponseEntity<String> savePresenca(@PathVariable("raAluno") Long raAluno, @PathVariable("idHorarioDisciplina") Long idHorarioDisciplina) {

        Optional<Aluno> aluno = alunoRepository.findById(raAluno);

        if (!aluno.isPresent()) {
            return new ResponseEntity("Usuário não encontrado", HttpStatus.NOT_FOUND);
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
            return new ResponseEntity("Usuário já registrado", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Presença registrada", HttpStatus.OK);

    }

}
