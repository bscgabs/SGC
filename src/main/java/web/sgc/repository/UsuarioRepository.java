package web.sgc.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import web.sgc.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByLogin(String login);

    boolean existsByLogin(String login);
}
