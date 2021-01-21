package br.gov.sp.fatec.presensor.controller;

import br.gov.sp.fatec.presensor.controller.dto.AlunoRq;
import br.gov.sp.fatec.presensor.controller.dto.AlunoRs;
import br.gov.sp.fatec.presensor.model.Aluno;
import br.gov.sp.fatec.presensor.repository.AlunoRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/aluno")
public class AlunoController {

    private final AlunoRepository alunoRepository;

    @RequestMapping(path = "/{email}/{senha}", method = RequestMethod.GET)
    public AlunoRs findAlunoByEmailAndSenha(@PathVariable("email") String email, @PathVariable("senha") String senha) throws Exception {
        Aluno aluno = alunoRepository.findAlunoByEmailAndSenha(email, senha);

        if(aluno != null) {
            return AlunoRs.converter(aluno);
        } else {
            throw new Exception("Aluno não encontrado");
        }
    }

    @PostMapping("/")
    public void saveAluno(@Valid @RequestBody AlunoRq alunoRq) {
        Aluno aluno = new Aluno();

        aluno.setRa(alunoRq.getRa());
        aluno.setEmail(alunoRq.getEmail());
        aluno.setSenha(alunoRq.getSenha());
        aluno.setNome(alunoRq.getNome());
        alunoRepository.save(aluno);
    }

}
