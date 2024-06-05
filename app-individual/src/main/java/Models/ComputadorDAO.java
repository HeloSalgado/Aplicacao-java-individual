package Models;

import Conexao.Conexao;
import Entidades.Computador;
import Entidades.Usuario;
import Logs.LogGenerator;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ComputadorDAO {

    public static void cadastrarComputador(Computador computador) throws IOException {
        String sql = "INSERT INTO Maquina (hostname, ip, fkEmpresa) VALUES (?, ?, ?)";
        LocalDateTime momento = LocalDateTime.now();
        Locale localeBR = new Locale("pt", "BR");
        DateTimeFormatter formatoSimples = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", localeBR);
        String dataFormatadaSimples = momento.format(formatoSimples);
        PreparedStatement ps2 = null;

        try {
            ps2 = Conexao.getConexao().prepareStatement(sql);
            ps2.setString(1, computador.getHostName());
            ps2.setString(2, computador.getIpv4());
            ps2.setString(3, computador.getFkEmpresa());
            ps2.execute();

            System.out.println("O computador foi cadastrado com sucesso!");
        } catch (Exception e) {
            LogGenerator.gerarLogBD("[ %s ] SEVERE - Não foi possível efetuar o insert dos dados da MÁQUINA no banco de dados | %s | %s".formatted(dataFormatadaSimples, e.getMessage(), e.getCause()));
            throw new RuntimeException(e);
        }
    }

    public static boolean verificarComputador(Computador computador) throws IOException {
        String sql = "select * from Maquina where hostname = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        LocalDateTime momento = LocalDateTime.now();
        Locale localeBR = new Locale("pt", "BR");
        DateTimeFormatter formatoSimples = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", localeBR);
        String dataFormatadaSimples = momento.format(formatoSimples);

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, computador.getHostName());
            rs = ps.executeQuery();

            if (rs.next()){
                System.out.println();
                System.out.println("Máquina já cadastrada");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LogGenerator.gerarLogBD("[ %s ] SEVERE - Não foi possível efetuar o select dos dados da MÁQUINA no banco de dados | %s | %s".formatted(dataFormatadaSimples, e.getMessage(), e.getCause()));
            throw new RuntimeException(e);
        }
    }

    public static Integer buscarIdMaquina(Computador computador) throws IOException {
        String sql = "select idMaquina from Maquina where hostname = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        LocalDateTime momento = LocalDateTime.now();
        Locale localeBR = new Locale("pt", "BR");
        DateTimeFormatter formatoSimples = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", localeBR);
        String dataFormatadaSimples = momento.format(formatoSimples);

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, computador.getHostName());
            rs = ps.executeQuery();

            if (rs.next()){
                return rs.getInt("idMaquina");
            } else {
                return null;
            }
        } catch (Exception e) {
            LogGenerator.gerarLogBD("[ %s ] SEVERE - Não foi possível efetuar o select do ID da máquina no banco de dados | %s | %s".formatted(dataFormatadaSimples, e.getMessage(), e.getCause()));
            throw new RuntimeException(e);
        }
    }


}

