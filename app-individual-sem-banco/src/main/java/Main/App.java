package Main;

import Entidades.*;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.janelas.Janela;
import com.github.britooo.looca.api.group.janelas.JanelaGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.sistema.Sistema;

import java.time.LocalTime;
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

    public static void Menu() {
        Scanner leitor = new Scanner(System.in);

        System.out.println("De quanto em quanto tempo deseja capturar os seguintes dados?");
        System.out.print("Sistema Operacional: ");
        int tempoCapturaSO = leitor.nextInt();

        System.out.print("Memória RAM: ");
        int tempoCapturaRAM = leitor.nextInt();

        System.out.print("Disco: ");
        int tempoCapturaDisco = leitor.nextInt();

        System.out.print("CPU: ");
        int tempoCapturaCPU = leitor.nextInt();

        System.out.print("Janelas: ");
        int tempoCapturaJanelas = leitor.nextInt();

        if (tempoCapturaSO <= 0 || tempoCapturaRAM <= 0 || tempoCapturaDisco <= 0 || tempoCapturaCPU <= 0 || tempoCapturaJanelas <= 0){
            System.out.println("Tempo deve ser maior que 0");
            Menu();
        }

        System.out.println("""
                Qual a unidade de tempo desejada?
                s -> Segundos
                m -> Minutos
                """);
        String unidadeTempo = leitor.next();

        if (!unidadeTempo.equals("s") && !unidadeTempo.equals("m")){
            System.out.println("Unidade de tempo deve ser segundos ou minutos");
            Menu();
        }

        CapturarDados(tempoCapturaSO, tempoCapturaRAM, tempoCapturaDisco, tempoCapturaCPU, tempoCapturaJanelas, unidadeTempo);
    }

    public static void CapturarDados(Integer tempoCapturaSO, Integer tempoCapturaRAM, Integer tempoCapturaDisco, Integer tempoCapturaCPU, Integer tempoCapturaJanela, String unidadeTempo) {
        Looca looca = new Looca();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable taskSO = () -> {
            Sistema sistema = looca.getSistema();

            // Sistemas Operacional
            String nomeSO = sistema.getSistemaOperacional();
            Long tempoAtivdadeSO = sistema.getTempoDeAtividade() / 3600; // em horas
            System.out.println("------ Sistema Operacional ------");
            System.out.println("Nome: " + nomeSO);
            System.out.println("Tempo de atividade: " + tempoAtivdadeSO);        };

        if (unidadeTempo.equals("s")){
            executor.scheduleAtFixedRate(taskSO, 0,tempoCapturaSO,TimeUnit.SECONDS);
        } else {
            executor.scheduleAtFixedRate(taskSO, 0,tempoCapturaSO,TimeUnit.MINUTES);
        }

        Runnable taskMemoria = () -> {
            Memoria memoria = looca.getMemoria();

            // Memória RAM
            Double memoriaUso = Double.valueOf(memoria.getEmUso()) / 1e+9;
            memoriaUso = Math.round(memoriaUso * 20.0) / 20.0;
            Double memoriaDisponivel = Double.valueOf(memoria.getDisponivel()) / 1e+9;
            memoriaDisponivel = Math.round(memoriaDisponivel * 20.0) / 20.0;
            Double memoriaTotal = Double.valueOf(memoria.getTotal()) / 1e+9;
            memoriaTotal = Math.round(memoriaTotal * 20.0) / 20.0;
            System.out.println("------ Mémoria RAM ------");

            System.out.println("Mémoria em uso: " + memoriaUso);
            System.out.println("Mémoria disponível: " + memoriaDisponivel);
            System.out.println("Mémoria total: " + memoriaTotal);
        };

        if (unidadeTempo.equals("s")){
            executor.scheduleAtFixedRate(taskMemoria, 0,tempoCapturaRAM,TimeUnit.SECONDS);
        } else {
            executor.scheduleAtFixedRate(taskMemoria, 0,tempoCapturaRAM,TimeUnit.MINUTES);
        }

        Runnable taskDisco = () -> {
            DiscoGrupo disco = looca.getGrupoDeDiscos();

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

                System.out.println("Tamanha: " + tamanho);
                System.out.println("Leituras: " + leituras);
                System.out.println("Bytes de leitura: " + bytesLeitura);
                System.out.println("Escritas: " + escritas);
                System.out.println("Bytes de escrita: " + bytesEscrita);
                System.out.println("Tempo de transferência: " + tempoTranferencia);
            }
        };

        if (unidadeTempo.equals("s")){
            executor.scheduleAtFixedRate(taskDisco, 0,tempoCapturaDisco,TimeUnit.SECONDS);
        } else {
            executor.scheduleAtFixedRate(taskDisco, 0,tempoCapturaDisco,TimeUnit.MINUTES);
        }

        Runnable taskCPU = () -> {
            Processador processador = looca.getProcessador();

            // CPU
            String nomeCpu = processador.getNome();
            Double usoCPU = processador.getUso();
            System.out.println("------ CPU ------");
            System.out.println("Nome: " + nomeCpu);
            System.out.println("Uso: " + usoCPU);
        };

        if (unidadeTempo.equals("s")){
            executor.scheduleAtFixedRate(taskCPU, 0,tempoCapturaCPU,TimeUnit.SECONDS);
        } else {
            executor.scheduleAtFixedRate(taskCPU, 0,tempoCapturaCPU,TimeUnit.MINUTES);
        }

        Runnable taskJanelas = () -> {
            JanelaGrupo janelas = looca.getGrupoDeJanelas();

            // Janelas
            List<Janela> janela1 = janelas.getJanelas();
            Long idJanela;
            String titulo;
            Long pidJanela;
            Integer totalJanelas = janelas.getTotalJanelas();
            System.out.println("------ Janelas ------");
            System.out.println("Total Janelas: " + totalJanelas);

            for (Janela janela : janela1) {
                idJanela = janela.getJanelaId();
                titulo = janela.getTitulo();
                pidJanela = janela.getPid();

                if (titulo != null && !titulo.isEmpty()){
                    System.out.println("ID: " + idJanela);
                    System.out.println("Título: " + titulo);
                    System.out.println("Pid: " + pidJanela);
                }
            }
        };

        if (unidadeTempo.equals("s")){
            executor.scheduleAtFixedRate(taskJanelas, 0,tempoCapturaJanela,TimeUnit.SECONDS);
        } else {
            executor.scheduleAtFixedRate(taskJanelas, 0,tempoCapturaJanela,TimeUnit.MINUTES);
        }

        LocalTime horaAtual = LocalTime.now();

        if (horaAtual.getHour() > 17){
            executor.shutdown();
            System.exit(0);
        }
    }
}