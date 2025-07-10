package com.clinica.sistema.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinica.sistema.model.Especialidade;
import com.clinica.sistema.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    boolean existsByNome(String nome);
    Medico findByNome(String nome);
    List<Medico> findByNomeContainingIgnoreCase(String nome);
    List<Medico> findByEspecialidade(Especialidade especialidade);
    List<Medico> findByNomeContainingIgnoreCaseAndEspecialidade(String nome, Especialidade especialidade);
}
