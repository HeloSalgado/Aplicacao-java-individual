package Models;

import Conexao.Conexao;
import Entidades.Janelas;
import Logs.LogGenerator;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class JanelasDAO {
    public static boolean cadastrarJanelas(Janelas janelas) throws IOException {
        String sql = "INSERT INTO LeituraJanelas (identificador, titulo, pid, totalJanelas, fkMaquina) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        LocalDateTime momento = LocalDateTime.now();
        Locale localeBR = new Locale("pt", "BR");
        DateTimeFormatter formatoSimples = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", localeBR);
        String dataFormatadaSimples = momento.format(formatoSimples);

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setLong(1, janelas.getId());
            ps.setString(2, janelas.getTitulo());
            ps.setLong(3, janelas.getPid());
            ps.setInt(4, janelas.getTotal());
            ps.setInt(5, janelas.getFkMaquina());
            ps.execute();

            System.out.println("A Janela foi cadastrada com sucesso!");
        } catch (Exception e) {
            LogGenerator.gerarLogBD("[ %s ] SEVERE - Não foi possível efetuar o insert dos dados das JANELAS no banco de dados | %s | %s".formatted(dataFormatadaSimples, e.getMessage(), e.getCause()));
            throw new RuntimeException(e);
        }

        return true;
    }
}
