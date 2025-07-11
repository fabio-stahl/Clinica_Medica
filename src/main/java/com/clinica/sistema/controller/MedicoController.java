package com.clinica.sistema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinica.sistema.model.Especialidade;
import com.clinica.sistema.model.Medico;
import com.clinica.sistema.model.MedicoDTO;
import com.clinica.sistema.repository.MedicoRepository;
import com.clinica.sistema.service.MedicoService;

@RestController
@RequestMapping("/buscarmedicos")
@CrossOrigin(origins = "*")
public class MedicoController {

    private final MedicoRepository medicoRepository;

    @Autowired
    private MedicoService medicoService;

    MedicoController(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    // POST /medicos
    @PostMapping
    public ResponseEntity<String> cadastrarMedico(@RequestBody Medico medico) {
        medicoService.cadastrarMedico(medico);
        return ResponseEntity.ok("Médico cadastrado com sucesso!");
    }


    // PUT /medicos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarMedico(@PathVariable Long id, @RequestBody MedicoDTO medicoDTO) {
        try {
            Medico medico = medicoService.buscarPorId(id);
            if (medico == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Médico não encontrado.");
            }

            medico.setNome(medicoDTO.getNome());
            medico.setEspecialidade(Especialidade.fromDescricao(medicoDTO.getEspecialidade()));  // converte usando o fromDescricao
            medico.setPlanoDeSaude(medicoDTO.getPlanoDeSaude());

            medicoRepository.save(medico);

            return ResponseEntity.ok("Médico atualizado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Especialidade inválida: " + e.getMessage());
        }
    }

    
    @GetMapping
    public List<Medico> listar(@RequestParam(required = false) String nome,
                               @RequestParam(required = false) String especialidade) {
        // Chama o método do service para buscar médicos
        return medicoService.buscarMedicos(nome, especialidade);
    }
    
}