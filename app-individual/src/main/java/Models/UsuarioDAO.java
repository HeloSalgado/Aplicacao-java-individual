package Models;

import Conexao.Conexao;
import Entidades.Usuario;
import Logs.LogGenerator;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class UsuarioDAO {
    public static boolean verificarUsuario(Usuario usuario) throws IOException {
        String sql = "select * from Funcionario where email = ? and senha = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        LocalDateTime momento = LocalDateTime.now();
        Locale localeBR = new Locale("pt", "BR");
        DateTimeFormatter formatoSimples = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", localeBR);
        String dataFormatadaSimples = momento.format(formatoSimples);

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getSenha());
            rs = ps.executeQuery();


            if (rs.next()) {
                System.out.println("Acesso liberado");
                LogGenerator.gerarLogUsuarios("[ %s ] INFO - Usuário logado com sucesso = %s".formatted(dataFormatadaSimples, rs.getString("email")));
                return true;
            } else {
                System.out.println("Acesso negado");
                LogGenerator.gerarLogUsuarios("[ %s ] INFO - Tentativa de acesso inválido detectada para o usuário = %s".formatted(dataFormatadaSimples, usuario.getEmail()));
                Usuario.fazerLogin();
            }

        } catch (Exception e) {
            LogGenerator.gerarLogBD("[ %s ] SEVERE - Não foi possível efetuar o select do USUÁRIO no banco de dados | %s | %s".formatted(dataFormatadaSimples, e.getMessage(), e.getCause()));
            throw new RuntimeException(e);
        }

        return false;
    }
}
