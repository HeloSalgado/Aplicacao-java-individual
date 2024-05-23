package Models;

import Conexao.Conexao;
import Entidades.Processador;
import Logs.LogGenerator;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ProcessadorDAO {
    public static boolean cadastrarCPU(Processador processador) throws IOException {
        String sql = "INSERT INTO LeituraCPU (nome, emUso, fkMaquina) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        LocalDateTime momento = LocalDateTime.now();
        Locale localeBR = new Locale("pt", "BR");
        DateTimeFormatter formatoSimples = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", localeBR);
        String dataFormatadaSimples = momento.format(formatoSimples);

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, processador.getNome());
            ps.setDouble(2, processador.getEmUso());
            ps.setInt(3, processador.getFkMaquina());
            ps.execute();

            System.out.println("A CPU foi cadastrada com sucesso!");
        } catch (Exception e) {
            LogGenerator.gerarLogBD("[ %s ] SEVERE - Não foi possível efetuar o insert dos dados da CPU no banco de dados | %s | %s".formatted(dataFormatadaSimples, e.getMessage(), e.getCause()));
            throw new RuntimeException(e);
        }

        return false;
    }
}
