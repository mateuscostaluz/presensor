package br.gov.sp.fatec.presensor.service;

import br.gov.sp.fatec.presensor.dto.Response;
import br.gov.sp.fatec.presensor.model.Aluno;
import br.gov.sp.fatec.presensor.repository.AlunoRepository;
import br.gov.sp.fatec.presensor.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public Response signin(String email, String senha) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, senha));
            return new Response(jwtTokenProvider.createToken(alunoRepository.findByEmail(email)), HttpStatus.OK.value(), null);
        } catch (AuthenticationException e) {
            return new Response(null, HttpStatus.BAD_REQUEST.value(), "Email e/ou senha inválidos.");
        }
    }

    public Response signup(Aluno aluno) {
        if (alunoRepository.existsByEmail(aluno.getEmail())) {
            return new Response(null, HttpStatus.BAD_REQUEST.value(), "Este email já está em uso.");
        }

        if (alunoRepository.existsById(aluno.getRa())) {
            return new Response(null, HttpStatus.BAD_REQUEST.value(), "Este RA já está em uso.");
        }

        aluno.setSenha(passwordEncoder.encode(aluno.getSenha()));
        alunoRepository.save(aluno);
        return new Response(null, HttpStatus.OK.value(), "Cadastro efetuado com sucesso.");
    }

}
