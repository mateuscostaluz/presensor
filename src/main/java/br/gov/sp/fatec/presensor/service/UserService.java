package br.gov.sp.fatec.presensor.service;

import br.gov.sp.fatec.presensor.dto.AlunoRs;
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

    public ResponseEntity<String> signin(String email, String senha) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, senha));
            return new ResponseEntity(jwtTokenProvider.createToken(email, alunoRepository.findByEmail(email).getRoles()), HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity("Email e/ou senha inválidos", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public ResponseEntity<String> signup(Aluno aluno) {
        if (alunoRepository.existsByEmail(aluno.getEmail())) {
            return new ResponseEntity("Este email já está em uso", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (alunoRepository.existsById(aluno.getRa())) {
            return new ResponseEntity("Este RA já está em uso", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        aluno.setSenha(passwordEncoder.encode(aluno.getSenha()));
        alunoRepository.save(aluno);
        return new ResponseEntity(jwtTokenProvider.createToken(aluno.getEmail(), aluno.getRoles()), HttpStatus.OK);
    }

    public Object search(String email) {
        Aluno aluno = alunoRepository.findByEmail(email);
        if (aluno == null) {
            System.out.println(aluno.toString());
            return new ResponseEntity("Aluno não encontradp", HttpStatus.NOT_FOUND);
        }
        System.out.println(aluno.toString());
        AlunoRs alunoRs = AlunoRs.converter(aluno);
        return new ResponseEntity(alunoRs, HttpStatus.OK);
    }

}
