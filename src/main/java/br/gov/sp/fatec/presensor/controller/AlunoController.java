package br.gov.sp.fatec.presensor.controller;

import br.gov.sp.fatec.presensor.controller.dto.AlunoRq;
import br.gov.sp.fatec.presensor.controller.dto.AlunoRs;
import br.gov.sp.fatec.presensor.model.Aluno;
import br.gov.sp.fatec.presensor.repository.AlunoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/aluno")
public class AlunoController {

    private final AlunoRepository alunoRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ResponseEntity<List<AlunoRs>> findAll() {
        List<Aluno> alunos = alunoRepository.findAll();

        List<AlunoRs> alunoRs = alunos
                .stream()
                .map(AlunoRs::converter)
                .collect(Collectors.toList());

        if(alunoRs.isEmpty()) {
            return new ResponseEntity("Não existem alunos cadastrados no sistema", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity(alunoRs, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<AlunoRq> saveAluno(@RequestBody AlunoRq alunoRq) {

        if(alunoRepository.findByEmail(alunoRq.getEmail()) != null) {
            return new ResponseEntity("Aluno já registrado com o email: " + alunoRq.getEmail(), HttpStatus.CONFLICT);
        }

        Aluno aluno = new Aluno();

        aluno.setRa(alunoRq.getRa());
        aluno.setEmail(alunoRq.getEmail());
        aluno.setSenha(passwordEncoder.encode(alunoRq.getSenha()));
        aluno.setNome(alunoRq.getSenha());
        alunoRepository.save(aluno);

        return new ResponseEntity("Aluno registrado com sucesso", HttpStatus.OK);
    }

}
