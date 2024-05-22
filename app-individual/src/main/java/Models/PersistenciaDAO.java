package Models;

import Conexao.Conexao;
import Entidades.PersistenciaDeDados;

import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaDAO {
    public static void cadastrarPersistencia(PersistenciaDeDados dadosPersistencia){
        String sql = "insert into PersistenciaDeDados (tempoSO, tempoRAM, tempoDisco, tempoCPU, tempoJanelas, unidadeTempo, fkEmpresa)" +
                "values (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;

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
            throw new RuntimeException(e);
        }

    }

    public static void atualizarPersistencia(PersistenciaDeDados dadosPersistencia){
        String select = "select * from PersistenciaDeDados where fkEmpresa = ?";
        PreparedStatement psSelect = null;

        String updateRAM = "update PersistenciaDeDados set tempoRAM = ? where fkEmpresa = ?";
        PreparedStatement psUpdateRAM = null;
        String updateDisco = "update PersistenciaDeDados set tempoDisco = ? where fkEmpresa = ?";
        PreparedStatement psUpdateDisco = null;
        String updateCPU = "update PersistenciaDeDados set tempoCPU = ? where fkEmpresa = ?";
        PreparedStatement psUpdateCPU = null;
        String updateJanelas = "update PersistenciaDeDados set tempoJanelas = ? where fkEmpresa = ?";
        PreparedStatement psUpdateJanelas = null;
        String updateUnidadeTempo = "update PersistenciaDeDados set unidadeTempo = ? where fkEmpresa = ?";
        PreparedStatement psUpdateTempo = null;

        ResultSet rs = null;

        try {
            psSelect = Conexao.getConexao().prepareStatement(select);
            psSelect.setString(1, dadosPersistencia.getFkEmpresa());
            rs = psSelect.executeQuery();

            if (rs.next()){
                psUpdateRAM = Conexao.getConexao().prepareStatement(updateRAM);
                psUpdateRAM.setInt(1, dadosPersistencia.getTempoRAM());
                psUpdateRAM.setString(2, dadosPersistencia.getFkEmpresa());
                psUpdateRAM.executeUpdate();

                psUpdateDisco = Conexao.getConexao().prepareStatement(updateDisco);
                psUpdateDisco.setInt(1, dadosPersistencia.getTempoDisco());
                psUpdateDisco.setString(2, dadosPersistencia.getFkEmpresa());
                psUpdateDisco.executeUpdate();

                psUpdateCPU = Conexao.getConexao().prepareStatement(updateCPU);
                psUpdateCPU.setInt(1, dadosPersistencia.getTempoCPU());
                psUpdateCPU.setString(2, dadosPersistencia.getFkEmpresa());
                psUpdateCPU.executeUpdate();

                psUpdateJanelas = Conexao.getConexao().prepareStatement(updateJanelas);
                psUpdateJanelas.setInt(1, dadosPersistencia.getTempoJanelas());
                psUpdateJanelas.setString(2, dadosPersistencia.getFkEmpresa());
                psUpdateJanelas.executeUpdate();

                psUpdateTempo = Conexao.getConexao().prepareStatement(updateUnidadeTempo);
                psUpdateTempo.setString(1, dadosPersistencia.getUnidadeTempo());
                psUpdateTempo.setString(2, dadosPersistencia.getFkEmpresa());
                psUpdateTempo.executeUpdate();
            } else {
                cadastrarPersistencia(dadosPersistencia);
            }
        } catch (Exception e) {
            try {
                throw new SQLException(e);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    public static List<String> temPersistencia(PersistenciaDeDados dadosPersistencia) {
        String sql = "select tempoRAM, tempoDisco, tempoCPU, tempoJanelas, unidadeTempo from PersistenciaDeDados where fkEmpresa = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;

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
            try {
                throw new SQLException(e);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        return null;
    }
}
