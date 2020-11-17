package br.gov.sp.fatec.presensor.controller;

import br.gov.sp.fatec.presensor.controller.dto.UsuarioRq;
import br.gov.sp.fatec.presensor.controller.dto.UsuarioRs;
import br.gov.sp.fatec.presensor.model.Usuario;
import br.gov.sp.fatec.presensor.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    @GetMapping("/{email}/{senha}")
    public UsuarioRs findByEmailAndSenha(@PathVariable("email") String email, @PathVariable("senha") String senha) throws Exception {
        Usuario usuario = usuarioRepository.findUsuarioByEmailAndSenha(email, senha);

        if(usuario != null) {
            return UsuarioRs.converter(usuario);
        } else {
            throw new Exception("Usuário não encontrado");
        }
    }

    @PostMapping("/")
    public void saveUsuario(@Valid @RequestBody UsuarioRq usuarioRq) {
        Usuario usuario = new Usuario();

        usuario.setRa(usuarioRq.getRa());
        usuario.setEmail(usuarioRq.getEmail());
        usuario.setSenha(usuarioRq.getSenha());
        usuarioRepository.save(usuario);
    }

}
