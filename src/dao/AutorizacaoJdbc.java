/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import controllerCoordenador.AdicionarCoordenadorView;
import entidade.Aluno;
import entidade.Autorizacao;
import entidade.Coordenador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aluno
 */
public class AutorizacaoJdbc implements AutorizacaoDao {

    private Coordenador c;
    private static List<Autorizacao> lista = new ArrayList();
    private AdicionarCoordenadorView acView;

    @Override
    public void salvar(Autorizacao autorizacao) {
        if (autorizacao.getId() == null) {
            try {
                Connection conn = Conexao.getConexao();
                String query = "INSERT INTO autorizacao ( idaluno, curso, criacao) VALUES (?, ?, now())";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, autorizacao.getAluno().getId());
                ps.setString(2, autorizacao.getCurso());
                ps.executeUpdate();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                Connection conn = Conexao.getConexao();
                String query = "UPDATE autorizacao set idaluno = ?, curso = ?, criacao = ?, saida = now() where id = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, autorizacao.getIdaluno());
                ps.setString(2, autorizacao.getCurso());
                long dateAsLong = autorizacao.getCriacao().getTime();
                ps.setDate(3, new java.sql.Date(dateAsLong));
//                long dateAsLong2 = autorizacao.getSaida().getTime();
//                ps.setDate(3, new java.sql.Date(dateAsLong2));
                ps.setInt(4, autorizacao.getId());
                ps.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void excluir(Autorizacao autorizacao) {
        try {
            Connection conn = Conexao.getConexao();
            String query = "DELETE FROM autorizacao WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, autorizacao.getId());
            ps.executeUpdate();
            //    conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Autorizacao> getAll() {
        List<Autorizacao> lista = new ArrayList();
        Connection conn = Conexao.getConexao();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from autorizacao");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Autorizacao at = new Autorizacao();
                at.setId(rs.getInt("id"));
                at.setIdaluno(rs.getInt("idaluno"));
                at.setCurso(rs.getString("curso"));
                at.setCriacao(rs.getTimestamp("criacao"));
                at.setSaida(rs.getTime("saida"));
                lista.add(at);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

}
