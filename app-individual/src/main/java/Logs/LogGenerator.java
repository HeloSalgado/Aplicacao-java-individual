package Logs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LogGenerator {
    public static void gerarLogCaptura(String mensagem) throws IOException {
        Path path = Paths.get("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs");

        if (!Files.exists(path)){
            Files.createDirectory(path);
        }

        File log = new File("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs\\logs.txt");

        if (!log.exists()) {
            log.createNewFile();
        }

        int maximoLinhas = 100; // Número máximo de linhas antes de criar um novo arquivo
        int contadorLinhas = 0;

        FileWriter fw = new FileWriter(log, true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(mensagem);
        bw.newLine();

        contadorLinhas++;

        if (contadorLinhas >= maximoLinhas) {
            bw.close();
            fw.close();

            int fileIndex = 1;
            File newLogFile = new File("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs\\logs_" + fileIndex + ".txt");
            while (newLogFile.exists()) {
                fileIndex++;
                newLogFile = new File("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs\\logs_" + fileIndex + ".txt");
            }

            // Cria o novo arquivo de log
            newLogFile.createNewFile();

            // Atualiza o contador de linhas e o arquivo de log atual
            contadorLinhas = 0;
            log = newLogFile;
            fw = new FileWriter(log, true);
            bw = new BufferedWriter(fw);
        }
    }

    public static void gerarLogUsuarios(String mensagem) throws IOException {
        Path path = Paths.get("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs");

        if (!Files.exists(path)){
            Files.createDirectory(path);
        }

        File logUser = new File("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs\\logsUsuarios.txt");

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

        File logErro = new File("C:\\Users\\helos\\OneDrive - SPTech School\\2º Semestre\\Pesquisa e Inovação II\\Aplicacao-java-individual\\logs\\logsErros.txt");

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
}
