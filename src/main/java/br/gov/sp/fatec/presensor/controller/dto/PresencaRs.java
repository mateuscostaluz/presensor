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

        Usuario usuario = usuarioRepository.getOne(p.getRaUsuario());

        HorarioDisciplina horarioDisciplina = horarioDisciplinaRepository.getOne(p.getIdHorarioDisciplina());

        LocalDate data = DateTimeServices.getLocalDate();

        presencaRs.setUsuario(usuario);
        presencaRs.setHorarioDisciplina(horarioDisciplina);
        presencaRs.setData(data);
        return presencaRs;
    }

}
