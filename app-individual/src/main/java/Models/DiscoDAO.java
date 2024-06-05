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
    public static void cadastrarDisco(Disco disco) throws IOException {
        String sql = "INSERT INTO LeituraDisco (disponivel, total, emUso, fkMaquina) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        LocalDateTime momento = LocalDateTime.now();
        Locale localeBR = new Locale("pt", "BR");
        DateTimeFormatter formatoSimples = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", localeBR);
        String dataFormatadaSimples = momento.format(formatoSimples);

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, disco.getDisponivel());
            ps.setString(2, disco.getTotal());
            ps.setString(3, disco.getEmUso());
            ps.setInt(4, disco.getFkMaquina());
            ps.execute();

            System.out.println("O Disco foi cadastrado com sucesso!");
        } catch (Exception e) {
            LogGenerator.gerarLogBD("[ %s ] SEVERE - Não foi possível efetuar o insert dos dados do DISCO no banco de dados | %s | %s".formatted(dataFormatadaSimples, e.getMessage(), e.getCause()));
            throw new RuntimeException(e);
        }

    }
}
