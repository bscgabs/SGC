package br.com.campeonato.service;

import br.com.campeonato.model.Equipe;
import br.com.campeonato.repository.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EquipeService {

    @Autowired
    private EquipeRepository repository;

    public Page<Equipe> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Equipe salvar(Equipe equipe) {
        return repository.save(equipe);
    }

    public Equipe buscarPorId(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}