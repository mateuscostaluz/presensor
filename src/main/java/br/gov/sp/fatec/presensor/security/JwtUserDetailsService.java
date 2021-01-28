package br.gov.sp.fatec.presensor.security;

import br.gov.sp.fatec.presensor.model.Aluno;
import br.gov.sp.fatec.presensor.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Aluno aluno = alunoRepository.findByEmail(email);

        if(aluno == null) {
            throw new UsernameNotFoundException("Aluno não encontrado!");
        }
        return aluno;
    }

}
