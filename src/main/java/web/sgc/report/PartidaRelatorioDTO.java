package web.sgc.report;

public class PartidaRelatorioDTO {

    private final String rodada;
    private final String dataHora;
    private final String confronto;
    private final String placar;
    private final String local;
    private final String status;

    public PartidaRelatorioDTO(
            String rodada,
            String dataHora,
            String confronto,
            String placar,
            String local,
            String status) {
        this.rodada = rodada;
        this.dataHora = dataHora;
        this.confronto = confronto;
        this.placar = placar;
        this.local = local;
        this.status = status;
    }

    public String getRodada() {
        return rodada;
    }

    public String getDataHora() {
        return dataHora;
    }

    public String getConfronto() {
        return confronto;
    }

    public String getPlacar() {
        return placar;
    }

    public String getLocal() {
        return local;
    }

    public String getStatus() {
        return status;
    }
}
