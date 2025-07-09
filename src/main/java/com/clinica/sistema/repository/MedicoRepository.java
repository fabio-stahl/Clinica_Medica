package com.clinica.sistema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinica.sistema.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    boolean existsByNome(String nome);
    Medico findByNome(String nome);
    List<Medico> findByNomeAndEspecialidadeAndPlanoDeSaude(String nome, String especialidade, String planoDeSaude);
    List<Medico> findByNomeAndEspecialidade(String nome, String especialidade);
    List<Medico> findByNomeContainingIgnoreCase(String nome);
    List<Medico> findByEspecialidadeContainingIgnoreCase(String especialidade);
    List<Medico> findByNomeContainingIgnoreCaseAndEspecialidadeContainingIgnoreCase(String nome, String especialidade);

}
