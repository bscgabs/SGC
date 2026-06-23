package web.sgc.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.sgc.model.Campeonato;
import web.sgc.model.Equipe;
import web.sgc.model.Partida;
import web.sgc.model.StatusPartida;
import web.sgc.repository.CampeonatoRepository;
import web.sgc.repository.EquipeRepository;
import web.sgc.repository.PartidaRepository;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final CampeonatoRepository campeonatoRepository;
    private final EquipeRepository equipeRepository;

    public PartidaService(
            PartidaRepository partidaRepository,
            CampeonatoRepository campeonatoRepository,
            EquipeRepository equipeRepository) {
        this.partidaRepository = partidaRepository;
        this.campeonatoRepository = campeonatoRepository;
        this.equipeRepository = equipeRepository;
    }

    public Page<Partida> listar(Pageable pageable) {
        return partidaRepository.findAll(pageable);
    }

    public List<Partida> listarPorCampeonato(Long campeonatoId) {
        return partidaRepository.findByCampeonatoIdOrderByNumeroRodadaAscDataHoraAsc(campeonatoId);
    }

    public List<Partida> listarFinalizadasPorCampeonato(Long campeonatoId) {
        return partidaRepository.findByCampeonatoIdAndStatusOrderByNumeroRodadaAscDataHoraAsc(
                campeonatoId,
                StatusPartida.FINALIZADA);
    }

    @Transactional
    public Partida salvar(Partida partida) {
        Long campeonatoId = partida.getCampeonato() == null ? null : partida.getCampeonato().getId();
        Long mandanteId = partida.getMandante() == null ? null : partida.getMandante().getId();
        Long visitanteId = partida.getVisitante() == null ? null : partida.getVisitante().getId();

        if (campeonatoId == null || mandanteId == null || visitanteId == null) {
            throw new IllegalArgumentException("Selecione campeonato, mandante e visitante.");
        }

        Campeonato campeonato = campeonatoRepository.findById(campeonatoId)
                .orElseThrow(() -> new IllegalArgumentException("Selecione um campeonato valido."));
        Equipe mandante = equipeRepository.findById(mandanteId)
                .orElseThrow(() -> new IllegalArgumentException("Selecione a equipe mandante."));
        Equipe visitante = equipeRepository.findById(visitanteId)
                .orElseThrow(() -> new IllegalArgumentException("Selecione a equipe visitante."));

        validar(partida, campeonato, mandante, visitante);

        Partida destino = partida.getId() == null
                ? new Partida()
                : buscarPorId(partida.getId());

        destino.setCampeonato(campeonato);
        destino.setMandante(mandante);
        destino.setVisitante(visitante);
        destino.setDataHora(partida.getDataHora());
        destino.setNumeroRodada(partida.getNumeroRodada());
        destino.setLocal(partida.getLocal());
        destino.setStatus(partida.getStatus());
        destino.setPlacarMandante(partida.getPlacarMandante());
        destino.setPlacarVisitante(partida.getPlacarVisitante());

        return partidaRepository.save(destino);
    }

    public Partida buscarPorId(Long id) {
        return partidaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Partida nao encontrada."));
    }

    public void excluir(Long id) {
        partidaRepository.deleteById(id);
    }

    private void validar(Partida partida, Campeonato campeonato, Equipe mandante, Equipe visitante) {
        if (mandante.getId().equals(visitante.getId())) {
            throw new IllegalArgumentException("A partida precisa ter duas equipes diferentes.");
        }

        if (partida.getStatus() == null) {
            throw new IllegalArgumentException("Informe o status da partida.");
        }

        if (partida.getDataHora() != null) {
            LocalDate dataPartida = partida.getDataHora().toLocalDate();
            if (dataPartida.isBefore(campeonato.getDataInicio()) || dataPartida.isAfter(campeonato.getDataFim())) {
                throw new IllegalArgumentException("A partida deve acontecer dentro do periodo do campeonato.");
            }
        }
    }
}
