package dao;

import entidade.Aluno;
import java.util.List;

public interface AlunoDao {

    public void salvar(Aluno aluno);

    public void excluir(Aluno aluno);

    public List<Aluno> getAll();

    public List<Aluno> getAllByNome(String nome);
}
