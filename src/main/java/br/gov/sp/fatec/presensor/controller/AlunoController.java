package br.gov.sp.fatec.presensor.controller;

import br.gov.sp.fatec.presensor.dto.AlunoLogin;
import br.gov.sp.fatec.presensor.dto.AlunoRq;
import br.gov.sp.fatec.presensor.dto.AlunoRs;
import br.gov.sp.fatec.presensor.dto.AlunoToken;
import br.gov.sp.fatec.presensor.model.Aluno;
import br.gov.sp.fatec.presensor.model.Role;
import br.gov.sp.fatec.presensor.repository.AlunoRepository;
import br.gov.sp.fatec.presensor.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private UserService userService;

    @Autowired
    private final AlunoRepository alunoRepository;

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastro(@RequestBody AlunoRq alunoRq) {
        Aluno aluno = new Aluno();

        aluno.setRa(alunoRq.getRa());
        aluno.setEmail(alunoRq.getEmail());
        aluno.setSenha(alunoRq.getSenha());
        aluno.setNome(alunoRq.getNome());
        aluno.setRole(Role.ROLE_CLIENT.toString());

        return userService.signup(aluno);
    }

    @PostMapping("/login")
    public ResponseEntity<AlunoToken> login(@RequestBody AlunoLogin alunoLogin) {
        return userService.signin(alunoLogin.getEmail(), alunoLogin.getSenha());
    }

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

}
