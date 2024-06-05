package Entidades;

public class Disco {
    private final String disponivel;
    private final String total;
    private final String emUso;
    private final Integer fkMaquina;

    public Disco(String disponivel, String total, String emUso, Integer fkMaquina) {
        this.disponivel = disponivel;
        this.total = total;
        this.emUso = emUso;
        this.fkMaquina = fkMaquina;
    }

    public String getDisponivel() {
        return disponivel;
    }

    public String getTotal() {
        return total;
    }

    public String getEmUso() {
        return emUso;
    }

    public Integer getFkMaquina() {
        return fkMaquina;
    }
}
