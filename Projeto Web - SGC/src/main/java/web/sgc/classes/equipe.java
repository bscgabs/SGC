package br.com.campeonato.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "equipes")
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String local;

    @OneToMany(mappedBy = "equipeA")
    private List<Partida> partidasComoEquipeA;

    @OneToMany(mappedBy = "equipeB")
    private List<Partida> partidasComoEquipeB;

    public Equipe() {
    }

    public Long getId() {
        return id;
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

    public List<Partida> getPartidasComoEquipeA() {
        return partidasComoEquipeA;
    }

    public void setPartidasComoEquipeA(List<Partida> partidasComoEquipeA) {
        this.partidasComoEquipeA = partidasComoEquipeA;
    }

    public List<Partida> getPartidasComoEquipeB() {
        return partidasComoEquipeB;
    }

    public void setPartidasComoEquipeB(List<Partida> partidasComoEquipeB) {
        this.partidasComoEquipeB = partidasComoEquipeB;
    }
}