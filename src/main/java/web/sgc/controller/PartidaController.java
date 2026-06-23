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
import web.sgc.model.Partida;
import web.sgc.model.StatusPartida;
import web.sgc.service.CampeonatoService;
import web.sgc.service.EquipeService;
import web.sgc.service.PartidaService;

@Controller
@RequestMapping("/partidas")
public class PartidaController {

    private final PartidaService partidaService;
    private final CampeonatoService campeonatoService;
    private final EquipeService equipeService;

    public PartidaController(
            PartidaService partidaService,
            CampeonatoService campeonatoService,
            EquipeService equipeService) {
        this.partidaService = partidaService;
        this.campeonatoService = campeonatoService;
        this.equipeService = equipeService;
    }

    @GetMapping
    public String listar(@RequestParam(defaultValue = "0") int page, Model model) {
        model.addAttribute(
                "partidas",
                partidaService.listar(PageRequest.of(Math.max(page, 0), 10, Sort.by("dataHora").descending())));
        return "partidas/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("partida", new Partida());
        prepararFormulario(model);
        return "partidas/form";
    }

    @PostMapping
    public String salvar(@Valid @ModelAttribute Partida partida, BindingResult result, Model model) {
        if (result.hasErrors()) {
            prepararFormulario(model);
            return "partidas/form";
        }

        try {
            partidaService.salvar(partida);
        } catch (IllegalArgumentException ex) {
            result.reject("partida", ex.getMessage());
            prepararFormulario(model);
            return "partidas/form";
        }

        return "redirect:/partidas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("partida", partidaService.buscarPorId(id));
        prepararFormulario(model);
        return "partidas/form";
    }

    @GetMapping("/excluir/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String excluir(@PathVariable Long id) {
        partidaService.excluir(id);
        return "redirect:/partidas";
    }

    private void prepararFormulario(Model model) {
        model.addAttribute("campeonatos", campeonatoService.listarTodos());
        model.addAttribute("equipes", equipeService.listarTodas());
        model.addAttribute("statusPartida", StatusPartida.values());
    }
}
