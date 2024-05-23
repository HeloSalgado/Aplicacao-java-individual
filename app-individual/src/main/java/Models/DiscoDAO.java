package Models;

import Conexao.Conexao;
import Entidades.Disco;
import Logs.LogGenerator;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DiscoDAO {
    public static boolean cadastrarDisco(Disco disco) throws IOException {
        String sql = "INSERT INTO LeituraDisco (tamanho, leituras, bytesLeitura, escritas, bytesEscrita, tempoTransferencia, fkMaquina) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        LocalDateTime momento = LocalDateTime.now();
        Locale localeBR = new Locale("pt", "BR");
        DateTimeFormatter formatoSimples = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", localeBR);
        String dataFormatadaSimples = momento.format(formatoSimples);

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setDouble(1, disco.getTamanho());
            ps.setDouble(2, disco.getLeituras());
            ps.setDouble(3, disco.getBytesLeitura());
            ps.setDouble(4, disco.getEscritas());
            ps.setDouble(5, disco.getBytesEscrita());
            ps.setLong(6, disco.getTempoTranferencia());
            ps.setInt(7, disco.getFkMaquina());
            ps.execute();

            System.out.println("O Disco foi cadastrado com sucesso!");
        } catch (Exception e) {
            LogGenerator.gerarLogBD("[ %s ] SEVERE - Não foi possível efetuar o insert dos dados do DISCO no banco de dados | %s | %s".formatted(dataFormatadaSimples, e.getMessage(), e.getCause()));
            throw new RuntimeException(e);
        }

        return false;
    }
}
