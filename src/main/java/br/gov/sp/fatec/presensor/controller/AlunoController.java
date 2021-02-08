package br.gov.sp.fatec.presensor.controller;

import br.gov.sp.fatec.presensor.dto.AlunoLogin;
import br.gov.sp.fatec.presensor.dto.AlunoRq;
import br.gov.sp.fatec.presensor.dto.AlunoRs;
import br.gov.sp.fatec.presensor.model.Aluno;
import br.gov.sp.fatec.presensor.model.Role;
import br.gov.sp.fatec.presensor.repository.AlunoRepository;
import br.gov.sp.fatec.presensor.service.UserService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/aluno")
@Api(tags = "aluno")
public class AlunoController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private final AlunoRepository alunoRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AlunoLogin alunoLogin) {
        return userService.signin(alunoLogin.getEmail(), alunoLogin.getSenha());
    }

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

    @GetMapping(value = "/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object search(@ApiParam("email") @PathVariable String email) {
        return userService.search(email);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
