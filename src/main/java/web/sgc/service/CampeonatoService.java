package web.sgc.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.sgc.model.Campeonato;
import web.sgc.model.Usuario;
import web.sgc.repository.CampeonatoRepository;
import web.sgc.repository.UsuarioRepository;

@Service
public class CampeonatoService {

    private final CampeonatoRepository campeonatoRepository;
    private final UsuarioRepository usuarioRepository;

    public CampeonatoService(CampeonatoRepository campeonatoRepository, UsuarioRepository usuarioRepository) {
        this.campeonatoRepository = campeonatoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Page<Campeonato> listar(Pageable pageable) {
        return campeonatoRepository.findAll(pageable);
    }

    public List<Campeonato> listarTodos() {
        return campeonatoRepository.findAll();
    }

    @Transactional
    public Campeonato salvar(Campeonato campeonato, String loginResponsavel) {
        validarPeriodo(campeonato.getDataInicio(), campeonato.getDataFim());

        Campeonato destino = campeonato.getId() == null
                ? new Campeonato()
                : buscarPorId(campeonato.getId());

        destino.setNome(campeonato.getNome());
        destino.setDataInicio(campeonato.getDataInicio());
        destino.setDataFim(campeonato.getDataFim());

        if (destino.getResponsavel() == null) {
            Usuario responsavel = usuarioRepository.findByLogin(loginResponsavel)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario responsavel nao encontrado."));
            destino.setResponsavel(responsavel);
        }

        return campeonatoRepository.save(destino);
    }

    public Campeonato buscarPorId(Long id) {
        return campeonatoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Campeonato nao encontrado."));
    }

    public void excluir(Long id) {
        campeonatoRepository.deleteById(id);
    }

    private void validarPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio != null && fim != null && fim.isBefore(inicio)) {
            throw new IllegalArgumentException("A data de fim nao pode ser anterior a data de inicio.");
        }
    }
}
