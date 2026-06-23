package web.sgc.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import web.sgc.model.Partida;
import web.sgc.model.StatusPartida;

public interface PartidaRepository extends JpaRepository<Partida, Long> {

    @Override
    @EntityGraph(attributePaths = {"campeonato", "mandante", "visitante"})
    Page<Partida> findAll(Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"campeonato", "mandante", "visitante"})
    Optional<Partida> findById(Long id);

    Page<Partida> findByCampeonatoId(Long campeonatoId, Pageable pageable);

    @EntityGraph(attributePaths = {"campeonato", "mandante", "visitante"})
    List<Partida> findByCampeonatoIdOrderByNumeroRodadaAscDataHoraAsc(Long campeonatoId);

    @EntityGraph(attributePaths = {"campeonato", "mandante", "visitante"})
    List<Partida> findByCampeonatoIdAndStatusOrderByNumeroRodadaAscDataHoraAsc(
            Long campeonatoId,
            StatusPartida status);
}
