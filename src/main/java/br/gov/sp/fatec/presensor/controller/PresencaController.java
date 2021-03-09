package br.gov.sp.fatec.presensor.controller;

import br.gov.sp.fatec.presensor.dto.PresencaRq;
import br.gov.sp.fatec.presensor.dto.PresencaRs;
import br.gov.sp.fatec.presensor.dto.Response;
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

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public Response savePresenca(@RequestBody PresencaRq presencaRq) {

        Optional<Aluno> aluno = alunoRepository.findById(presencaRq.getRaAluno());

        if (!aluno.isPresent()) {
            return new Response(null, HttpStatus.NOT_FOUND.value(), "Aluno não encontrado.");
        }

        Optional<HorarioDisciplina> horarioDisciplina = horarioDisciplinaRepository.findById(presencaRq.getIdHorarioDisciplina());

        if (!horarioDisciplina.isPresent()) {
            return new Response(null, HttpStatus.NOT_FOUND.value(), "Disciplina não encontrada.");
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
            return new Response(null, HttpStatus.BAD_REQUEST.value(), "Aluno já registrado nesta aula.");
        }

        return new Response(null, HttpStatus.OK.value(), "Presença registrada com sucesso.");

    }

    @GetMapping("/")
    public Response findAll() {
        List<Presenca> presencas = presencaRepository.findAll();

        List<PresencaRs> presencasRs = presencas
                                       .stream()
                                       .map(PresencaRs::converter)
                                       .collect(Collectors.toList());

        if(presencasRs.isEmpty()) {
            return new Response(null, HttpStatus.NOT_FOUND.value(), "Não existem listas de presenças cadastradas no sistema.");
        } else {
            return new Response(presencasRs, HttpStatus.OK.value(), null);
        }
    }

    @GetMapping("/filter")
    public Response findByCustomFilter(
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
            return new Response(null, HttpStatus.NOT_FOUND.value(), "Nenhuma lista de presenças encontrada com estes filtros.");
        } else {
            return new Response(presencasRs, HttpStatus.OK.value(), null);
        }
    }

}
