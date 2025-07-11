import com.clinica.sistema.model.Paciente;
import com.clinica.sistema.model.Medico;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "avaliacao_table")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Paciente paciente;

    @ManyToOne
    @JsonBackReference  // evita loop com @JsonManagedReference do lado do Medico
    private Medico medico;

    private int nota;
    private String comentario;

    public Avaliacao() {}

    public Avaliacao(Paciente paciente, Medico medico, int nota, String comentario) {
        this.paciente = paciente;
        this.medico = medico;
        this.nota = nota;
        this.comentario = comentario;
    }

    // Getters
    public Long getId()              { return id; }
    public Paciente getPaciente()   { return paciente; }
    public Medico getMedico()       { return medico; }
    public int getNota()            { return nota; }
    public String getComentario()   { return comentario; }

    // Setters
    public void setId(Long id)                { this.id = id; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public void setMedico(Medico medico)       { this.medico = medico; }
    public void setNota(int nota)              { this.nota = nota; }
    public void setComentario(String c)        { this.comentario = c; }
}
