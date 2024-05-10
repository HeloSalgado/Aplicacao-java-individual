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
import com.github.britooo.looca.api.group.rede.Rede;

import com.github.britooo.looca.api.group.sistema.Sistema;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) {
        System.out.println("""       
                         
                ███╗   ███╗██╗███╗   ██╗██████╗      ██████╗ ██████╗ ██████╗ ███████╗
                ████╗ ████║██║████╗  ██║██╔══██╗    ██╔════╝██╔═══██╗██╔══██╗██╔════╝
                ██╔████╔██║██║██╔██╗ ██║██║  ██║    ██║     ██║   ██║██████╔╝█████╗ \s
                ██║╚██╔╝██║██║██║╚██╗██║██║  ██║    ██║     ██║   ██║██╔══██╗██╔══╝ \s
                ██║ ╚═╝ ██║██║██║ ╚████║██████╔╝    ╚██████╗╚██████╔╝██║  ██║███████╗
                ╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝╚═════╝      ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝
                                                                                    \s               
                """);

        Usuario.FazerLogin();
    }

    public static void Menu() {
        Looca looca = new Looca();

        Rede rede = looca.getRede();
        String hostname = rede.getParametros().getHostName();
        Computador computador = new Computador(hostname);

        boolean maquinaExiste = ComputadorDAO.verificarComputador(computador);
        Integer idMaquina = ComputadorDAO.buscarIdMaquina(computador);
        String fkEmpresa = ComputadorDAO.buscarFkEmpresa(computador);

        if (!maquinaExiste){
            System.out.println("Máquina não está cadastrada");
            System.exit(0);
        }


        PersistenciaDeDados persistenciaDeDados01 = new PersistenciaDeDados(0, 0, 0, 0,0, "", fkEmpresa);
        List<String> dadosPersistencia = PersistenciaDAO.temPersistencia(persistenciaDeDados01);

        int opcao = 0;

        if (dadosPersistencia != null){
            Scanner leitor = new Scanner(System.in);

            System.out.println("Parâmetros de captura já cadastrados!");
            System.out.println("""
                    Deseja atualizar esses parâmetros?
                    1 -> sim
                    2 -> não
                    
                    Digite sua opção: """);
            opcao = leitor.nextInt();

            if (opcao == 1){
                definicaoDeParametros(opcao, fkEmpresa, idMaquina);
            } else {
                CapturarDados(idMaquina, Integer.valueOf(dadosPersistencia.get(0)), Integer.valueOf(dadosPersistencia.get(1)), Integer.valueOf(dadosPersistencia.get(2)), Integer.valueOf(dadosPersistencia.get(3)), Integer.valueOf(dadosPersistencia.get(4)), dadosPersistencia.get(5));
            }
        } else {
            System.out.println("Parâmetros para captura dos dados ainda não foram cadastrados...");
            definicaoDeParametros(opcao, fkEmpresa, idMaquina);
        }
    }

    public static void definicaoDeParametros(Integer opcao, String fkEmpresa, Integer fkMaquina){
        Scanner leitor = new Scanner(System.in);

        System.out.println("""
                Qual a unidade de tempo de captura desejada?
                s -> Segundos
                m -> Minutos
                """);
        String unidadeTempo = leitor.next();

        if (!unidadeTempo.equals("s") && !unidadeTempo.equals("m")){
            System.out.println("Unidade de tempo deve ser segundos ou minutos");
            definicaoDeParametros(opcao, fkEmpresa, fkMaquina);
        }

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
            definicaoDeParametros(opcao, fkEmpresa, fkMaquina);
        }

        PersistenciaDeDados persistenciaDeDados = new PersistenciaDeDados(tempoCapturaSO, tempoCapturaRAM, tempoCapturaDisco, tempoCapturaCPU, tempoCapturaJanelas, unidadeTempo, fkEmpresa);

        if (opcao == 1){
            PersistenciaDAO.atualizarPersistencia(persistenciaDeDados);
        } else {
            PersistenciaDAO.cadastrarPersistencia(persistenciaDeDados);
        }

        CapturarDados(fkMaquina, tempoCapturaSO, tempoCapturaRAM, tempoCapturaDisco, tempoCapturaCPU, tempoCapturaJanelas, unidadeTempo);
    }

    public static void CapturarDados(Integer fkMaquina, Integer tempoCapturaSO, Integer tempoCapturaRAM, Integer tempoCapturaDisco, Integer tempoCapturaCPU, Integer tempoCapturaJanela, String unidadeTempo) {
        Looca looca = new Looca();

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        System.out.println();
        System.out.println("------------ Iniciando Captura Dos Dados ------------");
        Runnable taskSO = () -> {
            Sistema sistema = looca.getSistema();

            // Sistemas Operacional
            String nomeSO = sistema.getSistemaOperacional();
            Long tempoAtivdadeSO = sistema.getTempoDeAtividade() / 3600; // em horas
            System.out.println("------ Sistema Operacional ------");

            SistemaOperacional sistemaOperacional = new SistemaOperacional(nomeSO, tempoAtivdadeSO, fkMaquina);
            SistemaOperacionalDAO.cadastrarSO(sistemaOperacional);
        };

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

            MemoriaRam memoriaRam = new MemoriaRam(memoriaUso, memoriaDisponivel, memoriaTotal, fkMaquina);
            MemoriaRamDAO.cadastrarRAM(memoriaRam);
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
            double tamanho;
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

                Entidades.Disco disco00 = new Entidades.Disco(tamanho, leituras, bytesLeitura, escritas, bytesEscrita, tempoTranferencia, fkMaquina);
                DiscoDAO.cadastrarDisco(disco00);
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

            Entidades.Processador cpu = new Entidades.Processador(nomeCpu, usoCPU, fkMaquina);
            ProcessadorDAO.cadastrarCPU(cpu);
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

            for (Janela janela : janela1) {
                idJanela = janela.getJanelaId();
                titulo = janela.getTitulo();
                pidJanela = janela.getPid();

                Entidades.Janelas janela00 = new Janelas(idJanela, titulo, pidJanela, totalJanelas, fkMaquina);

                if (titulo != null && !titulo.isEmpty()){
                    JanelasDAO.cadastrarJanelas(janela00);
                }
            }
        };

        if (unidadeTempo.equals("s")){
            executor.scheduleAtFixedRate(taskJanelas, 0,tempoCapturaJanela,TimeUnit.SECONDS);
        } else {
            executor.scheduleAtFixedRate(taskJanelas, 0,tempoCapturaJanela,TimeUnit.MINUTES);
        }

        LocalTime horaAtual = LocalTime.now();

        if (horaAtual.getHour() >= 17){
            System.out.println("Encerrando aplicação...");
            executor.shutdown();
            System.exit(0);
        }
    }
}