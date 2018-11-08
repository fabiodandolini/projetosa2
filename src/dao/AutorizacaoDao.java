package dao;

import entidade.Autorizacao;
import java.util.List;

public interface AutorizacaoDao {
   public void salvar(Autorizacao autorizacao);

    public void excluir(Autorizacao autorizacao);

    public List<Autorizacao> getAll();

}
