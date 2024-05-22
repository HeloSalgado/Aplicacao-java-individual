package Entidades;

public class PersistenciaDeDados {
    private Integer tempoRAM;
    private Integer tempoDisco;
    private Integer tempoCPU;
    private Integer tempoJanelas;
    private String unidadeTempo;
    private String fkEmpresa;

    public PersistenciaDeDados(Integer tempoRAM, Integer tempoDisco, Integer tempoCPU, Integer tempoJanelas, String unidadeTempo, String fkEmpresa) {
        this.tempoRAM = tempoRAM;
        this.tempoDisco = tempoDisco;
        this.tempoCPU = tempoCPU;
        this.tempoJanelas = tempoJanelas;
        this.unidadeTempo = unidadeTempo;
        this.fkEmpresa = fkEmpresa;
    }

    public Integer getTempoRAM() {
        return tempoRAM;
    }

    public void setTempoRAM(Integer tempoRAM) {
        this.tempoRAM = tempoRAM;
    }

    public Integer getTempoDisco() {
        return tempoDisco;
    }

    public void setTempoDisco(Integer tempoDisco) {
        this.tempoDisco = tempoDisco;
    }

    public Integer getTempoCPU() {
        return tempoCPU;
    }

    public void setTempoCPU(Integer tempoCPU) {
        this.tempoCPU = tempoCPU;
    }

    public Integer getTempoJanelas() {
        return tempoJanelas;
    }

    public void setTempoJanelas(Integer tempoJanelas) {
        this.tempoJanelas = tempoJanelas;
    }

    public String getUnidadeTempo() {
        return unidadeTempo;
    }

    public void setUnidadeTempo(String unidadeTempo) {
        this.unidadeTempo = unidadeTempo;
    }

    public String getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(String fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }
}
