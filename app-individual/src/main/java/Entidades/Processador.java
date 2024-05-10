package Entidades;

public class Processador {
    private String nome;
    private Double emUso;
    private Integer fkMaquina;


    public Processador(String nome, Double emUso, Integer fkMaquina) {
        this.nome = nome;
        this.emUso = emUso;
        this.fkMaquina = fkMaquina;
    }

    public String getNome() {
        return nome;
    }

    public Double getEmUso() {
        return emUso;
    }

    public Integer getFkMaquina() {
        return fkMaquina;
    }

    public void setFkMaquina(Integer fkMaquina) {
        this.fkMaquina = fkMaquina;
    }
}
