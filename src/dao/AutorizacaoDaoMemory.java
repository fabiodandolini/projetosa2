/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidade.Aluno;
import entidade.Autorizacao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Salesio Dandolini
 */
public class AutorizacaoDaoMemory implements AutorizacaoDao {
    
    private static List<Autorizacao> lista = new ArrayList();
    
    @Override
    public void salvar(Autorizacao autorizacao) {
        lista.add(autorizacao);
    }

    @Override
    public void excluir(Autorizacao autorizacao) {
        lista.remove(autorizacao);
    }

    @Override
    public List<Autorizacao> getAll() {
        return lista;
    }

    

}
