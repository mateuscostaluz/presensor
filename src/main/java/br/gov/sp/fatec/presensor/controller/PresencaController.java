package br.gov.sp.fatec.presensor.controller;

import br.gov.sp.fatec.presensor.controller.dto.PresencaRs;
import br.gov.sp.fatec.presensor.model.HorarioDisciplina;
import br.gov.sp.fatec.presensor.model.Presenca;
import br.gov.sp.fatec.presensor.model.Usuario;
import br.gov.sp.fatec.presensor.repository.HorarioDisciplinaRepository;
import br.gov.sp.fatec.presensor.repository.PresencaRepository;
import br.gov.sp.fatec.presensor.repository.UsuarioRepository;
import br.gov.sp.fatec.presensor.services.DateTimeServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/presenca")
public class PresencaController {

    private final UsuarioRepository usuarioRepository;
    private final HorarioDisciplinaRepository horarioDisciplinaRepository;

    private final PresencaRepository presencaRepository;

    @GetMapping("/")
    public List<PresencaRs> findAll() {
        List<Presenca> presencas = presencaRepository.findAll();
        return presencas
                .stream()
                .map(PresencaRs::converter)
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/{raUsuario}/{idHorarioDisciplina}", method = RequestMethod.POST)
    public void savePresenca(@PathVariable("raUsuario") Long raUsuario, @PathVariable("idHorarioDisciplina") Long idHorarioDisciplina) throws Exception {

        Optional<Usuario> usuario = usuarioRepository.findById(raUsuario);

        Usuario usuarioSave;

        if(usuario.isPresent()) {
            usuarioSave = usuario.get();
        } else {
            throw new Exception("Usuário não encontrado");
        }

        Optional<HorarioDisciplina> horarioDisciplina = horarioDisciplinaRepository.findById(idHorarioDisciplina);

        HorarioDisciplina horarioDisciplinaSave;

        if(horarioDisciplina.isPresent()) {
            horarioDisciplinaSave = horarioDisciplina.get();
        } else {
            throw new Exception("Disciplina não encontrada");
        }

        Presenca presencaSave = new Presenca();

        presencaSave.setUsuario(usuarioSave);
        presencaSave.setHorarioDisciplina(horarioDisciplinaSave);
        presencaSave.setData(DateTimeServices.getLocalDate());

        Presenca presencaQuery = presencaRepository.findPresencaByUsuarioAndHorarioDisciplinaAndDataHora(
                usuarioSave,
                horarioDisciplinaSave,
                DateTimeServices.getLocalDate()
        );

        LocalDate datePresencaSave = presencaSave.getData();
        LocalDate datePresencaQuery = presencaQuery.getData();

        boolean raUsuarioEquals = presencaSave.getUsuario().getRa().toString().equals(
                                  presencaQuery.getUsuario().getRa().toString());
        boolean idHorarioDisciplinaEquals = presencaSave.getHorarioDisciplina().getId().toString().equals(
                                            presencaQuery.getHorarioDisciplina().getId().toString());
        boolean dateEquals = (datePresencaSave.compareTo(datePresencaQuery) == 0);

        if((raUsuarioEquals) && (idHorarioDisciplinaEquals) && (dateEquals)) {
            throw new Exception("Usuário já registrado");
        } else {
            presencaRepository.save(presencaSave);
        }
    }

}
