package br.gov.sp.fatec.presensor.repository;

import br.gov.sp.fatec.presensor.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM usuarios " +
            "WHERE email = :email " +
            "AND senha = :senha ", nativeQuery = true)
    Usuario findUsuarioByEmailAndSenha(
            @Param("email") String email,
            @Param("senha") String senha
    );
}
