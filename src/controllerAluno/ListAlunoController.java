package controllerAluno;

import controllerAutorizacao.RegistroAlunosView;
import controllerCoordenador.CoordenadorTableModel;
import dao.AlunoDao;
import dao.AlunoDaoJdbc;
import dao.AutorizacaoDao;
import dao.AutorizacaoJdbc;
import dao.CoordenadorDao;
import dao.CoordenadorDaoJdbc;
import entidade.Aluno;
import entidade.Coordenador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

public class ListAlunoController implements ActionListener {

    private EditarAlunoView editView;
    private AdicionarAlunoView adView;
    private AlunoView view;
    private Aluno alunoSelecionado = null;
    private Coordenador coordenadorSelecionado = null;
    private RegistroAlunosView raView;

    public ListAlunoController(AlunoView view, EditarAlunoView editView, AdicionarAlunoView adView, RegistroAlunosView raView) {
        this.view = view;
        this.editView = editView;
        this.adView = adView;
        this.raView = raView;
    }

    public void iniciar() {
        limparAlunos();
        editView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editView.getTxtId().setEnabled(false);
        adView.getTxtId().setEditable(false);
        construirEAssinarEventos();
    }

    private void construirEAssinarEventos() {
        popularTabela();
        editView.getBtnCancelar().addActionListener(this);
        editView.getBtnEditar().addActionListener(this);
        adView.getBtnAdicionar().addActionListener(this);
        adView.getBtnCancelar().addActionListener(this);
        view.getBtnAdicionar().addActionListener(this);
        view.getBtnEditar().addActionListener(this);
        view.getBtnExcluir().addActionListener(this);
        view.getBtnMostrar().addActionListener(this);
        raView.getBtnPesquisar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton botao = (JButton) e.getSource();
        if (botao == view.getBtnExcluir()) {
            int posicaoSelecionada = view.getTabelaAluno().getSelectedRow();
            if (posicaoSelecionada > -1) {
                selecionarNaTabela();
                if (alunoSelecionado != null) {
                    int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir?", "Excluir contato", JOptionPane.YES_NO_OPTION);
                    if (resposta == JOptionPane.YES_OPTION) {
                        AlunoDao dao = new AlunoDaoJdbc();
                        dao.excluir(alunoSelecionado);
                        alunoSelecionado = null;
                        popularTabela();
                        view.getTxtId().setText("");
                        view.getTxtAluno().setText("");
                        view.getTxtData().setText("");
                        view.getTxtMatricula().setText("");
                        view.getTxtCurso().setText("");
                        view.getTxtCoordenador().setText("");
                        view.getTxtAtivo().setText("");
                        view.getTabelaAluno().clearSelection();
                    } else {
                        limparAlunos();
                        view.getTabelaAluno().clearSelection();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione uma linha na tabela!");
            }
        }
        if (botao == view.getBtnEditar()) {
            selecionarNaTabela();
            if (alunoSelecionado != null) {
                limparAlunos();
                alunoSelecionadoParaEditView();
                editView.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Selecione uma linha na tabela!");
            }
        }
        if (botao == editView.getBtnCancelar()) {
            limparAlunos();
            alunoSelecionado = null;
            editView.setVisible(false);
            view.getTabelaAluno().clearSelection();
        }
        if (botao == editView.getBtnEditar()) {
            editViewParaContatoSelecionado();
            AlunoDao dao = new AlunoDaoJdbc();
            dao.salvar(alunoSelecionado);
            alunoSelecionado = null;
            limparAlunos();
            popularTabela();
            editView.setVisible(false);

        }
        if (botao == view.getBtnAdicionar()) {
            limparAlunos();
            alunoSelecionado = new Aluno();
            adView.setVisible(true);
            view.getTabelaAluno().clearSelection();

        }
        if (botao == adView.getBtnAdicionar()) {
            adViewParaContatoSelecionado();  
            AlunoDao dao1 = new AlunoDaoJdbc();
            dao1.salvar(alunoSelecionado);
            alunoSelecionado = null;
            limparAlunos();
            popularTabela();  
            adView.setVisible(false);

        }
        if (botao == adView.getBtnCancelar()) {
            limparAlunos();
            alunoSelecionado = null;
            adView.setVisible(false);

        }
        if (botao == view.getBtnMostrar()) {
            int posicaoSelecionada = view.getTabelaAluno().getSelectedRow();
            if (posicaoSelecionada > -1) {
                
                TableModel tm = view.getTabelaAluno().getModel();
                AlunoTableModel atm = (AlunoTableModel) tm;
                List<Aluno> lista = atm.getAlunos();
                Aluno a = lista.get(posicaoSelecionada);
                view.getTxtId().setText(a.getId().toString());
                view.getTxtAluno().setText(a.getAluno().substring(0,1).toUpperCase() + a.getAluno().substring(1).toLowerCase());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                view.getTxtData().setText(sdf.format(a.getDataNascimento().getTime()));
                view.getTxtMatricula().setText(a.getMatricula());
                view.getTxtCurso().setText(a.getCurso().substring(0,1).toUpperCase() + a.getCurso().substring(1).toLowerCase());
                view.getTxtCoordenador().setText(a.getCoordenador().substring(0,1).toUpperCase() + a.getCoordenador().substring(1).toLowerCase());
                if(a.getAtivo().equals(true)){
                    view.getTxtAtivo().setText("Sim");
                }else{
                    view.getTxtAtivo().setText("Não");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione uma linha na tabela!");
            }
        }
        if (botao == raView.getBtnPesquisar()) {
            String nome = raView.getTxtPesquisar().getText();
            AlunoDao dao = new AlunoDaoJdbc();
            AlunoTableModel atm = new AlunoTableModel();
            List<Aluno> alunosPesquisados = dao.getAllByNome(nome);
            atm.setContatos(alunosPesquisados);
            raView.getTbAlunos().setModel(atm);

        }
    }

    private void limparAlunos() {
        editView.getTxtId().setText("");
        editView.getTxtAluno().setText("");
        editView.getTxtData().setText("");
        editView.getTxtMatricula().setText("");
        editView.getTxtCurso().setText("");
        editView.getCbCoordenadores().setSelectedItem("");
        editView.getTxtAtivo().setText("");

        adView.getTxtId().setText("");
        adView.getTxtAluno().setText("");
        adView.getTxtData().setText("");
        adView.getTxtMatricula().setText("");
        adView.getTxtCurso().setText("");
        adView.getCbCoordenadores();
        adView.getTxtAtivo().setSelected(false);

        view.getTxtId().setText("");
        view.getTxtAluno().setText("");
        view.getTxtAtivo().setText("");
        view.getTxtCoordenador().setText("");
        view.getTxtCurso().setText("");
        view.getTxtData().setText("");
        view.getTxtMatricula().setText("");
    }

    private void popularTabela() {
        AlunoDao dao = new AlunoDaoJdbc();
        AlunoTableModel atm = new AlunoTableModel();
        atm.setContatos(dao.getAll());
        view.getTabelaAluno().setModel(atm);
    }

    private void editViewParaContatoSelecionado() {
        alunoSelecionado.setAluno(editView.getTxtAluno().getText());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date data = sdf.parse(editView.getTxtData().getText());
            alunoSelecionado.setDataNascimento(data);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        alunoSelecionado.setMatricula(editView.getTxtMatricula().getText());
        alunoSelecionado.setCurso(editView.getTxtCurso().getText());
        alunoSelecionado.setCoordenador(editView.getCbCoordenadores().getSelectedItem().toString());
        alunoSelecionado.setAtivo(editView.getTxtAtivo().isSelected());
    }

    private void alunoSelecionadoParaEditView() {
        editView.getTxtId().setText(alunoSelecionado.getId().toString());
        editView.getTxtAluno().setText(alunoSelecionado.getAluno());
        editView.getTxtMatricula().setText(alunoSelecionado.getMatricula());
        editView.getTxtCurso().setText(alunoSelecionado.getCurso());
        editView.getCbCoordenadores().setSelectedItem(alunoSelecionado.getCoordenador());
        editView.getTxtAtivo().setText(alunoSelecionado.getAtivo().toString());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        editView.getTxtData().setText(sdf.format(alunoSelecionado.getDataNascimento().getTime()));
    }

    private void selecionarNaTabela() {
        int posicaoSelecionada = view.getTabelaAluno().getSelectedRow();
        if (posicaoSelecionada > -1) {
            alunoSelecionado = ((AlunoTableModel) (view.getTabelaAluno().getModel())).getAlunos().get(posicaoSelecionada);
        } else {
            alunoSelecionado = null;
        }
    }

    private void adViewParaContatoSelecionado() {
        alunoSelecionado.setAluno(adView.getTxtAluno().getText());
        alunoSelecionado.setMatricula(adView.getTxtMatricula().getText());
        alunoSelecionado.setCurso(adView.getTxtCurso().getText());
        alunoSelecionado.setCoordenador(adView.getCbCoordenadores().getSelectedItem().toString());
        
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date data = sdf.parse(adView.getTxtData().getText());
            alunoSelecionado.setDataNascimento(data);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        alunoSelecionado.setAtivo(adView.getTxtAtivo().isSelected());
    }
    
}
