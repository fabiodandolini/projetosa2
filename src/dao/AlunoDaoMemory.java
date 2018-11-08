package dao;

import entidade.Aluno;
import java.util.ArrayList;
import java.util.List;

public class AlunoDaoMemory implements AlunoDao {

    private static List<Aluno> lista = new ArrayList();

    @Override
    public void salvar(Aluno aluno) {
        lista.add(aluno);
    }

    @Override
    public void excluir(Aluno aluno) {
        lista.remove(aluno);
    }

    @Override
    public List<Aluno> getAll() {
        return lista;
    }

    @Override
    public List<Aluno> getAllByNome(String nome) {
        return lista;
    }

}
