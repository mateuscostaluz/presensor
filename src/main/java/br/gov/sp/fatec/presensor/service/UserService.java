package br.gov.sp.fatec.presensor.service;

import br.gov.sp.fatec.presensor.dto.BodyRs;
import br.gov.sp.fatec.presensor.model.Aluno;
import br.gov.sp.fatec.presensor.repository.AlunoRepository;
import br.gov.sp.fatec.presensor.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<BodyRs> signin(String email, String senha) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, senha));
            return new ResponseEntity(new BodyRs(jwtTokenProvider.createToken(alunoRepository.findByEmail(email))), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity(new BodyRs("Email e/ou senha inválidos"), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<BodyRs> signup(Aluno aluno) {
        if (alunoRepository.existsByEmail(aluno.getEmail())) {
            return new ResponseEntity(new BodyRs("Este email já está em uso"), HttpStatus.BAD_REQUEST);
        }

        if (alunoRepository.existsById(aluno.getRa())) {
            return new ResponseEntity(new BodyRs("Este RA já está em uso"), HttpStatus.BAD_REQUEST);
        }

        aluno.setSenha(passwordEncoder.encode(aluno.getSenha()));
        alunoRepository.save(aluno);
        return new ResponseEntity(new BodyRs("Cadastro efetuado com sucesso"), HttpStatus.OK);
    }

}
