package web.sgc.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.sgc.model.Perfil;
import web.sgc.model.Usuario;
import web.sgc.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute("usuarios", service.listar(PageRequest.of(Math.max(page, 0), 10, Sort.by("nome"))));
        return "usuarios/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("perfis", Perfil.values());
        return "usuarios/form";
    }

    @PostMapping
    public String salvar(
            @Valid @ModelAttribute Usuario usuario,
            BindingResult result,
            @RequestParam(required = false) String novaSenha,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("perfis", Perfil.values());
            return "usuarios/form";
        }

        try {
            service.salvar(usuario, novaSenha);
        } catch (IllegalArgumentException ex) {
            result.reject("usuario", ex.getMessage());
            model.addAttribute("perfis", Perfil.values());
            return "usuarios/form";
        }

        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", service.buscarPorId(id));
        model.addAttribute("perfis", Perfil.values());
        return "usuarios/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/usuarios";
    }
}
