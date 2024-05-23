package Logs;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogGenerator {
    public static void gerarLogCaptura(String mensagem) throws IOException {
        Path path = Paths.get("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs");

        if (!Files.exists(path)){
            Files.createDirectory(path);
        }

        int maximoLinhas = 2000;
        int fileIndex = 1;
        File log = new File("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs\\logs_captura" + fileIndex + ".txt");

        // Encontrar o arquivo de log que não excede o máximo de linhas
        while (log.exists() && contarLinhas(log) >= maximoLinhas) {
            fileIndex++;
            log = new File("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs\\logs_captura" + fileIndex + ".txt");
        }

        if (!log.exists()) {
            log.createNewFile();
        }

        FileWriter fw = new FileWriter(log, true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(mensagem);
        bw.newLine();

        bw.close();
        fw.close();
    }

    public static void gerarLogUsuarios(String mensagem) throws IOException {
        Path path = Paths.get("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs");

        if (!Files.exists(path)){
            Files.createDirectory(path);
        }

        int maximoLinhas = 2000;
        int fileIndex = 1;
        File logUser = new File("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs\\logs_usuarios" + fileIndex + ".txt");

        // Encontrar o arquivo de log que não excede o máximo de linhas
        while (logUser.exists() && contarLinhas(logUser) >= maximoLinhas) {
            fileIndex++;
            logUser = new File("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs\\logs_usuarios" + fileIndex + ".txt");
        }

        if (!logUser.exists()) {
            logUser.createNewFile();
        }

        FileWriter fw = new FileWriter(logUser, true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(mensagem);
        bw.newLine();

        bw.close();
        fw.close();
    }

    public static void gerarLogErros(String mensagem) throws IOException {
        Path path = Paths.get("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs");

        if (!Files.exists(path)){
            Files.createDirectory(path);
        }

        int maximoLinhas = 2000;
        int fileIndex = 1;
        File logErro = new File("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs\\logs_erros" + fileIndex + ".txt");

        // Encontrar o arquivo de log que não excede o máximo de linhas
        while (logErro.exists() && contarLinhas(logErro) >= maximoLinhas) {
            fileIndex++;
            logErro = new File("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs\\logs_erros" + fileIndex + ".txt");
        }
        if (!logErro.exists()) {
            logErro.createNewFile();
        }

        FileWriter fw = new FileWriter(logErro, true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(mensagem);
        bw.newLine();

        bw.close();
        fw.close();
    }

    public static void gerarLogBD(String mensagem) throws IOException {
        Path path = Paths.get("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs");

        if (!Files.exists(path)){
            Files.createDirectory(path);
        }

        int maximoLinhas = 2000;
        int fileIndex = 1;
        File logBD = new File("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs\\logs_bancodedados" + fileIndex + ".txt");

        // Encontrar o arquivo de log que não excede o máximo de linhas
        while (logBD.exists() && contarLinhas(logBD) >= maximoLinhas) {
            fileIndex++;
            logBD = new File("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs\\logs_bancodedados" + fileIndex + ".txt");
        }
        if (!logBD.exists()) {
            logBD.createNewFile();
        }

        FileWriter fw = new FileWriter(logBD, true);
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
