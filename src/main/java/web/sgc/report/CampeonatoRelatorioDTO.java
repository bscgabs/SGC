package web.sgc.report;

import java.util.Collection;

public class CampeonatoRelatorioDTO {

    private final String nome;
    private final String dataInicio;
    private final String dataFim;
    private final String responsavel;
    private final Collection<PartidaRelatorioDTO> partidas;

    public CampeonatoRelatorioDTO(
            String nome,
            String dataInicio,
            String dataFim,
            String responsavel,
            Collection<PartidaRelatorioDTO> partidas) {
        this.nome = nome;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.responsavel = responsavel;
        this.partidas = partidas;
    }

    public String getNome() {
        return nome;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public Collection<PartidaRelatorioDTO> getPartidas() {
        return partidas;
    }
}
