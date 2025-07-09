package com.clinica.sitema.DTO;

public class AvaliacaoConsultaDTO {
    private Long medicoId;
    private Long pacienteId;
    private int nota;
    private String mensagem;

    public Long getMedicoId() { return medicoId; }
    public void setMedicoId(Long medicoId) { this.medicoId = medicoId; }

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public int getNota() { return nota; }
    public void setNota(int nota) { this.nota = nota; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
}
