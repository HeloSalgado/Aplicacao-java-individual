package Models;

import Conexao.Conexao;
import Entidades.Usuario;
import Logs.LogGenerator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class UsuarioDAO {
    public static boolean verificarUsuario(Usuario usuario){
        String sql = "select * from Funcionario where email = ? and senha = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, usuario.getEmail());
            ps.setString(2, usuario.getSenha());
            rs = ps.executeQuery();

            LocalDateTime dataHoraAtual = LocalDateTime.now();
            Locale localeBR = new Locale("pt", "BR");
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", localeBR);
            String dataFormatada = dataHoraAtual.format(formato);

            if (rs.next()) {
                System.out.println("Acesso liberado");
                LogGenerator.gerarLogUsuarios("[ %s ] Usuário logado com sucesso = %s".formatted(dataFormatada, rs.getString("email")));
                return true;
            } else {
                System.out.println("Acesso negado");
                LogGenerator.gerarLogUsuarios("[ %s ] Tentativa de acesso inválido detectada para o usuário = %s".formatted(dataFormatada, usuario.getEmail()));
                Usuario.fazerLogin();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
