package Main;

import Entidades.*;
import Models.*;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.janelas.Janela;
import com.github.britooo.looca.api.group.janelas.JanelaGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.sistema.Sistema;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) {
        System.out.println("""
                Seja Bem Vindo(a) a
                
                **      **  **  **   **  *******         ********  **********  *********    *********
                ** **** **  **  ***  **  **     **      **         **      **  **      **   **
                **  **  **  **  ** * **  **      **    **          **      **  *********    *****
                **      **  **  **  ***  **     **      **         **      **  **   **      **
                **      **  **  **   **  *******         ********  **********  **     **    *********
                
                """);

        Usuario.FazerLogin();
    }

    public static void Menu(){
        Scanner leitor = new Scanner(System.in);
        List<Integer> componentes = new ArrayList<>();
        System.out.println("""
                Quais componentes deseja capturar?
                1 - Sistema
                2 - Memória
                3 - Disco
                4 - Processador
                5 - Janelas
                
                0 - Terminar seleção
                """);

        int componente;
        System.out.println("Digite suas opções: ");
        while ((componente = leitor.nextInt()) != 0) {
            componentes.add(componente);
        }

        CapturarDados(componentes);
    }

    public static void CapturarDados(@NotNull List<Integer> componentes){
        Looca looca = new Looca();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
        Sistema sistema = looca.getSistema();
        Memoria memoria = looca.getMemoria();
        DiscoGrupo disco = looca.getGrupoDeDiscos();
        Processador processador = looca.getProcessador();
        JanelaGrupo janelas = looca.getGrupoDeJanelas();

        for (int i = 0; i < componentes.size(); i++) {
            switch (componentes.get(i)){
                case 1 -> {
                    // Sistemas Operacional
                    String nomeSO = sistema.getSistemaOperacional();
                    Long tempoAtivdadeSO = sistema.getTempoDeAtividade() / 3600; // em horas
                    System.out.println("------ Sistema Operacional ------");

                    SistemaOperacional sistemaOperacional = new SistemaOperacional(nomeSO, tempoAtivdadeSO);
                    SistemaOperacionalDAO.cadastrarSO(sistemaOperacional);
                }
                case 2 -> {
                    // Memória RAM
                    Double memoriaUso = Double.valueOf(memoria.getEmUso()) / 1e+9;
                    memoriaUso = Math.round(memoriaUso * 20.0) / 20.0;
                    Double memoriaDisponivel = Double.valueOf(memoria.getDisponivel()) / 1e+9;
                    memoriaDisponivel = Math.round(memoriaDisponivel * 20.0) / 20.0;
                    Double memoriaTotal = Double.valueOf(memoria.getTotal()) / 1e+9;
                    memoriaTotal = Math.round(memoriaTotal * 20.0) / 20.0;
                    System.out.println("------ Mémoria RAM ------");

                    MemoriaRam memoriaRam = new MemoriaRam(memoriaUso, memoriaDisponivel, memoriaTotal);
                    MemoriaRamDAO.cadastrarRAM(memoriaRam);
                }
                case 3 -> {
                    // Disco
                    List<Disco> discos = disco.getDiscos();
                    Double tamanho;
                    Double leituras;
                    Double bytesLeitura;
                    Double escritas;
                    Double bytesEscrita;
                    Long tempoTranferencia;
                    System.out.println("------ Disco ------");

                    for (Disco disco1 : discos) {
                        tamanho = disco1.getTamanho() / 1e+9;
                        leituras = Double.valueOf(disco1.getLeituras());
                        bytesLeitura = Double.valueOf(disco1.getBytesDeLeitura());
                        escritas = Double.valueOf(disco1.getEscritas());
                        bytesEscrita = Double.valueOf(disco1.getBytesDeEscritas());
                        tempoTranferencia = disco1.getTempoDeTransferencia();

                        Entidades.Disco disco00 = new Entidades.Disco(tamanho, leituras, bytesLeitura, escritas, bytesEscrita, tempoTranferencia);
                        DiscoDAO.cadastrarDisco(disco00);
                    }
                }
                case 4 -> {
                    // CPU
                    String nomeCpu = processador.getNome();
                    Double usoCPU = processador.getUso();
                    System.out.println("------ CPU ------");

                    Entidades.Processador cpu = new Entidades.Processador(nomeCpu, usoCPU);
                    ProcessadorDAO.cadastrarCPU(cpu);
                }
                case 5 -> {
                    // Janelas
                    List<Janela> janela1 = janelas.getJanelas();
                    Long idJanela;
                    String titulo;
                    Long pidJanela;
                    Integer totalJanelas = janelas.getTotalJanelas();
                    System.out.println("------ Janelas ------");

                    for (Janela janela : janela1) {
                        idJanela = janela.getJanelaId();
                        titulo = janela.getTitulo();
                        pidJanela = janela.getPid();

                        Entidades.Janelas janela00 = new Janelas(idJanela, titulo, pidJanela, totalJanelas);
                        JanelasDAO.cadastrarJanelas(janela00);
                    }
                }
            }
        }
        };

        executor.scheduleAtFixedRate(task, 0, 10, TimeUnit.SECONDS);

    }
}
