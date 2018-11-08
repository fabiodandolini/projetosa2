/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidade.Coordenador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Salesio Dandolini
 */
public class CoordenadorDaoJdbc implements CoordenadorDao {
    
    private static List<Coordenador> lista = new ArrayList();
    
    @Override
    public void salvar(Coordenador coordenador) {
        if (coordenador.getId() == null) {
            try {
                Connection conn = Conexao.getConexao();
                String query = "INSERT INTO coordenador (nome, curso, senha) VALUES (?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, coordenador.getNome());
                ps.setString(2, coordenador.getCurso());
                ps.setString(3, coordenador.getSenha());
                ps.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                Connection conn = Conexao.getConexao();
                String query = "UPDATE coordenador set nome= ?, curso= ?, senha= ? where id= ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, coordenador.getNome());
                ps.setString(2, coordenador.getCurso());
                ps.setString(3, coordenador.getSenha());
                ps.setInt(4, coordenador.getId());
                ps.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void excluir(Coordenador coordenador) {
        try {
            Connection conn = Conexao.getConexao();
            String query = "DELETE FROM coordenador WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, coordenador.getId());
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Coordenador> getAll() {
        List<Coordenador> lista = new ArrayList();
        Connection conn = Conexao.getConexao();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM coordenador");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Coordenador c = new Coordenador();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setCurso(rs.getString("curso"));
                c.setSenha(rs.getString("senha"));
                lista.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    @Override
    public List<Coordenador> getAllByNome() {
        List<Coordenador> lista = new ArrayList();
        Connection conn = Conexao.getConexao();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM coordenador");
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Coordenador c = new Coordenador();
                c.setNome(rs.getString("nome"));
                lista.add(c);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

}
