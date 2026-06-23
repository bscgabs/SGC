package web.sgc.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import web.sgc.model.Perfil;
import web.sgc.model.Usuario;
import web.sgc.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<Usuario> listar(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Transactional
    public Usuario salvar(Usuario usuario, String senhaAberta) {
        Usuario usuarioComMesmoLogin = usuarioRepository.findByLogin(usuario.getLogin()).orElse(null);
        if (usuarioComMesmoLogin != null && !usuarioComMesmoLogin.getId().equals(usuario.getId())) {
            throw new IllegalArgumentException("Ja existe um usuario com esse login.");
        }

        Usuario destino = usuario.getId() == null
                ? new Usuario()
                : buscarPorId(usuario.getId());

        destino.setNome(usuario.getNome());
        destino.setLogin(usuario.getLogin());
        destino.setPerfil(usuario.getPerfil());

        if (StringUtils.hasText(senhaAberta)) {
            destino.setSenha(passwordEncoder.encode(senhaAberta));
        } else if (destino.getSenha() == null) {
            throw new IllegalArgumentException("Informe a senha do novo usuario.");
        }

        return usuarioRepository.save(destino);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario nao encontrado."));
    }

    public void excluir(Long id) {
        usuarioRepository.deleteById(id);
    }

    public void criarUsuarioInicial(String nome, String login, String senha, Perfil perfil) {
        if (!usuarioRepository.existsByLogin(login)) {
            usuarioRepository.save(new Usuario(nome, login, passwordEncoder.encode(senha), perfil));
        }
    }
}
