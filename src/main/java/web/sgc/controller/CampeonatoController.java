package web.sgc.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.sgc.model.Campeonato;
import web.sgc.service.CampeonatoService;

@Controller
@RequestMapping("/campeonatos")
public class CampeonatoController {

    private final CampeonatoService service;

    public CampeonatoController(CampeonatoService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute(
                "campeonatos",
                service.listar(PageRequest.of(Math.max(page, 0), 10, Sort.by("dataInicio").descending())));
        return "campeonatos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("campeonato", new Campeonato());
        return "campeonatos/form";
    }

    @PostMapping
    public String salvar(
            @Valid @ModelAttribute Campeonato campeonato,
            BindingResult result,
            Authentication authentication) {
        if (result.hasErrors()) {
            return "campeonatos/form";
        }

        try {
            service.salvar(campeonato, authentication.getName());
        } catch (IllegalArgumentException ex) {
            result.reject("campeonato", ex.getMessage());
            return "campeonatos/form";
        }

        return "redirect:/campeonatos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("campeonato", service.buscarPorId(id));
        return "campeonatos/form";
    }

    @GetMapping("/excluir/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/campeonatos";
    }
}
