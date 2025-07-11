package com.clinica.sistema.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.clinica.sistema.model.Consulta;
import com.clinica.sistema.model.Paciente;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Query("SELECT c FROM Consulta c WHERE c.paciente = :paciente AND c.data = :data")
    Consulta findByPacienteAndData(@Param("paciente") Paciente paciente, @Param("data") LocalDateTime data);

    // outros métodos já existentes...
}
