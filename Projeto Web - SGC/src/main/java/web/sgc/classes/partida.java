package br.com.campeonato.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "partidas")
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    private Integer placarEquipeA;

    private Integer placarEquipeB;

    private Integer numeroRodada;

    private String local;

    private String status;

    @ManyToOne
    @JoinColumn(name = "campeonato_id")
    private Campeonato campeonato;

    @ManyToOne
    @JoinColumn(name = "equipe_a_id")
    private Equipe equipeA;

    @ManyToOne
    @JoinColumn(name = "equipe_b_id")
    private Equipe equipeB;

    public Partida() {
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getPlacarEquipeA() {
        return placarEquipeA;
    }

    public void setPlacarEquipeA(Integer placarEquipeA) {
        this.placarEquipeA = placarEquipeA;
    }

    public Integer getPlacarEquipeB() {
        return placarEquipeB;
    }

    public void setPlacarEquipeB(Integer placarEquipeB) {
        this.placarEquipeB = placarEquipeB;
    }

    public Integer getNumeroRodada() {
        return numeroRodada;
    }

    public void setNumeroRodada(Integer numeroRodada) {
        this.numeroRodada = numeroRodada;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Campeonato getCampeonato() {
        return campeonato;
    }

    public void setCampeonato(Campeonato campeonato) {
        this.campeonato = campeonato;
    }

    public Equipe getEquipeA() {
        return equipeA;
    }

    public void setEquipeA(Equipe equipeA) {
        this.equipeA = equipeA;
    }

    public Equipe getEquipeB() {
        return equipeB;
    }

    public void setEquipeB(Equipe equipeB) {
        this.equipeB = equipeB;
    }
}