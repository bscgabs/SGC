package web.sgc.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipes")
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Informe o nome da equipe.")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "Informe a cidade ou local da equipe.")
    @Column(nullable = false, length = 100)
    private String local;

    @OneToMany(mappedBy = "mandante", cascade = CascadeType.REMOVE)
    private List<Partida> partidasComoMandante = new ArrayList<>();

    @OneToMany(mappedBy = "visitante", cascade = CascadeType.REMOVE)
    private List<Partida> partidasComoVisitante = new ArrayList<>();

    public Equipe() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public List<Partida> getPartidasComoMandante() {
        return partidasComoMandante;
    }

    public void setPartidasComoMandante(List<Partida> partidasComoMandante) {
        this.partidasComoMandante = partidasComoMandante;
    }

    public List<Partida> getPartidasComoVisitante() {
        return partidasComoVisitante;
    }

    public void setPartidasComoVisitante(List<Partida> partidasComoVisitante) {
        this.partidasComoVisitante = partidasComoVisitante;
    }
}
