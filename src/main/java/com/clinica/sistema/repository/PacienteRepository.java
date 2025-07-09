package com.clinica.sistema.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinica.sistema.model.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    boolean existsByNome(String nome);
    Paciente findByNome(String nome);
}
