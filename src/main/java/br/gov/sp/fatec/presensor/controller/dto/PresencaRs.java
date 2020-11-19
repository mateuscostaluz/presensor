package br.gov.sp.fatec.presensor.controller.dto;

import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import br.gov.sp.fatec.presensor.model.Presenca;
import br.gov.sp.fatec.presensor.model.Usuario;
import br.gov.sp.fatec.presensor.repository.HorarioDisciplinaRepository;
import br.gov.sp.fatec.presensor.repository.UsuarioRepository;
import br.gov.sp.fatec.presensor.services.DateTimeServices;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

@Getter
@Setter
public class PresencaRs {

    public static UsuarioRepository usuarioRepository;
    public static HorarioDisciplinaRepository horarioDisciplinaRepository;

    public Usuario usuario;
    public HorarioDisciplina horarioDisciplina;
    public LocalDate data;

    public static PresencaRs converter(Presenca p) {

        PresencaRs presencaRs = new PresencaRs();

        Optional<Usuario> u = usuarioRepository.findById(p.getRaUsuario());

        if (u.isPresent()) {
            Usuario usuario = u.get();
            presencaRs.setUsuario(usuario);
        }

        Optional<HorarioDisciplina> hd = horarioDisciplinaRepository.findById(p.getIdHorarioDisciplina());

        if (hd.isPresent()) {
            HorarioDisciplina horarioDisciplina = hd.get();
            presencaRs.setHorarioDisciplina(horarioDisciplina);
        }

        LocalDate data = DateTimeServices.getLocalDate();

        presencaRs.setData(data);

        return presencaRs;
    }

}
