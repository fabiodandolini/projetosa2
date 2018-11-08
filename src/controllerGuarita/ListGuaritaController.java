package controllerGuarita;

import controllerAutorizacao.AutorizacaoTableModel;
import controllerAutorizacao.RegistroAutorizacoes;
import dao.AutorizacaoDao;
import dao.AutorizacaoJdbc;
import entidade.Aluno;
import entidade.Autorizacao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ListGuaritaController implements ActionListener {

    private GuaritaView gView;
    private Autorizacao autorizacao;
    private Aluno aluno;
    private RegistroAutorizacoes raView;

    public ListGuaritaController(GuaritaView gView) {
        this.gView = gView;
    }

    public void iniciar() {
        construirEAssinarEventos();
    }

    private void construirEAssinarEventos() {
        popularTabela();
        gView.getBtnRefresh().addActionListener(this);
        gView.getBtnAtualizar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton botao = (JButton) e.getSource();
        if (botao == gView.getBtnRefresh()) {
            selecionarNaTabela();
            int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente liberar este aluno?");
            if (resposta == JOptionPane.YES_OPTION) {
                if(autorizacao.getSaida() != null){
                    JOptionPane.showMessageDialog(null, "Este aluno já foi liberado!");
                }else{
                operacao();
            }
        }
        }
        if (botao == gView.getBtnAtualizar()) {
            popularTabela();
        }
    }

    private void popularTabela() {
        AutorizacaoDao dao = new AutorizacaoJdbc();
        AutorizacaoTableModel atm = new AutorizacaoTableModel();
        atm.setAutorizacoes(dao.getAll());
        gView.getTabelaGuarita().setModel(atm);
    }

    private void selecionarNaTabela() {
        int posicaoSelecionada = gView.getTabelaGuarita().getSelectedRow();
        if (posicaoSelecionada > -1) {
            autorizacao = ((AutorizacaoTableModel) (gView.getTabelaGuarita().getModel())).getAutorizacoes().get(posicaoSelecionada);
        } else {
            autorizacao = null;
        }
    }

    private void operacao() {
        AutorizacaoDao dao = new AutorizacaoJdbc();
        dao.salvar(autorizacao);
        popularTabela();
    }

}
