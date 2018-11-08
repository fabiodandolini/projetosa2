package controllerAluno;

import entidade.Aluno;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class AlunoTableModel extends AbstractTableModel {

    private List<Aluno> alunos = new ArrayList();

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setContatos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    @Override
    public int getRowCount() {
        return alunos.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String valor = "";
        Aluno umAluno = alunos.get(rowIndex);
        switch (columnIndex) {
            case 0: {
                valor = umAluno.getId().toString();
                break;
            }
            case 1: {
                valor = umAluno.getAluno();
                break;
            }
            case 2: {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                valor = sdf.format(umAluno.getDataNascimento());
                break;
            }
            case 3: {
                valor = umAluno.getMatricula();
                break;
            }
            case 4: {
                valor = umAluno.getCurso();
                break;
            }
            case 5: {
                valor = umAluno.getCoordenador();
                break;
            }
            case 6: {
                if (umAluno.getAtivo()) {
                    valor = "Cursando";
                } else {
                    valor = "Inativo";
                }
                break;
            }
        }
        return valor;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: {
                return "ID";
            }
            case 1: {
                return "Aluno";
            }
            case 2: {
                return "Data de Nascimento";
            }
            case 3: {
                return "Matrícula";
            }
            case 4: {
                return "Curso";
            }
            case 5: {
                return "Coordenador";
            }
            case 6: {
                return "Estado";
            }
            default: {
                return "";
            }
        }
    }

}
