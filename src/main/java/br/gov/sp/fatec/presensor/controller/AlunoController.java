package br.gov.sp.fatec.presensor.controller;

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
    @ApiOperation(value = "${AlunoController.login}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Algo deu errado"),
            @ApiResponse(code = 422, message = "Email e/ou senha inválidos")})
    public String login(@RequestParam String email,
                        @RequestParam String senha) throws Exception {
        return userService.signin(email, senha);
    }

    @PostMapping("/cadastro")
    @ApiOperation(value = "${AlunoController.cadastro}")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Algo deu errado"),
            @ApiResponse(code = 403, message = "Acesso negado"),
            @ApiResponse(code = 422, message = "Este email já está em uso")})
    public String signup(@ApiParam("aluno") @RequestBody AlunoRq alunoRq) throws Exception {
        Aluno aluno = new Aluno();

        aluno.setRa(alunoRq.getRa());
        aluno.setEmail(alunoRq.getEmail());
        aluno.setSenha(alunoRq.getSenha());
        aluno.setNome(alunoRq.getNome());
        aluno.setRole(Role.ROLE_CLIENT.toString());

        return userService.signup(modelMapper.map(aluno, Aluno.class));
    }

    @GetMapping(value = "/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${AlunoController.search}", response = AlunoRs.class, authorizations = { @Authorization(value="apiKey") })
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Algo deu errado"),
            @ApiResponse(code = 403, message = "Acesso negado"),
            @ApiResponse(code = 404, message = "Aluno não encontradp"),
            @ApiResponse(code = 500, message = "Token JWT expirado ou inválido")})
    public AlunoRs search(@ApiParam("email") @PathVariable String email) throws Exception {
        return modelMapper.map(userService.search(email), AlunoRs.class);
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
