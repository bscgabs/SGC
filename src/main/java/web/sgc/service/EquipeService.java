package web.sgc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import web.sgc.model.Equipe;
import web.sgc.repository.EquipeRepository;

@Service
public class EquipeService {

    private final EquipeRepository repository;

    public EquipeService(EquipeRepository repository) {
        this.repository = repository;
    }

    public Page<Equipe> listar(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public java.util.List<Equipe> listarTodas() {
        return repository.findAll();
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
