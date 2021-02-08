package br.gov.sp.fatec.presensor.controller;

import br.gov.sp.fatec.presensor.dto.PresencaRq;
import br.gov.sp.fatec.presensor.dto.PresencaRs;
import br.gov.sp.fatec.presensor.model.Aluno;
import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import br.gov.sp.fatec.presensor.model.Presenca;
import br.gov.sp.fatec.presensor.repository.AlunoRepository;
import br.gov.sp.fatec.presensor.repository.HorarioDisciplinaRepository;
import br.gov.sp.fatec.presensor.repository.PresencaCustomRepository;
import br.gov.sp.fatec.presensor.repository.PresencaRepository;
import br.gov.sp.fatec.presensor.service.DateTimeServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/presenca")
public class PresencaController {

    @Autowired
    private final AlunoRepository alunoRepository;

    @Autowired
    private final HorarioDisciplinaRepository horarioDisciplinaRepository;

    @Autowired
    private final PresencaRepository presencaRepository;

    @Autowired
    private final PresencaCustomRepository presencaCustomRepository;

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<PresencaRs>> findAll() {
        List<Presenca> presencas = presencaRepository.findAll();

        List<PresencaRs> presencasRs = presencas
                                       .stream()
                                       .map(PresencaRs::converter)
                                       .collect(Collectors.toList());

        if(presencasRs.isEmpty()) {
            return new ResponseEntity("Não existem listas de presenças cadastradas no sistema", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(presencasRs, HttpStatus.OK);
        }
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<PresencaRs>> findByCustomFilter(
            @RequestParam(value = "disciplina", required = false) String disciplina,
            @RequestParam(value = "sala", required = false) Integer sala,
            @RequestParam(value = "data", required = false) String dataPresenca) {

        List<Presenca> presencas;

        if (dataPresenca != null) {
            presencas = presencaCustomRepository.findBySiglaDisciplinaAndNumeroSalaAndData(sala, disciplina, LocalDate.parse(dataPresenca));
        } else {
            presencas = presencaCustomRepository.findBySiglaDisciplinaAndNumeroSalaAndData(sala, disciplina, null);
        }

        List<PresencaRs> presencasRs = presencas
                                       .stream()
                                       .map(PresencaRs::converter)
                                       .collect(Collectors.toList());

        if (presencasRs.isEmpty()) {
            return new ResponseEntity("Nenhuma lista de presenças encontrada com estes filtros", HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(presencasRs, HttpStatus.OK);
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<String> savePresenca(@RequestBody PresencaRq presencaRq) {

        Optional<Aluno> aluno = alunoRepository.findById(presencaRq.getRaAluno());

        if (!aluno.isPresent()) {
            return new ResponseEntity("Aluno não encontrado", HttpStatus.NOT_FOUND);
        }

        Optional<HorarioDisciplina> horarioDisciplina = horarioDisciplinaRepository.findById(presencaRq.getIdHorarioDisciplina());

        if (!horarioDisciplina.isPresent()) {
            return new ResponseEntity("Disciplina não encontrada", HttpStatus.NOT_FOUND);
        }

        LocalDate dataPresenca = DateTimeServices.getLocalDate();

        Presenca presenca = presencaRepository.findByRaAlunoAndIdHorarioDisciplinaAndData(presencaRq.getRaAluno(), presencaRq.getIdHorarioDisciplina(), dataPresenca);

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
