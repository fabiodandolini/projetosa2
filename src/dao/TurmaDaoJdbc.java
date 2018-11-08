package dao;

import entidade.Turma;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TurmaDaoJdbc implements TurmaDao {
    private List<Turma> lista = new ArrayList();
    
    @Override
    public void salvar(Turma turma) {
       if(turma.getId() == null){
           try{
               Connection conn = Conexao.getConexao();
               String query = "INSERT INTO turma(turma,curso,coordenador) VALUES (?,?,?)";
               PreparedStatement ps = conn.prepareStatement(query);
               ps.setString(1, turma.getTurma());
               ps.setString(2, turma.getCurso());
               ps.setString(3, turma.getCoordenador());
               ps.executeUpdate();
           }catch(Exception ex){
               ex.printStackTrace();
           }}else{
           try{
            Connection conn = Conexao.getConexao();
            String QUERY_UPDATE = "UPDATE turma set turma= ?, curso= ?, coordenador=? where idturma=?";
            PreparedStatement ps = conn.prepareStatement(QUERY_UPDATE);
            ps.setString(1, turma.getTurma());
            ps.setString(2, turma.getCurso());
            ps.setString(3, turma.getCoordenador());
            ps.setString(4, turma.getId().toString());
            ps.executeUpdate();
       }catch(Exception ex) {
           ex.printStackTrace();
       }
       }
    }

    @Override
    public void excluir(Turma turma) {
        try{
        Connection conn = Conexao.getConexao();
        String query= "DELETE FROM turma where idturma=?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, turma.getId());
        ps.executeUpdate();
    }catch(Exception ex) {
        ex.printStackTrace();
    }
    }
    @Override
     public List<Turma> getAll() {
        List<Turma> lista = new ArrayList();
        Connection conn = Conexao.getConexao();
        try{
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM turma");
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Turma t = new Turma();
                t.setId(rs.getInt("idturma"));
                t.setTurma(rs.getString("turma"));
                t.setCurso(rs.getString("curso"));
                t.setCoordenador(rs.getString("coordenador"));
                lista.add(t);
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Turma> getAllByNome(String nome) {
        List<Turma> listaTurmas = new ArrayList();
        Connection conn = Conexao.getConexao();
        String query = "SELECT * FROM turma WHERE turma.turma LIKE '" + nome + "%'";
        try{
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Turma t = new Turma();
                t.setId(rs.getInt("idturma"));
                t.setTurma(rs.getString("turma"));
                t.setCurso(rs.getString("curso"));
                t.setCoordenador(rs.getString("coordenador"));
                listaTurmas.add(t);
            }
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return listaTurmas;
    }
    

}
