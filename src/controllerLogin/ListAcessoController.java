package controllerLogin;

import controllerAluno.AlunoTableModel;
import controllerAluno.AlunoView;
import controllerAutorizacao.AutorizacaoTableModel;
import controllerAutorizacao.RegistroAutorizacoes;
import controllerCoordenador.CoordenadorLoginTableModel;
import controllerCoordenador.ListaCoordenadorView;
import controllerCoordenador.LoginCoordenadorView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import controllerGuarita.GuaritaView;
import controllerTurma.TurmaTableModel;
import controllerTurma.TurmaView;
import dao.AlunoDao;
import dao.AlunoDaoJdbc;
import dao.AutorizacaoDao;
import dao.AutorizacaoJdbc;
import dao.CoordenadorDao;
import dao.CoordenadorDaoJdbc;
import dao.TurmaDao;
import dao.TurmaDaoJdbc;
import java.awt.Color;
import javax.swing.JOptionPane;

public class ListAcessoController implements ActionListener {

    private AcessoView acessView;
    private GuaritaView gView;
    private AlunoView view;
    private RegistroAutorizacoes raView;
    private LoginView lgView;
    private LoginCoordenadorView lgcView;
    private ListaCoordenadorView lcView;
    private TurmaView tView;

    public ListAcessoController(AcessoView acessView, GuaritaView gView, AlunoView view, RegistroAutorizacoes raView,
             LoginView lgView, LoginCoordenadorView lgcView, ListaCoordenadorView lcView, TurmaView tView) {
        this.acessView = acessView;
        this.gView = gView;
        this.view = view;
        this.raView = raView;
        this.lgView = lgView;
        this.lgcView = lgcView;
        this.lcView = lcView;
        this.tView =  tView;
    }

    public void iniciar() {
        acessView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        raView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        lgView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lcView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        construirEAssinarEventos();
        lgView.setVisible(true);
        popularTabelaAut();
        popularTabelaAluno();
        popularTabelaTurma();
        popularTabelaGuaritaAut();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton botao = (JButton) e.getSource();
        if (botao == acessView.getBtnAlunos()) {
            view.setVisible(true);
            view.getTxtId().setEditable(false);
            view.getTxtId().setBackground(Color.white);
            view.getTxtId().setForeground(Color.red);
            view.getTxtAluno().setEditable(false);
            view.getTxtAluno().setBackground(Color.white);
            view.getTxtAluno().setForeground(Color.red);
            view.getTxtCurso().setEditable(false);
            view.getTxtCurso().setBackground(Color.white);
            view.getTxtCurso().setForeground(Color.red);
            view.getTxtMatricula().setEditable(false);
            view.getTxtMatricula().setBackground(Color.white);
            view.getTxtMatricula().setForeground(Color.red);
            view.getTxtCoordenador().setEditable(false);
            view.getTxtCoordenador().setBackground(Color.white);
            view.getTxtCoordenador().setForeground(Color.red);
            view.getTxtData().setEditable(false);
            view.getTxtData().setBackground(Color.white);
            view.getTxtData().setForeground(Color.red);
            view.getTxtAtivo().setEditable(false);
            view.getTxtAtivo().setBackground(Color.white);
            view.getTxtAtivo().setForeground(Color.red);
        }
        if (botao == acessView.getBtnAutorizacoes()) {
            popularTabelaAut();
            popularTabelaCoordenadorLogin();
            lgcView.setVisible(true);
        }
        if (botao == acessView.getBtnGuarita()) {
            gView.setVisible(true);
        }
        if (botao == acessView.getBtnLogin()) {
            lcView.setVisible(true);
        }
        if (botao == lgView.getBtnAcessar()) {
            verificarSenha();
        }
        if (botao == acessView.getBtnTurmas()){
            tView.setVisible(true);
        }
    }

    private void construirEAssinarEventos() {
        acessView.getBtnAlunos().addActionListener(this);
        acessView.getBtnAutorizacoes().addActionListener(this);
        acessView.getBtnGuarita().addActionListener(this);
        acessView.getBtnLogin().addActionListener(this);
        lgView.getBtnAcessar().addActionListener(this);
        acessView.getBtnTurmas().addActionListener(this);

    }

    private void verificarSenha() {
        String valor = lgView.getTxtSenha().getText();
        if ("fabio0619".equals(valor)) {
            acessView.setVisible(true);
            lgView.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null, "Você digitou a senha errada!");
        }
    }

    private void popularTabelaAut() {
        AutorizacaoDao dao = new AutorizacaoJdbc();
        AutorizacaoTableModel atm = new AutorizacaoTableModel();
        atm.setAutorizacoes(dao.getAll());
        raView.getTabelaAutorizacoes().setModel(atm);
    }

    private void popularTabelaAluno() {
        AlunoDao dao = new AlunoDaoJdbc();
        AlunoTableModel ctm = new AlunoTableModel();
        ctm.setContatos(dao.getAll());
        view.getTabelaAluno().setModel(ctm);
    }

    private void popularTabelaGuaritaAut() {
        AutorizacaoDao dao = new AutorizacaoJdbc();
        AutorizacaoTableModel atm = new AutorizacaoTableModel();
        atm.setAutorizacoes(dao.getAll());
        gView.getTabelaGuarita().setModel(atm);
    }

    private void popularTabelaCoordenadorLogin() {
        CoordenadorDao dao = new CoordenadorDaoJdbc();
        CoordenadorLoginTableModel cltm = new CoordenadorLoginTableModel();
        cltm.setCoordenador(dao.getAll());
        lgcView.getTabelaCoordenadores().setModel(cltm);
    }

    private void popularTabelaTurma() {
        TurmaDao dao = new TurmaDaoJdbc();
        TurmaTableModel ttm = new TurmaTableModel();
        ttm.setLista(dao.getAll());
        tView.getTbTurma().setModel(ttm);
    }

}
 