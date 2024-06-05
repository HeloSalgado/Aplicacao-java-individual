package Models;

import Conexao.Conexao;
import Entidades.PersistenciaDeDados;
import Logs.LogGenerator;

import java.io.IOException;
import java.rmi.server.ExportException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PersistenciaDAO {
    public static void cadastrarPersistencia(PersistenciaDeDados dadosPersistencia) throws IOException {
        String sql = "insert into PersistenciaDeDados (tempoRAM, tempoDisco, tempoCPU, tempoJanelas, unidadeTempo, fkEmpresa)" +
                "values (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        LocalDateTime momento = LocalDateTime.now();
        Locale localeBR = new Locale("pt", "BR");
        DateTimeFormatter formatoSimples = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", localeBR);
        String dataFormatadaSimples = momento.format(formatoSimples);

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setInt(1, dadosPersistencia.getTempoRAM());
            ps.setInt(2, dadosPersistencia.getTempoDisco());
            ps.setInt(3, dadosPersistencia.getTempoCPU());
            ps.setInt(4, dadosPersistencia.getTempoJanelas());
            ps.setString(5, dadosPersistencia.getUnidadeTempo());
            ps.setString(6, dadosPersistencia.getFkEmpresa());
            ps.execute();

        } catch (Exception e) {
            LogGenerator.gerarLogBD("[ %s ] SEVERE - Não foi possível efetuar o insert dos parâmetros de coleta de dados no banco de dados | %s | %s".formatted(dataFormatadaSimples, e.getMessage(), e.getCause()));
            throw new RuntimeException(e);
        }

    }

    public static void atualizarPersistencia(PersistenciaDeDados dadosPersistencia) throws IOException {
        String select = "select * from PersistenciaDeDados where fkEmpresa = ?";

        LocalDateTime momento = LocalDateTime.now();
        Locale localeBR = new Locale("pt", "BR");
        DateTimeFormatter formatoSimples = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", localeBR);
        String dataFormatadaSimples = momento.format(formatoSimples);

        String update = "UPDATE PersistenciaDeDados SET tempoRAM = ?, tempoDisco = ?, tempoCPU = ?, tempoJanelas = ?, unidadeTempo = ? WHERE fkEmpresa = ?";
        PreparedStatement psSelect = null;
        PreparedStatement psUpdate = null;
        ResultSet rs = null;

        try {
            psSelect = Conexao.getConexao().prepareStatement(select);
            psSelect.setString(1, dadosPersistencia.getFkEmpresa());
            rs = psSelect.executeQuery();

            if (rs.next()){
                psUpdate = Conexao.getConexao().prepareStatement(update);
                psUpdate.setInt(1, dadosPersistencia.getTempoRAM());
                psUpdate.setInt(2, dadosPersistencia.getTempoDisco());
                psUpdate.setInt(3, dadosPersistencia.getTempoCPU());
                psUpdate.setInt(4, dadosPersistencia.getTempoJanelas());
                psUpdate.setString(5, dadosPersistencia.getUnidadeTempo());
                psUpdate.setString(6, dadosPersistencia.getFkEmpresa());
                psUpdate.executeUpdate();
            } else {
                cadastrarPersistencia(dadosPersistencia);
            }
        } catch (Exception e) {
            LogGenerator.gerarLogBD("[ %s ] SEVERE - Não foi possível efetuar o update dos parâmetros de coleta de dados no banco de dados | %s | %s".formatted(dataFormatadaSimples, e.getMessage(), e.getCause()));
        }

    }

    public static List<String> temPersistencia(PersistenciaDeDados dadosPersistencia) throws IOException {
        String sql = "select tempoRAM, tempoDisco, tempoCPU, tempoJanelas, unidadeTempo from PersistenciaDeDados where fkEmpresa = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        LocalDateTime momento = LocalDateTime.now();
        Locale localeBR = new Locale("pt", "BR");
        DateTimeFormatter formatoSimples = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss", localeBR);
        String dataFormatadaSimples = momento.format(formatoSimples);

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, dadosPersistencia.getFkEmpresa());
            rs = ps.executeQuery();

            List<String> dados = new ArrayList<>();

            if (rs.next()){
                dados.add(String.valueOf(rs.getInt("tempoRAM")));
                dados.add(String.valueOf(rs.getInt("tempoDisco")));
                dados.add(String.valueOf(rs.getInt("tempoCPU")));
                dados.add(String.valueOf(rs.getInt("tempoJanelas")));
                dados.add(rs.getString("unidadeTempo"));

                return dados;
            }

        } catch (Exception e){
            LogGenerator.gerarLogBD("[ %s ] SEVERE - Não foi possível efetuar o select dos PARÂMETROS de coleta de dados no banco de dados | %s | %s".formatted(dataFormatadaSimples, e.getMessage(), e.getCause()));
            throw new RuntimeException(e);
        }

        return null;
    }
}
