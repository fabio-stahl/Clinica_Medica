package com.clinica.sitema.repository;

import com.clinica.sitema.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository {

    boolean existsByNome(String nome);
    Paciente findByNome(String nome);
    void save();

}
