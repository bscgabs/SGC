package br.com.campeonato.repository;

import br.com.campeonato.model.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipeRepository extends JpaRepository<Equipe, Long> {

}