package web.sgc.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.sgc.model.Equipe;
import web.sgc.service.EquipeService;

@Controller
@RequestMapping("/equipes")
public class EquipeController {

    private final EquipeService service;

    public EquipeController(EquipeService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        model.addAttribute(
                "equipes",
                service.listar(PageRequest.of(Math.max(page, 0), 10, Sort.by("nome"))));

        return "equipes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {

        model.addAttribute("equipe", new Equipe());

        return "equipes/form";
    }

    @PostMapping
    public String salvar(@Valid Equipe equipe, BindingResult result) {
        if (result.hasErrors()) {
            return "equipes/form";
        }

        service.salvar(equipe);

        return "redirect:/equipes";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "equipe",
                service.buscarPorId(id));

        return "equipes/form";
    }

    @GetMapping("/excluir/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String excluir(@PathVariable Long id) {

        service.excluir(id);

        return "redirect:/equipes";
    }
}
