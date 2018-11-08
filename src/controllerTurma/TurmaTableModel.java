package controllerTurma;

import entidade.Turma;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TurmaTableModel extends AbstractTableModel {
 private List<Turma> lista = new ArrayList();

    public List<Turma> getLista() {
        return lista;
    }

    public void setLista(List<Turma> lista) {
        this.lista = lista;
    }
 
    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String valor = "";
        Turma umaTurma = lista.get(rowIndex);
        switch(columnIndex){
            case 0: {
                valor = umaTurma.getId().toString();
                break;}
            case 1: {
                valor = umaTurma.getTurma();
                break;}
            case 2:{
                valor = umaTurma.getCurso();
                break;}
            case 3:{
                valor = umaTurma.getCoordenador();
                break;}
        }
        return valor;
    }
 @Override
    public String getColumnName (int column) {
     switch(column) {
         case 0:{ return "ID";}
         
         case 1:{ return "Turma";}
         
         case 2:{ return "Curso";}
         
         case 3:{ return "Coordenador";}
         
         default:{ return "";}
     }    
    }
}
