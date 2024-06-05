package Main;

import Entidades.*;
import Logs.LogGenerator;
import Models.*;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.janelas.Janela;
import com.github.britooo.looca.api.group.janelas.JanelaGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.rede.Rede;

import com.github.britooo.looca.api.group.sistema.Sistema;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String[] args) throws IOException {

        System.out.println("""       
                         
                ███╗   ███╗██╗███╗   ██╗██████╗      ██████╗ ██████╗ ██████╗ ███████╗
                ████╗ ████║██║████╗  ██║██╔══██╗    ██╔════╝██╔═══██╗██╔══██╗██╔════╝
                ██╔████╔██║██║██╔██╗ ██║██║  ██║    ██║     ██║   ██║██████╔╝█████╗ \s
                ██║╚██╔╝██║██║██║╚██╗██║██║  ██║    ██║     ██║   ██║██╔══██╗██╔══╝ \s
                ██║ ╚═╝ ██║██║██║ ╚████║██████╔╝    ╚██████╗╚██████╔╝██║  ██║███████╗
                ╚═╝     ╚═╝╚═╝╚═╝  ╚═══╝╚═════╝      ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝
                """);

        Usuario.fazerLogin();
    }

    public static void menu(String fkEmpresa) throws IOException {
        Looca looca = new Looca();

        Rede rede = looca.getRede();
        String hostname = rede.getParametros().getHostName();
        String ipv4 = String.valueOf((looca.getRede()).getGrupoDeInterfaces().getInterfaces().get(4).getEnderecoIpv4());
        Computador computador = new Computador(hostname, ipv4, fkEmpresa);

        boolean maquinaExiste = ComputadorDAO.verificarComputador(computador);

        if (!maquinaExiste){
            ComputadorDAO.cadastrarComputador(computador);
        }

        Integer idMaquina = ComputadorDAO.buscarIdMaquina(computador);

        PersistenciaDeDados persistenciaDeDados01 = new PersistenciaDeDados(0, 0, 0, 0,"", fkEmpresa);
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
                CapturarDados(idMaquina, Integer.valueOf(dadosPersistencia.get(0)), Integer.valueOf(dadosPersistencia.get(1)), Integer.valueOf(dadosPersistencia.get(2)), Integer.valueOf(dadosPersistencia.get(3)), dadosPersistencia.get(4));
            }
        } else {
            System.out.println("Parâmetros para captura dos dados ainda não foram cadastrados...");
            definicaoDeParametros(opcao, fkEmpresa, idMaquina);
        }
    }

    public static void definicaoDeParametros(Integer opcao, String fkEmpresa, Integer fkMaquina) throws IOException {
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
        System.out.print("Memória RAM: ");
        int tempoCapturaRAM = leitor.nextInt();

        System.out.print("Disco: ");
        int tempoCapturaDisco = leitor.nextInt();

        System.out.print("CPU: ");
        int tempoCapturaCPU = leitor.nextInt();

        System.out.print("Janelas: ");
        int tempoCapturaJanelas = leitor.nextInt();

        if (tempoCapturaRAM <= 0 || tempoCapturaDisco <= 0 || tempoCapturaCPU <= 0 || tempoCapturaJanelas <= 0){
            System.out.println("Tempo deve ser maior que 0");
            definicaoDeParametros(opcao, fkEmpresa, fkMaquina);
        }

        PersistenciaDeDados persistenciaDeDados = new PersistenciaDeDados(tempoCapturaRAM, tempoCapturaDisco, tempoCapturaCPU, tempoCapturaJanelas, unidadeTempo, fkEmpresa);

        if (opcao == 1){
            PersistenciaDAO.atualizarPersistencia(persistenciaDeDados);
        } else {
            PersistenciaDAO.cadastrarPersistencia(persistenciaDeDados);
        }

        CapturarDados(fkMaquina, tempoCapturaRAM, tempoCapturaDisco, tempoCapturaCPU, tempoCapturaJanelas, unidadeTempo);
    }

    public static void CapturarDados(Integer fkMaquina, Integer tempoCapturaRAM, Integer tempoCapturaDisco, Integer tempoCapturaCPU, Integer tempoCapturaJanela, String unidadeTempo) throws IOException {
        Looca looca = new Looca();
        Sistema sistema = looca.getSistema();

        LocalDateTime dataHoraAtual = LocalDateTime.now();
        Locale localeBR = new Locale("pt", "BR");
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy HH:mm:ss", localeBR);
        String dataFormatada = dataHoraAtual.format(formato);

        String javaVersion = System.getProperty("java.version");
        String nomeSO = sistema.getSistemaOperacional();
        String versaoSO = System.getProperty("os.version");
        String arqSO = System.getProperty("os.arch");

        String unidadePorExtenso = unidadeTempo.equals("m") ? "Minutos" : "Segundos";

        LogGenerator.gerarLogCaptura("""
                >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                >> Nova sessão de log: %s
                Informações do sistema:
                    Versão do Java = %s
                    Sistema Operacional = %s versão %s rodando na %s
                    Localidade do sistemas = %s
                Parâmetros para captura = Memória RAM: %d %s | Disco: %d %s | CPU: %d %s | Janelas: %d %s
                """.formatted(dataFormatada, javaVersion, nomeSO, versaoSO, arqSO, localeBR, tempoCapturaRAM, unidadePorExtenso, tempoCapturaDisco, unidadePorExtenso, tempoCapturaCPU, unidadePorExtenso, tempoCapturaJanela, unidadePorExtenso));

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        System.out.println();
        System.out.println("------------ Iniciando Captura Dos Dados ------------");

        Runnable taskSO = () -> {
            Long tempoAtivdadeSO = sistema.getTempoDeAtividade() / 3600; // em horas
            SistemaOperacional SO = new SistemaOperacional(nomeSO, tempoAtivdadeSO, fkMaquina);

            try {
                SistemaOperacionalDAO.cadastrarSO(SO);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

        executor.scheduleAtFixedRate(taskSO, 0, 1, TimeUnit.HOURS);

        Runnable taskMemoria = () -> {
            Memoria memoria = looca.getMemoria();
            LocalDateTime momento = LocalDateTime.now();
            DateTimeFormatter formatoSimples = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", localeBR);
            String dataFormatadaSimples = momento.format(formatoSimples);

            // Memória RAM
            Double memoriaUso = Double.valueOf(memoria.getEmUso()) / 1e+9;
            memoriaUso = Math.round(memoriaUso * 20.0) / 20.0;
            Double memoriaDisponivel = Double.valueOf(memoria.getDisponivel()) / 1e+9;
            memoriaDisponivel = Math.round(memoriaDisponivel * 20.0) / 20.0;
            Double memoriaTotal = Double.valueOf(memoria.getTotal()) / 1e+9;
            memoriaTotal = Math.round(memoriaTotal * 20.0) / 20.0;
            System.out.println("------ Mémoria RAM ------");

            MemoriaRam memoriaRam = new MemoriaRam(memoriaUso, memoriaDisponivel, memoriaTotal, fkMaquina);
            try {
                MemoriaRamDAO.cadastrarRAM(memoriaRam);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Double porcentagemMemoria = (memoriaUso / memoriaTotal) * 100;
            porcentagemMemoria = Math.round(porcentagemMemoria * 100.0) / 100.0;

            String hostName = looca.getRede().getParametros().getHostName();

            if (porcentagemMemoria > 70.0) {
                try {
                    LogGenerator.gerarLogCaptura("[ %s ] ALERT %.2f%% da memória RAM utilizada da máquina %s".formatted(dataFormatadaSimples, porcentagemMemoria, hostName));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        };

        if (unidadeTempo.equals("s")) {
            executor.scheduleAtFixedRate(taskMemoria, 0, tempoCapturaRAM, TimeUnit.SECONDS);
        } else {
            executor.scheduleAtFixedRate(taskMemoria, 0, tempoCapturaRAM, TimeUnit.MINUTES);
        }


        Runnable taskDisco = () -> {
            DiscoGrupo disco = looca.getGrupoDeDiscos();
            String hostName = looca.getRede().getParametros().getHostName();
            LocalDateTime momento = LocalDateTime.now();
            DateTimeFormatter formatoSimples = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", localeBR);
            String dataFormatadaSimples = momento.format(formatoSimples);

            // Disco
            double disponivel = 0l;
            double total = 0l;
            double emUso = 0l;
            DecimalFormat df = new DecimalFormat("#.##");
            double porcentagemUso = 0;

            System.out.println("------ Disco ------");

            List<Volume> grupoDiscos = disco.getVolumes();

            for (Volume grupoDisco : grupoDiscos) {
                disponivel = (grupoDisco.getDisponivel() / 1e+6) / 1024;
                total = (grupoDisco.getTotal() / 1e+6) / 1024;
                emUso = total - disponivel;

                porcentagemUso = (emUso / total) * 100;
            }

            Disco disco00 = new Disco(df.format(disponivel), df.format(total), df.format(emUso), fkMaquina);

            try {
                DiscoDAO.cadastrarDisco(disco00);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (porcentagemUso > 80.0) {
                try {
                    LogGenerator.gerarLogCaptura("[ %s ] ALERT %.2f%% do DISCO utilizado da máquina %s".formatted(dataFormatadaSimples, porcentagemUso, hostName));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        if (unidadeTempo.equals("s")){
            executor.scheduleAtFixedRate(taskDisco, 0,tempoCapturaDisco,TimeUnit.SECONDS);
        } else {
            executor.scheduleAtFixedRate(taskDisco, 0,tempoCapturaDisco,TimeUnit.MINUTES);
        }

        Runnable taskCPU = () -> {
            Processador processador = looca.getProcessador();
            LocalDateTime momento = LocalDateTime.now();
            DateTimeFormatter formatoSimples = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", localeBR);
            String dataFormatadaSimples = momento.format(formatoSimples);
            String hostName = looca.getRede().getParametros().getHostName();

            // CPU
            String nomeCpu = processador.getNome();
            Double usoCPU = processador.getUso();
            System.out.println("------ CPU ------");

            Entidades.Processador cpu = new Entidades.Processador(nomeCpu, usoCPU, fkMaquina);

            try {
                ProcessadorDAO.cadastrarCPU(cpu);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (usoCPU > 80.0) {
                try {
                    LogGenerator.gerarLogCaptura("[ %s ] ALERT %.2f%% da CPU utilizada da máquina %s".formatted(dataFormatadaSimples, usoCPU, hostName));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        if (unidadeTempo.equals("s")){
            executor.scheduleAtFixedRate(taskCPU, 0,tempoCapturaCPU,TimeUnit.SECONDS);
        } else {
            executor.scheduleAtFixedRate(taskCPU, 0,tempoCapturaCPU,TimeUnit.MINUTES);
        }

        if (nomeSO.contains("Windows")) {
            Runnable taskJanelas = () -> {

                // JanelasJanelaGrupo
                JanelaGrupo janelas = looca.getGrupoDeJanelas();

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

                    Janelas janela00 = new Janelas(idJanela, titulo, pidJanela, totalJanelas, fkMaquina);

                    try {
                        if (titulo != null && !titulo.isEmpty()) {
                            JanelasDAO.cadastrarJanelas(janela00);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            };

            if (unidadeTempo.equals("s")){
                executor.scheduleAtFixedRate(taskJanelas, 0,tempoCapturaJanela,TimeUnit.SECONDS);
            } else {
                executor.scheduleAtFixedRate(taskJanelas, 0,tempoCapturaJanela,TimeUnit.MINUTES);
            }
        }

//        if (dataHoraAtual.getHour() >= 17){
//            System.out.println("Encerrando aplicação...");
//            LogGenerator.gerarLogCaptura("[ %s ] Aplicação foi encerrada por conta do horário".formatted(dataHoraAtual));
//            executor.shutdown();
//            System.exit(0);
//        }
    }
}