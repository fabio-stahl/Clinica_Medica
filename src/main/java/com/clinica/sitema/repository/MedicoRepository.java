package com.clinica.sitema.repository;

import com.clinica.sitema.model.Medico;
import com.clinica.sitema.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicoRepository extends JpaRepository {
    boolean existsByNome(String nome);
    Medico findByNome(String nome);
    void save();
    List<Medico> findByNomeAndEspecialidadeAndPlanoDeSaude(String nome, String especialidade, String planoDeSaude);
    List<Medico> findByNomeAndEspecialidade(String nome, String especialidade);


}
