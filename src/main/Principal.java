package main;

import controllerAluno.ListAlunoController;
import dao.AlunoDao;
import dao.AlunoDaoJdbc;
import controllerLogin.AcessoView;
import controllerAluno.AdicionarAlunoView;
import controllerAluno.AlunoView;
import controllerAutorizacao.RegistroAlunosView;
import controllerAluno.EditarAlunoView;
import controllerLogin.ListAcessoController;
import controllerAutorizacao.ListAutorizacaoController;
import controllerGuarita.ListGuaritaController;
import controllerAutorizacao.RegistroAutorizacoes;
import controllerCoordenador.AdicionarCoordenadorView;
import controllerCoordenador.EditCoordenadorView;
import controllerCoordenador.ListCoordenadorController;
import controllerCoordenador.ListaCoordenadorView;
import controllerCoordenador.LoginCoordenadorView;
import controllerGuarita.GuaritaView;
import controllerLogin.LoginView;
import controllerTurma.AdicionarTurmaView;
import controllerTurma.EditTurmaView;
import controllerTurma.ListTurmaController;
import controllerTurma.TurmaView;

public class Principal {

    static AlunoDao dao = new AlunoDaoJdbc();

    public static void main(String[] args) {
        // TODO code application logic here:
        LoginView lgView = new LoginView();
        AcessoView acessView = new AcessoView();
        AlunoView view = new AlunoView();
        RegistroAlunosView autoView = new RegistroAlunosView();
        GuaritaView gView = new GuaritaView();
        EditarAlunoView editView = new EditarAlunoView();
        AdicionarAlunoView adView = new AdicionarAlunoView();
        RegistroAutorizacoes raView = new RegistroAutorizacoes();
        LoginCoordenadorView lgcView = new LoginCoordenadorView();
        EditCoordenadorView ecView = new EditCoordenadorView();
        AdicionarCoordenadorView acView = new AdicionarCoordenadorView();
        ListaCoordenadorView lcView = new ListaCoordenadorView();
        TurmaView tView = new TurmaView();
        AdicionarTurmaView atView = new AdicionarTurmaView();
        EditTurmaView etView = new EditTurmaView();
        ListAlunoController controller = new ListAlunoController(view, editView, adView, autoView);
        ListAutorizacaoController controller1 = new ListAutorizacaoController(autoView, raView, lgcView);
        ListGuaritaController controller2 = new ListGuaritaController(gView);
        ListAcessoController controller3 = new ListAcessoController(acessView, gView, view, raView, lgView, lgcView, lcView,tView);
        ListCoordenadorController controller4 = new ListCoordenadorController(lcView, ecView, acView, lgView, raView,
            lgcView, acessView);
        ListTurmaController controller5 = new ListTurmaController(atView,etView,tView);
        controller2.iniciar();
        controller.iniciar();
        controller1.iniciar();
        controller3.iniciar();
        controller4.iniciar();
        controller5.iniciar();
    }
}
