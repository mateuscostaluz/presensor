package br.gov.sp.fatec.presensor.controller.dto;

import br.gov.sp.fatec.presensor.model.Usuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRs {

    private Long ra;
    private String email;
    private String senha;

    public static UsuarioRs converter(Usuario u) {
        UsuarioRs usuarioRs = new UsuarioRs();
        usuarioRs.setRa(u.getRa());
        usuarioRs.setEmail(u.getEmail());
        usuarioRs.setSenha(u.getSenha());
        return usuarioRs;
    }

}
