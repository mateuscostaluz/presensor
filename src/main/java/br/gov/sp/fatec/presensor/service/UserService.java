package br.gov.sp.fatec.presensor.service;

import br.gov.sp.fatec.presensor.dto.AlunoToken;
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

    public ResponseEntity<AlunoToken> signin(String email, String senha) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, senha));
            AlunoToken alunoToken = new AlunoToken(jwtTokenProvider.createToken(alunoRepository.findByEmail(email)));
            return new ResponseEntity(alunoToken, HttpStatus.OK);
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
        return new ResponseEntity("Cadastro efetuado com sucesso", HttpStatus.OK);
    }

}
