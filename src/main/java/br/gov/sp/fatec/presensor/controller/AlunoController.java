package br.gov.sp.fatec.presensor.controller;

import br.gov.sp.fatec.presensor.controller.dto.AlunoRq;
import br.gov.sp.fatec.presensor.controller.dto.AlunoRs;
import br.gov.sp.fatec.presensor.model.Aluno;
import br.gov.sp.fatec.presensor.repository.AlunoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/aluno")
public class AlunoController {

    private final AlunoRepository alunoRepository;

    @GetMapping("")
    public ResponseEntity<AlunoRs> findAlunoByEmailAndSenha(
            @RequestParam(value = "email") String email,
            @RequestParam(value = "senha") String senha) {
        Aluno aluno = alunoRepository.findByEmailAndSenha(email, senha);

        if(aluno != null) {
            AlunoRs alunoRs = AlunoRs.converter(aluno);
            return new ResponseEntity(alunoRs, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/")
    public void saveAluno(@Valid @RequestBody AlunoRq alunoRq) {
        Aluno aluno = new Aluno();

        aluno.setRa(alunoRq.getRa());
        aluno.setEmail(alunoRq.getEmail());
        aluno.setSenha(alunoRq.getSenha());
        aluno.setNome(alunoRq.getNome());
        alunoRepository.save(aluno);
    }

}
