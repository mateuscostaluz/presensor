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

import javax.servlet.http.HttpServletRequest;

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

    public String signin(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return jwtTokenProvider.createToken(email, alunoRepository.findByEmail(email).getRoles());
        } catch (AuthenticationException e) {
            throw new Exception("Email e/ou senha inválidos", e);
        }
    }

    public String signup(Aluno aluno) throws Exception {
        if (!alunoRepository.existsByEmail(aluno.getEmail())) {
            aluno.setSenha(passwordEncoder.encode(aluno.getSenha()));
            alunoRepository.save(aluno);
            return jwtTokenProvider.createToken(aluno.getEmail(), aluno.getRoles());
        } else {
            throw new Exception("Este email já está em uso");
        }
    }

    public void delete(String email) {
        alunoRepository.deleteByEmail(email);
    }

    public Aluno search(String email) throws Exception {
        Aluno aluno = alunoRepository.findByEmail(email);
        if (aluno == null) {
            throw new Exception("Não existe algum aluno cadastrado com este email");
        }
        return aluno;
    }

}
