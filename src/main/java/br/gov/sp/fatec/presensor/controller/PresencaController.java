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

    @GetMapping("/")
    public List<PresencaRs> findByFilter(
            @RequestParam(value = "disciplina", required = false) String disciplina,
            @RequestParam("sala", required = false) Integer sala,
            @RequestParam("data", required = false) LocalDate data) {
        List<Presenca> presencas = (List<Presenca>) presencaRepository.findPresencaByDisciplinaAndSalaAndData(disciplina, sala, data);
        return presencas
                .stream()
                .map(PresencaRs::converter)
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/{raUsuario}/{idHorarioDisciplina}", method = RequestMethod.POST)
    public void savePresenca(@PathVariable("raUsuario") Long raUsuario, @PathVariable("idHorarioDisciplina") Long idHorarioDisciplina) throws Exception {

        Optional<Usuario> usuario = usuarioRepository.findById(raUsuario);

        if (!usuario.isPresent()) {
            throw new Exception("Usuário não encontrado");
        }

        Optional<HorarioDisciplina> horarioDisciplina = horarioDisciplinaRepository.findById(idHorarioDisciplina);

        if (!horarioDisciplina.isPresent()) {
            throw new Exception("Disciplina não encontrada");
        }

        LocalDate data = DateTimeServices.getLocalDate();

        Presenca presenca = presencaRepository
                .findPresencaByRaUsuarioAndIdHorarioDisciplinaAndData
                        (raUsuario, idHorarioDisciplina, data);

        if (presenca == null) {
            Presenca presencaSave = new Presenca();

            presencaSave.setUsuario(usuario.get());
            presencaSave.setHorarioDisciplina(horarioDisciplina.get());
            presencaSave.setData(data);
            presencaRepository.save(presencaSave);
        } else {
            throw new Exception("Usuário já registrado");
        }

    }

}
