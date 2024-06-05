package Logs;

import com.github.britooo.looca.api.core.Looca;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogGenerator {
    private static final Looca looca = new Looca();
    private static final String nomeSO = looca.getSistema().getSistemaOperacional();
    private static final Path pathWindows = Paths.get("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs");
    private static final Path pathLinux = Paths.get("/home/heloisa/Aplicacao-java-individual/logs");

    public static String dataFormatada() {
        LocalDateTime momento = LocalDateTime.now();
        DateTimeFormatter formatoSimples = DateTimeFormatter.ofPattern("yyyyMMdd");
        return momento.format(formatoSimples);
    }

    public static void gerarLogCaptura(String mensagem) throws IOException {

        if (nomeSO.contains("Windows")){
            if (!Files.exists(pathWindows)){
                Files.createDirectory(pathWindows);
            }
        } else {
            if (!Files.exists(pathLinux)){
                Files.createDirectory(pathLinux);
            }
        }

        int maximoLinhas = 2000;
        int fileIndex = 1;

        File log = new File("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs\\logs_captura" + dataFormatada() + fileIndex + ".txt");
        File logLinux = new File("/home/heloisa/Aplicacao-java-individual/logs/logs_captura" + dataFormatada() + fileIndex + ".txt");

        if (nomeSO.contains("Windows")){
            // Encontrar o arquivo de log que não excede o máximo de linhas
            while (log.exists() && contarLinhas(log) >= maximoLinhas) {
                fileIndex++;
                log = new File("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs\\logs_captura" + dataFormatada() + fileIndex + ".txt");
            }
        } else {
            while (logLinux.exists() && contarLinhas(logLinux) >= maximoLinhas) {
                fileIndex++;
                logLinux = new File("/home/heloisa/Aplicacao-java-individual/logs/logs_captura" + dataFormatada() + fileIndex + ".txt");
            }
        }

        if (nomeSO.contains("Windows")){
            if (!log.exists()) {
                log.createNewFile();
            }
        } else {
            if (!logLinux.exists()) {
                logLinux.createNewFile();
            }
        }

        FileWriter fw;

        if (nomeSO.contains("Windows")){
            fw = new FileWriter(log, true);

        } else {
            fw = new FileWriter(logLinux, true);
        }

        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(mensagem);
        bw.newLine();
        bw.close();
        fw.close();
    }

    public static void gerarLogUsuarios(String mensagem) throws IOException {

        if (nomeSO.contains("Windows")){
            if (!Files.exists(pathWindows)){
                Files.createDirectory(pathWindows);
            }
        } else {
            if (!Files.exists(pathLinux)){
                Files.createDirectory(pathLinux);
            }
        }

        int maximoLinhas = 2000;
        int fileIndex = 1;
        File logUser = new File("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs\\logs_usuarios" + dataFormatada() + fileIndex + ".txt");
        File logLinux = new File("/home/heloisa/Aplicacao-java-individual/logs/logs_usuarios" + dataFormatada() + fileIndex + ".txt");

        if (nomeSO.contains("Windows")){
            // Encontrar o arquivo de log que não excede o máximo de linhas
            while (logUser.exists() && contarLinhas(logUser) >= maximoLinhas) {
                fileIndex++;
                logUser = new File("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs\\logs_usuarios" + dataFormatada() + fileIndex + ".txt");
            }
        } else {
            while (logLinux.exists() && contarLinhas(logLinux) >= maximoLinhas) {
                fileIndex++;
                logLinux = new File("/home/heloisa/Aplicacao-java-individual/logs/logs_usuarios" + dataFormatada() + fileIndex + ".txt");
            }
        }

        if (nomeSO.contains("Windows")){
            if (!logUser.exists()) {
                logUser.createNewFile();
            }
        } else {
            if (!logLinux.exists()) {
                logLinux.createNewFile();
            }
        }

        FileWriter fw;

        if (nomeSO.contains("Windows")){
            fw = new FileWriter(logUser, true);

        } else {
            fw = new FileWriter(logLinux, true);
        }

        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(mensagem);
        bw.newLine();
        bw.close();
        fw.close();
    }

    public static void gerarLogBD(String mensagem) throws IOException {

        if (nomeSO.contains("Windows")){
            if (!Files.exists(pathWindows)){
                Files.createDirectory(pathWindows);
            }
        } else {
            if (!Files.exists(pathLinux)){
                Files.createDirectory(pathLinux);
            }
        }

        int maximoLinhas = 2000;
        int fileIndex = 1;
        File logBD = new File("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs\\logs_bancodedados" + dataFormatada() + fileIndex + ".txt");
        File logLinux = new File("/home/heloisa/Aplicacao-java-individual/logs/logs_bancodedados" + dataFormatada() + fileIndex + ".txt");

        if (nomeSO.contains("Windows")){
            // Encontrar o arquivo de log que não excede o máximo de linhas
            while (logBD.exists() && contarLinhas(logBD) >= maximoLinhas) {
                fileIndex++;
                logBD = new File("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs\\logs_bancodedados" + dataFormatada() + fileIndex + ".txt");
            }
        } else {
            while (logLinux.exists() && contarLinhas(logLinux) >= maximoLinhas) {
                fileIndex++;
                logLinux = new File("/home/heloisa/Aplicacao-java-individual/logs/logs_bancodedados" + dataFormatada() + fileIndex + ".txt");
            }
        }

        if (nomeSO.contains("Windows")){
            if (!logBD.exists()) {
                logBD.createNewFile();
            }
        } else {
            if (!logLinux.exists()) {
                logLinux.createNewFile();
            }
        }

        FileWriter fw;

        if (nomeSO.contains("Windows")){
            fw = new FileWriter(logBD, true);

        } else {
            fw = new FileWriter(logLinux, true);
        }

        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(mensagem);
        bw.newLine();
        bw.close();
        fw.close();
    }

    private static int contarLinhas(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        int linhas = 0;
        while (reader.readLine() != null) {
            linhas++;
        }
        reader.close();
        return linhas;
    }
}
