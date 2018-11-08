/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entidade.Coordenador;
import java.util.List;

/**
 *
 * @author Salesio Dandolini
 */
public interface CoordenadorDao {
    public void salvar (Coordenador coordenador);
    
    public void excluir (Coordenador coordenador);
    
    public List<Coordenador> getAll();
    
    public List<Coordenador> getAllByNome();
}
