package dao;

import entidade.Aluno;
import entidade.Autorizacao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AlunoDaoJdbc implements AlunoDao {

    private static List<Aluno> lista = new ArrayList();

    @Override
    public void salvar(Aluno aluno) {
        if (aluno.getId() == null) {
            try {
                Connection conn = Conexao.getConexao();
                String query = "INSERT INTO aluno(aluno, dataNascimento, matricula, curso, coordenador, ativo) VALUES (?,?,?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, aluno.getAluno());
                long dateAsLong = aluno.getDataNascimento().getTime();
                ps.setDate(2, new java.sql.Date(dateAsLong));
                ps.setString(3, aluno.getMatricula());
                ps.setString(4, aluno.getCurso());
                ps.setString(5, aluno.getCoordenador());
                ps.setBoolean(6, aluno.getAtivo());
                ps.executeUpdate();
                //  conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                Connection conn = Conexao.getConexao();
                String QUERY_UPDATE = "update aluno set aluno = ?, dataNascimento = ?, matricula = ?, curso= ?, coordenador= ?, ativo=?  where idaluno = ? ";
                PreparedStatement ps = conn.prepareStatement(QUERY_UPDATE);
                ps.setString(1, aluno.getAluno());
                long dateAsLong = aluno.getDataNascimento().getTime();
                ps.setDate(2, new java.sql.Date(dateAsLong));
                ps.setString(3, aluno.getMatricula());
                ps.setString(4, aluno.getCurso());
                ps.setString(5, aluno.getCoordenador());
                ps.setBoolean(6, aluno.getAtivo());
                ps.setInt(7, aluno.getId());
                ps.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    @Override
    public void excluir(Aluno aluno) {
        try {
            Connection conn = Conexao.getConexao();
            String query = "DELETE FROM ALUNO WHERE idaluno = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, aluno.getId());
            ps.executeUpdate();
            //    conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Aluno> getAll() {
        List<Aluno> lista = new ArrayList();
        Connection conn = Conexao.getConexao();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from aluno");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Aluno a = new Aluno();
                a.setId(rs.getInt("idaluno"));
                a.setAluno(rs.getString("aluno"));
                a.setDataNascimento(rs.getDate("dataNascimento"));
                a.setMatricula(rs.getString("matricula"));
                a.setCurso(rs.getString("curso"));
                a.setCoordenador(rs.getString("coordenador"));
                a.setAtivo(rs.getBoolean("ativo"));
                lista.add(a);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Aluno> getAllByNome(String nome) {
        List<Aluno> listaAlunos = new ArrayList();
        Connection conn = Conexao.getConexao();
        String query ="SELECT * FROM ALUNO WHERE aluno LIKE '" + nome + "%'";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Aluno a = new Aluno();
                a.setId(rs.getInt("idaluno"));
                a.setAluno(rs.getString("aluno"));
                a.setDataNascimento(rs.getDate("dataNascimento"));
                a.setMatricula(rs.getString("matricula"));
                a.setCurso(rs.getString("curso"));
                a.setCoordenador(rs.getString("coordenador"));
                a.setAtivo(rs.getBoolean("ativo"));
                listaAlunos.add(a);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listaAlunos;
    }

}
