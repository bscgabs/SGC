package web.sgc.service;

import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.sgc.model.Campeonato;
import web.sgc.model.Partida;
import web.sgc.report.CampeonatoRelatorioDTO;
import web.sgc.report.PartidaRelatorioDTO;

@Service
public class RelatorioService {

    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final CampeonatoService campeonatoService;
    private final PartidaService partidaService;
    private final ResourceLoader resourceLoader;

    public RelatorioService(
            CampeonatoService campeonatoService,
            PartidaService partidaService,
            ResourceLoader resourceLoader) {
        this.campeonatoService = campeonatoService;
        this.partidaService = partidaService;
        this.resourceLoader = resourceLoader;
    }

    @Transactional(readOnly = true)
    public byte[] gerarCampeonatoPdf(Long campeonatoId) {
        Campeonato campeonato = campeonatoService.buscarPorId(campeonatoId);
        List<PartidaRelatorioDTO> partidas = partidaService.listarFinalizadasPorCampeonato(campeonatoId)
                .stream()
                .map(this::toPartidaRelatorio)
                .toList();

        CampeonatoRelatorioDTO dto = new CampeonatoRelatorioDTO(
                campeonato.getNome(),
                campeonato.getDataInicio().format(DATA),
                campeonato.getDataFim().format(DATA),
                campeonato.getResponsavel().getNome(),
                partidas);

        try (
                InputStream relatorio = resourceLoader.getResource("classpath:reports/campeonato_report.jrxml").getInputStream();
                InputStream subrelatorio = resourceLoader.getResource("classpath:reports/partidas_subreport.jrxml").getInputStream()) {

            JasperReport relatorioCompilado = JasperCompileManager.compileReport(relatorio);
            JasperReport subrelatorioCompilado = JasperCompileManager.compileReport(subrelatorio);
            Map<String, Object> parametros = Map.of("PARTIDAS_SUBREPORT", subrelatorioCompilado);
            JRBeanCollectionDataSource dados = new JRBeanCollectionDataSource(List.of(dto));
            JasperPrint print = JasperFillManager.fillReport(relatorioCompilado, parametros, dados);
            return JasperExportManager.exportReportToPdf(print);
        } catch (Exception ex) {
            throw new IllegalStateException("Nao foi possivel gerar o relatorio PDF.", ex);
        }
    }

    private PartidaRelatorioDTO toPartidaRelatorio(Partida partida) {
        return new PartidaRelatorioDTO(
                String.valueOf(partida.getNumeroRodada()),
                partida.getDataHora().format(DATA_HORA),
                partida.getMandante().getNome() + " x " + partida.getVisitante().getNome(),
                formatarPlacar(partida),
                partida.getLocal(),
                partida.getStatus().getDescricao());
    }

    private String formatarPlacar(Partida partida) {
        if (partida.getPlacarMandante() == null || partida.getPlacarVisitante() == null) {
            return "-";
        }
        return partida.getPlacarMandante() + " x " + partida.getPlacarVisitante();
    }
}
