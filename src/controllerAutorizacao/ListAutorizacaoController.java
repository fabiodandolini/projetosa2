package controllerAutorizacao;

import controllerAluno.AlunoTableModel;
import controllerAluno.AlunoView;
import controllerCoordenador.CoordenadorLoginTableModel;
import controllerCoordenador.ListaCoordenadorView;
import controllerCoordenador.LoginCoordenadorView;
import dao.AlunoDao;
import dao.AlunoDaoJdbc;
import dao.AutorizacaoDao;
import dao.AutorizacaoJdbc;
import entidade.Aluno;
import entidade.Autorizacao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import controllerLogin.AcessoView;
import entidade.Coordenador;

public class ListAutorizacaoController implements ActionListener {

    private AcessoView acessView;
    private AlunoView view;
    private RegistroAlunosView autoView;
    private RegistroAutorizacoes raView;
    private LoginCoordenadorView lgcView;
    private Aluno alunoSelecionado = null;
    private Autorizacao autoSelecionado = null;
    private Coordenador c;

    public ListAutorizacaoController(RegistroAlunosView autoView, RegistroAutorizacoes raView,
            LoginCoordenadorView lgcView) {
        this.autoView = autoView;
        this.raView = raView;
        this.lgcView = lgcView;

    }

    public void iniciar() {
        autoView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        raView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        lgcView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        construirEAssinarEventos();
    }

    private void construirEAssinarEventos() {
        popularTabelaAut();
        popularTabelaAlunoAut();
        raView.getBtnNovaAutorizacao().addActionListener(this);
        raView.getBtnExcluir().addActionListener(this);
        autoView.getBtnAutorizar().addActionListener(this);
        lgcView.getBtnAcessar().addActionListener(this);
        lgcView.getBtnCancelar().addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton botao = (JButton) e.getSource();
        if (botao == raView.getBtnNovaAutorizacao()) {
            popularTabelaAlunoAut();
            autoView.setVisible(true);
        }
        if (botao == raView.getBtnExcluir()) {
            selecionarNaTabelaAutorizacao();
            int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir a autorização?");
            if (resposta == JOptionPane.YES_OPTION) {
                AutorizacaoDao dao = new AutorizacaoJdbc();
                dao.excluir(autoSelecionado);
                autoSelecionado = null;
                popularTabelaAut();
            }
        }
        if (botao == autoView.getBtnAutorizar()) {
            selecionarNaTabela();
            int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente fazer uma autorização para este aluno?");
            if (resposta == JOptionPane.YES_OPTION) {
                Autorizacao novaAutorizacao = new Autorizacao();
                novaAutorizacao.setAluno(alunoSelecionado);
                novaAutorizacao.setCriacao(new Date());
                AutorizacaoDao dao = new AutorizacaoJdbc();
                dao.salvar(novaAutorizacao);
                autoView.setVisible(false);
                popularTabelaAut();
            }
        }
        if (botao == lgcView.getBtnAcessar()) {
            selecionarNaTabelaCoordLogin();
        }
        if (botao == lgcView.getBtnCancelar()) {
            lgcView.setVisible(false);
        }
    }

    private void popularTabela() {
        AutorizacaoDao dao = new AutorizacaoJdbc();
        AutorizacaoTableModel atm = new AutorizacaoTableModel();
        atm.setAutorizacoes(dao.getAll());
        autoView.getTbAlunos().setModel(atm);
    }

    private void selecionarNaTabela() {
        int posicaoSelecionada = autoView.getTbAlunos().getSelectedRow();
        if (posicaoSelecionada > -1) {
            alunoSelecionado = ((AlunoTableModel) (autoView.getTbAlunos().getModel())).getAlunos().get(posicaoSelecionada);
        } else {
            alunoSelecionado = null;
        }
    }

    private void selecionarNaTabelaAutorizacao() {
        int posicaoSelecionada = raView.getTabelaAutorizacoes().getSelectedRow();
        if (posicaoSelecionada > -1) {
            autoSelecionado = ((AutorizacaoTableModel) (raView.getTabelaAutorizacoes().getModel())).getAutorizacoes().get(posicaoSelecionada);
        } else {
            autoSelecionado = null;
        }
    }

    private void popularTabelaAut() {
        AutorizacaoDao dao = new AutorizacaoJdbc();
        AutorizacaoTableModel atm = new AutorizacaoTableModel();
        atm.setAutorizacoes(dao.getAll());
        raView.getTabelaAutorizacoes().setModel(atm);
    }

    private void popularTabelaAlunoAut() {
        AlunoDao dao = new AlunoDaoJdbc();
        AlunoTableModel ctm = new AlunoTableModel();
        ctm.setContatos(dao.getAll());
        autoView.getTbAlunos().setModel(ctm);
    }

    private void selecionarNaTabelaCoordLogin() {
        int posicaoSelecionada = lgcView.getTabelaCoordenadores().getSelectedRow();
        if (posicaoSelecionada > -1) {
            c = ((CoordenadorLoginTableModel) (lgcView.getTabelaCoordenadores().getModel())).getCoordenador().get(posicaoSelecionada);
        } else {
            c = null;
        }
    }

}
