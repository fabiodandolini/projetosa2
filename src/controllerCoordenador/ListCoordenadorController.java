package controllerCoordenador;

import controllerAutorizacao.RegistroAutorizacoes;
import controllerLogin.AcessoView;
import controllerLogin.LoginView;
import dao.CoordenadorDao;
import dao.CoordenadorDaoJdbc;
import entidade.Coordenador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ListCoordenadorController implements ActionListener {

    private Coordenador c;
    private ListaCoordenadorView lcView;
    private EditCoordenadorView ecView;
    private AdicionarCoordenadorView acView;
    private LoginView lgView;
    private RegistroAutorizacoes raView;
    private LoginCoordenadorView lgcView;
    private AcessoView acessView;

    public ListCoordenadorController(ListaCoordenadorView lcView, EditCoordenadorView ecView, AdicionarCoordenadorView acView, LoginView lgView, RegistroAutorizacoes raView, LoginCoordenadorView lgcView, AcessoView acessView) {
        this.lcView = lcView;
        this.ecView = ecView;
        this.acView = acView;
        this.lgView = lgView;
        this.raView = raView;
        this.lgcView = lgcView;
        this.acessView = acessView;
    }

    public void iniciar() {
        construirEAssinarEventos();
        popularTabelaCoordenador();
        popularTabelaCoordenadorLogin();
        acView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ecView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    private void construirEAssinarEventos() {
        ecView.getTxtId().setEnabled(false);
        acView.getTxtId().setEnabled(false);
        lcView.getBtnAdicionar().addActionListener(this);
        lcView.getBtnEditar().addActionListener(this);
        lcView.getBtnExcluir().addActionListener(this);
        ecView.getBtnSalvar().addActionListener(this);
        ecView.getBtnCancelar().addActionListener(this);
        acView.getBtnAdicionar().addActionListener(this);
        acView.getBtnCancelar().addActionListener(this);
        lgView.getBtnAcessar().addActionListener(this);
        lgcView.getBtnAcessar().addActionListener(this);
        lgcView.getBtnCancelar().addActionListener(this);
        ecView.getBtnSalvar().addActionListener(this);
        ecView.getBtnCancelar().addActionListener(this);
        acessView.getBtnAutorizacoes().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton botao = (JButton) e.getSource();
        if (botao == lcView.getBtnEditar()) {
            selecionarNaTabela();
            if (c != null) {
                int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente editar o coordenador?");
                if (resposta == JOptionPane.YES_OPTION) {
                    limparCampos();
                    coordenadorSelecionadoParaEditView();
                    ecView.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione uma linha na tabela!");
            }
        }
        if (botao == lcView.getBtnAdicionar()) {
            limparCampos();
            acView.setVisible(true);
        }
        if (botao == lcView.getBtnExcluir()) {
            selecionarNaTabela();
            if (c != null) {
                int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o coordenador?");
                if (resposta == JOptionPane.YES_OPTION) {
                    excluirCoordenador();
                }
            }
        }
        if (botao == ecView.getBtnSalvar()) {
            if (c != null) {
                editarCoordenador();
            }
        }
        if (botao == ecView.getBtnCancelar()) {
            limparCampos();
            c = null;
            ecView.setVisible(false);
        }
        if (botao == acView.getBtnAdicionar()) {
            novoCoordenador();
            acView.setVisible(false);
        }
        if (botao == acView.getBtnCancelar()) {
            limparCampos();
            c = null;
            acView.setVisible(false);
        }
        if (botao == lgcView.getBtnAcessar()) {
            coordenadorSelecionadoParaLogin();
            loginCoordenador();
            lgcView.getTxtSenha().setText("");
        }
        if (botao == lgcView.getBtnCancelar()) {
            lgcView.setVisible(false);
        }
    }

    private void popularTabelaCoordenador() {
        CoordenadorDao dao = new CoordenadorDaoJdbc();
        CoordenadorTableModel ctm = new CoordenadorTableModel();
        ctm.setCoordenador(dao.getAll());
        lcView.getTabelaCoordenadores().setModel(ctm);

    }

    private void popularTabelaCoordenadorLogin() {
        CoordenadorDao dao = new CoordenadorDaoJdbc();
        CoordenadorLoginTableModel cltm = new CoordenadorLoginTableModel();
        cltm.setCoordenador(dao.getAll());
        lgcView.getTabelaCoordenadores().setModel(cltm);
    }

    private void selecionarNaTabela() {
        int posicaoSelecionada = lcView.getTabelaCoordenadores().getSelectedRow();
        if (posicaoSelecionada > -1) {
            c = ((CoordenadorTableModel) (lcView.getTabelaCoordenadores().getModel())).getCoordenador().get(posicaoSelecionada);
        } else {
            c = null;
        }
    }

    private void limparCampos() {
        ecView.getTxtId().setText("");
        ecView.getTxtNome().setText("");
        ecView.getTxtCurso().setText("");
        ecView.getTxtSenha().setText("");
        acView.getTxtNome().setText("");
        acView.getTxtCurso().setText("");
        acView.getTxtSenha().setText("");
    }

    private void coordenadorSelecionadoParaEditView() {
        ecView.getTxtId().setText(c.getId().toString());
        ecView.getTxtNome().setText(c.getNome());
        ecView.getTxtCurso().setText(c.getCurso());
        ecView.getTxtSenha().setText(c.getSenha());
    }

    private void excluirCoordenador() {
        CoordenadorDao dao = new CoordenadorDaoJdbc();
        dao.excluir(c);
        c = null;
        popularTabelaCoordenador();
    }

    private void editarCoordenador() {
        editViewParaCoordenadorSelecionado();
        CoordenadorDao dao = new CoordenadorDaoJdbc();
        dao.salvar(c);
        c = null;
        popularTabelaCoordenador();
        ecView.setVisible(false);
    }

    private void novoCoordenador() {
        Coordenador umNovoCoordenador = new Coordenador();
        umNovoCoordenador.setNome(acView.getTxtNome().getText());
        umNovoCoordenador.setCurso(acView.getTxtCurso().getText());
        umNovoCoordenador.setSenha(acView.getTxtSenha().getText());
        CoordenadorDao dao = new CoordenadorDaoJdbc();
        dao.salvar(umNovoCoordenador);
        popularTabelaCoordenador();
    }

    private void coordenadorSelecionadoParaLogin() {
        int posicaoSelecionada = lgcView.getTabelaCoordenadores().getSelectedRow();
        if (posicaoSelecionada > -1) {
            c = ((CoordenadorLoginTableModel) (lgcView.getTabelaCoordenadores().getModel())).getCoordenador().get(posicaoSelecionada);
        } else {
            c = null;
        }
    }

    private void loginCoordenador() {
        if (c == null) {
            JOptionPane.showMessageDialog(null, "Selecione uma linha na tabela!");
        } else {
            if (c.getSenha() == null ? lgcView.getTxtSenha().getText()
                    == null : c.getSenha().equals(lgcView.getTxtSenha().getText())) {
                raView.setVisible(true);
                lgcView.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "A senha não coincide com o coordenador selecionado!");
            }
        }
    }

    private void editViewParaCoordenadorSelecionado() {
        c.setNome(ecView.getTxtNome().getText());
        c.setCurso(ecView.getTxtCurso().getText());
        c.setSenha(ecView.getTxtSenha().getText());
    }

}
