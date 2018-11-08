package controllerCoordenador;

import entidade.Coordenador;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class CoordenadorLoginTableModel extends AbstractTableModel {

    private List<Coordenador> coordenador = new ArrayList();

    public List<Coordenador> getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(List<Coordenador> coordenador) {
        this.coordenador = coordenador;
    }

    @Override
    public int getRowCount() {
        return coordenador.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String valor = "";
        Coordenador umCoordenador = coordenador.get(rowIndex);
        switch (columnIndex) {
            case 0: {
                valor = umCoordenador.getId().toString();
                break;
            }
            case 1: {
                valor = umCoordenador.getNome();
                break;
            }
            case 2: {
                valor = umCoordenador.getCurso();
                break;
            }
        }
        return valor;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: {
                return "Id Coordenador";
            }
            case 1: {
                return "Coordenador";
            }
            case 2: {
                return "Curso";
            }
            default: {
                return "";
            }
        }
    }
}
