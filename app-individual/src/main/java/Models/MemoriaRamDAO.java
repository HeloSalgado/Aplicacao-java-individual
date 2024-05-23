package Models;

import Conexao.Conexao;
import Entidades.MemoriaRam;
import Logs.LogGenerator;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MemoriaRamDAO {
    public static boolean cadastrarRAM(MemoriaRam memoriaRam) throws IOException {
        String sql = "INSERT INTO LeituraMemoriaRam (emUso, disponivel, total, fkMaquina) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        LocalDateTime momento = LocalDateTime.now();
        Locale localeBR = new Locale("pt", "BR");
        DateTimeFormatter formatoSimples = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", localeBR);
        String dataFormatadaSimples = momento.format(formatoSimples);

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setDouble(1, memoriaRam.getEmUso());
            ps.setDouble(2, memoriaRam.getDisponivel());
            ps.setDouble(3, memoriaRam.getTotal());
            ps.setInt(4, memoriaRam.getFkMaquina());
            ps.execute();

            System.out.println("A Memória RAM foi cadastrada com sucesso!");
        } catch (Exception e) {
            LogGenerator.gerarLogBD("[ %s ] SEVERE - Não foi possível efetuar o insert dos dados da MEMÓRIA RAM no banco de dados | %s | %s".formatted(dataFormatadaSimples, e.getMessage(), e.getCause()));
            throw new RuntimeException(e);
        }

        return false;
    }
}
