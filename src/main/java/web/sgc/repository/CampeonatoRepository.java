package web.sgc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import web.sgc.model.Campeonato;

public interface CampeonatoRepository extends JpaRepository<Campeonato, Long> {

    @Override
    @EntityGraph(attributePaths = "responsavel")
    Page<Campeonato> findAll(Pageable pageable);
}
