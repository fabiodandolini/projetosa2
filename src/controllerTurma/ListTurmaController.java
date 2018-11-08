package controllerTurma;

import controllerAluno.AlunoTableModel;
import dao.AlunoDao;
import dao.AlunoDaoJdbc;
import dao.AutorizacaoDao;
import dao.AutorizacaoJdbc;
import dao.TurmaDao;
import dao.TurmaDaoJdbc;
import entidade.Aluno;
import entidade.Turma;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ListTurmaController implements ActionListener {

    private AdicionarTurmaView atView;
    private EditTurmaView etView;
    private TurmaView tView;
    private Turma turmaSelecionada =  null;
    
    public ListTurmaController (AdicionarTurmaView atView, EditTurmaView etView, TurmaView tView){
        this.atView = atView;
        this.etView = etView;
        this.tView = tView;
    }
    
    public void iniciar(){
        //limparTurmas();
        tView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        atView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        etView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        atView.getTxtId().setEditable(false);
        etView.getTxtId().setEditable(false);
        construirEAssinarEventos();
    }

//    private void limparTurmas() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    private void construirEAssinarEventos() {
        popularTabelaTurma();
        tView.getBtnAdicionar().addActionListener(this);
        tView.getBtnEditar().addActionListener(this);
        tView.getBtnExcluir().addActionListener(this);
        atView.getBtnAdicionar().addActionListener(this);
        atView.getBtnCancelar().addActionListener(this);
        etView.getBtnEditar().addActionListener(this);
        etView.getBtnCancelar().addActionListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton botao = (JButton) e.getSource();
        if(botao == tView.getBtnAdicionar()){
           limparTurmas();
            turmaSelecionada = new Turma();
            atView.setVisible(true);
            tView.getTbTurma().clearSelection();
        }
        if(botao == tView.getBtnEditar()){
            selecionarNaTabela();
            if (turmaSelecionada != null) {
                limparTurmas();
                turmaSelecionadaParaEditView();
                etView.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Selecione uma linha na tabela!");
            }
        }
        if(botao == tView.getBtnExcluir()) {
            selecionarNaTabela();
            if (turmaSelecionada != null) {
                    int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?", "Excluir contato", JOptionPane.YES_NO_OPTION);
                    if (resposta == JOptionPane.YES_OPTION) {
                        TurmaDao dao = new TurmaDaoJdbc();
                        dao.excluir(turmaSelecionada);
                        turmaSelecionada = null;
                        popularTabelaTurma();
                        tView.getTbTurma().clearSelection();
                    } else {
                        limparTurmas();
                        tView.getTbTurma().clearSelection();
                    }  
                } else {
                JOptionPane.showMessageDialog(null, "Selecione uma linha na tabela!");
            }
        }
        if(botao == atView.getBtnAdicionar()){
            atViewParaContatoSelecionado();  
            TurmaDao dao1 = new TurmaDaoJdbc();
            dao1.salvar(turmaSelecionada);
            turmaSelecionada = null;
            limparTurmas();
            popularTabelaTurma();  
            atView.setVisible(false);
        }
        if(botao == atView.getBtnCancelar()) {
           limparTurmas();
            turmaSelecionada = null;
            atView.setVisible(false);
        }
        if(botao == etView.getBtnEditar()) {
            editViewParaTurmaSelecionada();
            TurmaDao dao = new TurmaDaoJdbc();
            dao.salvar(turmaSelecionada);
            turmaSelecionada = null;
            limparTurmas();
            popularTabelaTurma();
            etView.setVisible(false);
        }
        if(botao == etView. getBtnCancelar()) {
           limparTurmas();
            turmaSelecionada = null;
            etView.setVisible(false);
            tView.getTbTurma().clearSelection();
        }
    }

    private void popularTabelaTurma() {
        TurmaDao dao = new TurmaDaoJdbc();
        TurmaTableModel ttm = new TurmaTableModel();
        ttm.setLista(dao.getAll());
        tView.getTbTurma().setModel(ttm);
    }

    private void selecionarNaTabela() {
        int posicaoSelecionada = tView.getTbTurma().getSelectedRow();
        if (posicaoSelecionada > -1) {
            turmaSelecionada = ((TurmaTableModel) (tView.getTbTurma().getModel())).getLista().get(posicaoSelecionada);
        } else {
            turmaSelecionada = null;
        }
    }

    private void limparTurmas() {
        etView.getTxtId().setText("");
        etView.getTxtTurma().setText("");
        etView.getTxtCurso().setText("");
        etView.getTxtCoordenador().setText("");

        atView.getTxtId().setText("");
        atView.getTxtTurma().setText("");
        atView.getTxtCurso().setText("");
        atView.getTxtCoordenador().setText("");

    }

    private void turmaSelecionadaParaEditView() {
        etView.getTxtId().setText(turmaSelecionada.getId().toString());
        etView.getTxtTurma().setText(turmaSelecionada.getTurma());
        etView.getTxtCurso().setText(turmaSelecionada.getCurso());
        etView.getTxtCoordenador().setText(turmaSelecionada.getCoordenador());
    }

    private void atViewParaContatoSelecionado() {
        turmaSelecionada.setTurma(atView.getTxtTurma().getText());
        turmaSelecionada.setCurso(atView.getTxtCurso().getText());
        turmaSelecionada.setCoordenador(atView.getTxtCoordenador().getText());
    }

    private void editViewParaTurmaSelecionada() {
        turmaSelecionada.setTurma(etView.getTxtTurma().getText());
        turmaSelecionada.setCurso(etView.getTxtCurso().getText());
        turmaSelecionada.setCoordenador(etView.getTxtCoordenador().getText());
    }
}
