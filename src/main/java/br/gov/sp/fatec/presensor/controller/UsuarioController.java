package br.gov.sp.fatec.presensor.controller;

import br.gov.sp.fatec.presensor.controller.dto.UsuarioRq;
import br.gov.sp.fatec.presensor.controller.dto.UsuarioRs;
import br.gov.sp.fatec.presensor.model.Usuario;
import br.gov.sp.fatec.presensor.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    @GetMapping("/")
    public List<UsuarioRs> findAll() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios
                .stream()
                .map(UsuarioRs::converter)
                .collect(Collectors.toList());
    }

    @GetMapping("/{ra}/{senha}")
    public UsuarioRs findByEmailAndSenha(@PathVariable("email") String email, @PathVariable("senha") String senha) throws Exception {
        Usuario usuario = usuarioRepository.findByEmailAndSenha(email, senha);

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

    @PutMapping("/{ra}")
    public void updateUsuario(@Valid @PathVariable("ra") Long ra, @RequestBody UsuarioRq usuarioRq) throws Exception {
        Optional<Usuario> usuario = usuarioRepository.findById(ra);

        if(usuario.isPresent()) {
            Usuario usuarioSave = usuario.get();
            usuarioSave.setRa(usuarioRq.getRa());
            usuarioSave.setEmail(usuarioRq.getEmail());
            usuarioSave.setSenha(usuarioRq.getSenha());
            usuarioRepository.save(usuarioSave);
        } else {
            throw new Exception("Usuário não encontrado");
        }
    }

}
