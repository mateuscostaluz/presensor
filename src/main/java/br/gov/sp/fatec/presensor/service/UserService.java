package br.gov.sp.fatec.presensor.service;

import br.gov.sp.fatec.presensor.model.Aluno;
import br.gov.sp.fatec.presensor.repository.AlunoRepository;
import br.gov.sp.fatec.presensor.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String signin(String email, String senha) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, senha));
            return jwtTokenProvider.createToken(email, alunoRepository.findByEmail(email).getRoles());
        } catch (AuthenticationException e) {
            throw new Exception("Email e/ou senha inválidos", e);
        }
    }

    public String signup(Aluno aluno) throws Exception {
        if (!alunoRepository.existsByEmail(aluno.getEmail()) && !alunoRepository.existsById(aluno.getRa())) {
            aluno.setSenha(passwordEncoder.encode(aluno.getSenha()));
            alunoRepository.save(aluno);
            return jwtTokenProvider.createToken(aluno.getEmail(), aluno.getRoles());
        } else {
            throw new Exception("Este email e/ou RA já está em uso");
        }
    }

    public Aluno search(String email) throws Exception {
        Aluno aluno = alunoRepository.findByEmail(email);
        if (aluno == null) {
            throw new Exception("Não existe algum aluno cadastrado com este email");
        }
        return aluno;
    }

}
