package br.gov.sp.fatec.presensor.controller;

import br.gov.sp.fatec.presensor.controller.dto.AlunoRq;
import br.gov.sp.fatec.presensor.model.Aluno;
import br.gov.sp.fatec.presensor.repository.AlunoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/aluno")
public class AlunoController {

    private final AlunoRepository alunoRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/")
    public ResponseEntity<AlunoRq> saveAluno(AlunoRq alunoRq) {

        System.out.println("AlunoRq: " + alunoRq.getEmail());

        if(alunoRepository.findByEmail(alunoRq.getEmail()) != null) {
            return new ResponseEntity("Aluno já registrado com o email:" + alunoRq.getEmail(), HttpStatus.CONFLICT);
        }

        Aluno aluno = new Aluno();

        aluno.setRa(alunoRq.getRa());
        aluno.setEmail(alunoRq.getEmail());
        aluno.setSenha(passwordEncoder.encode(alunoRq.getSenha()));
        aluno.setNome(alunoRq.getSenha());
        System.out.println("EMAIL CADASTRADO: " + alunoRq.getEmail());
        System.out.println("SENHA CADASTRADA: " + alunoRq.getSenha());
        System.out.println("SENHA CRIPTOGRAFADA: " + aluno.getSenha());
        alunoRepository.save(aluno);

        return new ResponseEntity("Aluno registrado com sucesso", HttpStatus.OK);
    }

}
