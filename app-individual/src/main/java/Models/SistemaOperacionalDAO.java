package Models;

import Conexao.Conexao;
import Entidades.SistemaOperacional;
import Logs.LogGenerator;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class SistemaOperacionalDAO {
    public static boolean cadastrarSO(SistemaOperacional sistemaOperacional) throws IOException {
        String sql = "INSERT INTO LeituraSO (nome, tempoAtividade, fkMaquina) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        LocalDateTime momento = LocalDateTime.now();
        Locale localeBR = new Locale("pt", "BR");
        DateTimeFormatter formatoSimples = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", localeBR);
        String dataFormatadaSimples = momento.format(formatoSimples);

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, sistemaOperacional.getNome());
            ps.setLong(2, sistemaOperacional.getTempoAtividade());
            ps.setInt(3, sistemaOperacional.getFkMaquina());
            ps.execute();

            System.out.println("O Sistema Operacional foi cadastrado com sucesso!");
        } catch (Exception e) {
            LogGenerator.gerarLogBD("[ %s ] SEVERE - Não foi possível efetuar o insert dos dados do SISTEMA OPERACIONAL no banco de dados | %s | %s".formatted(dataFormatadaSimples, e.getMessage(), e.getCause()));
            throw new RuntimeException(e);
        }

        return false;
    }
}
