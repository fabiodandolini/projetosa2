package controllerAutorizacao;

import entidade.Aluno;
import entidade.Autorizacao;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class AutorizacaoTableModel extends AbstractTableModel {

    private List<Autorizacao> autorizacoes = new ArrayList();

    public List<Autorizacao> getAutorizacoes() {
        return autorizacoes;
    }

    public void setAutorizacoes(List<Autorizacao> autorizacoes) {
        this.autorizacoes = autorizacoes;
    }

    @Override
    public int getRowCount() {
        return autorizacoes.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String valor = "";
        Autorizacao umaAutorizacao = autorizacoes.get(rowIndex);
        switch (columnIndex) {
            case 0: {
                valor = umaAutorizacao.getId().toString();
                break;
            }
            case 1: {
                valor = umaAutorizacao.getIdaluno().toString();
                break;
            }
            case 2: {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                valor = sdf.format(umaAutorizacao.getCriacao());
                break;
            }
            case 3: {
                if (umaAutorizacao.getSaida() == null) {
                    valor = "";
                } else {
                    valor = umaAutorizacao.getSaida().toString();
                }
                break;
            }
        }
        return valor;
    }

    public String getColumnName(int column) {
        switch (column) {
            case 0: {
                return "ID Autorização";
            }
            case 1: {
                return "ID Aluno";
            }
            case 2: {
                return "Data criação";
            }
            case 3: {
                return "Data saída";
            }
            default: {
                return "";
            }
        }
    }
}
