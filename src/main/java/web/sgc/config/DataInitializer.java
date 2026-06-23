package web.sgc.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import web.sgc.model.Perfil;
import web.sgc.service.UsuarioService;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner criarUsuariosPadrao(UsuarioService usuarioService) {
        return args -> {
            usuarioService.criarUsuarioInicial("Administrador", "admin", "admin123", Perfil.ADMIN);
            usuarioService.criarUsuarioInicial("Operador", "operador", "operador123", Perfil.OPERADOR);
        };
    }
}
