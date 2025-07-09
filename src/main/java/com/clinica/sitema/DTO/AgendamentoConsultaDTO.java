package com.clinica.sitema.DTO;

import java.time.LocalDate;

public class AgendamentoConsultaDTO {
    private Long medicoId;
    private Long pacienteId;
    private LocalDate data;

    public Long getMedicoId() { return medicoId; }
    public void setMedicoId(Long medicoId) { this.medicoId = medicoId; }

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
}
