package Models;

import Conexao.Conexao;
import Entidades.Processador;

import java.sql.PreparedStatement;

public class ProcessadorDAO {
    public static boolean cadastrarCPU(Processador processador){
        String sql = "INSERT INTO LeituraCPU (nome, emUso, fkMaquina) VALUES (?, ?, ?)";
        PreparedStatement ps = null;

        try {
            ps = Conexao.getConexao().prepareStatement(sql);
            ps.setString(1, processador.getNome());
            ps.setDouble(2, processador.getEmUso());
            ps.setInt(3, processador.getFkMaquina());
            ps.execute();

            System.out.println("A CPU foi cadastrada com sucesso!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
