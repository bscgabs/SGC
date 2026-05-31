package br.com.campeonato.controller;

import br.com.campeonato.model.Equipe;
import br.com.campeonato.service.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/equipes")
public class EquipeController {

    @Autowired
    private EquipeService service;

    @GetMapping
    public String listar(
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        model.addAttribute(
                "equipes",
                service.listar(PageRequest.of(page, 10)));

        return "equipes/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {

        model.addAttribute("equipe", new Equipe());

        return "equipes/form";
    }

    @PostMapping
    public String salvar(Equipe equipe) {

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
    public String excluir(@PathVariable Long id) {

        service.excluir(id);

        return "redirect:/equipes";
    }
}