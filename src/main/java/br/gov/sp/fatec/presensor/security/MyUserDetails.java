package br.gov.sp.fatec.presensor.security;

import br.gov.sp.fatec.presensor.model.Aluno;
import br.gov.sp.fatec.presensor.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetails implements UserDetailsService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final Aluno aluno = alunoRepository.findByEmail(email);

        if (aluno == null) {
            throw new UsernameNotFoundException("Usuário com o email '" + email + "' não encontrado");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(aluno.getSenha())
                .authorities(aluno.getRole())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

}
