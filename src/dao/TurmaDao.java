package dao;

import entidade.Turma;
import java.util.List;

public interface TurmaDao {

    public void salvar(Turma turma);
    
    public void excluir(Turma turma);
    
    public List<Turma> getAll();
    
    public List<Turma> getAllByNome(String nome);
}
