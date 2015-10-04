package br.com.coffeebeans.fachada;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.coffeebeans.acionamento.Acionamento;
import br.com.coffeebeans.acionamento.ControladorAcionamento;
import br.com.coffeebeans.atividade.Atividade;
import br.com.coffeebeans.atividade.AtividadeRealizada;
import br.com.coffeebeans.atividade.ControladorAtividade;
import br.com.coffeebeans.atividade.ControladorAtividadeRealizada;
import br.com.coffeebeans.bomba.Bomba;
import br.com.coffeebeans.bomba.ControladorBomba;
import br.com.coffeebeans.exception.AcionamentoJaExistenteException;
import br.com.coffeebeans.exception.AcionamentoNaoEncontradoException;
import br.com.coffeebeans.exception.AtividadeJaExistenteException;
import br.com.coffeebeans.exception.AtividadeNaoEncontradaException;
import br.com.coffeebeans.exception.BombaJaExistenteException;
import br.com.coffeebeans.exception.BombaNaoEncontradaException;
import br.com.coffeebeans.exception.ListaUsuarioVaziaException;
import br.com.coffeebeans.exception.ListaVaziaException;
import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.exception.RepositorioJaExistenteException;
import br.com.coffeebeans.exception.RepositorioNaoEncontradoException;
import br.com.coffeebeans.exception.UsuarioInativoException;
import br.com.coffeebeans.exception.UsuarioJaExistenteException;
import br.com.coffeebeans.exception.UsuarioNaoEncontradoException;
import br.com.coffeebeans.exception.ViolacaoChaveEstrangeiraException;
import br.com.coffeebeans.repositorio.ControladorRepositorio;
import br.com.coffeebeans.repositorio.Repositorio;
import br.com.coffeebeans.usuario.ControladorUsuario;
import br.com.coffeebeans.usuario.Usuario;

public class Fachada {
	private static Fachada instance = null;
	ControladorRepositorio controladorRepositorio;
	ControladorUsuario controladorUsuario;
	ControladorBomba controladorBomba;
	ControladorAcionamento controladorAcionamento;
	ControladorAtividade controladorAtividade;
	ControladorAtividadeRealizada controladorAtividadeRealizada;

	private Fachada() throws Exception {
		this.controladorRepositorio = new ControladorRepositorio();
		this.controladorUsuario = new ControladorUsuario();
		this.controladorBomba = new ControladorBomba();
		this.controladorAcionamento = new ControladorAcionamento();
		this.controladorAtividade = new ControladorAtividade();
		this.controladorAtividadeRealizada = new ControladorAtividadeRealizada();

	}

	public static Fachada getInstance() throws Exception {
		if (Fachada.instance == null) {
			Fachada.instance = new Fachada();
		}
		return Fachada.instance;
	}

	public <E> void cadastrar(E element) throws SQLException,
			RepositorioJaExistenteException, RepositorioNaoEncontradoException,
			UsuarioJaExistenteException, UsuarioNaoEncontradoException,
			RepositorioException, BombaJaExistenteException,
			BombaNaoEncontradaException, ViolacaoChaveEstrangeiraException,
			AcionamentoNaoEncontradoException, AcionamentoJaExistenteException,
			AtividadeJaExistenteException, AtividadeNaoEncontradaException {
		if (element instanceof Repositorio) {
			controladorRepositorio.cadastrar((Repositorio) element);
		} else if (element instanceof Usuario) {
			controladorUsuario.cadastrar((Usuario) element);
		} else if (element instanceof Bomba) {
			controladorBomba.cadastrar((Bomba) element);
		} else if (element instanceof Acionamento) {
			controladorAcionamento.cadastrar((Acionamento) element);
		} else if (element instanceof Atividade) {
			controladorAtividade.cadastrar((Atividade) element);
		} else if (element instanceof AtividadeRealizada) {
			controladorAtividadeRealizada
					.cadastrar((AtividadeRealizada) element);
		}

	}

	public <E> void atualizar(E element) throws SQLException,
			RepositorioNaoEncontradoException, UsuarioNaoEncontradoException,
			RepositorioException, BombaNaoEncontradaException,
			AcionamentoNaoEncontradoException, AcionamentoJaExistenteException,
			AtividadeNaoEncontradaException {
		if (element instanceof Repositorio) {
			controladorRepositorio.atualizar((Repositorio) element);
		} else if (element instanceof Usuario) {
			controladorUsuario.atualizar((Usuario) element);
		} else if (element instanceof Bomba) {
			controladorBomba.atualizar((Bomba) element);
		} else if (element instanceof Acionamento) {
			controladorAcionamento.atualizar((Acionamento) element);
		} else if (element instanceof Atividade) {
			controladorAtividade.atualizar((Atividade) element);
		} else if (element instanceof AtividadeRealizada) {
			controladorAtividadeRealizada
					.atualizar((AtividadeRealizada) element);
		}

	}

	public void atividadeRealizadaRemover(int id)
			throws AtividadeNaoEncontradaException, SQLException,
			RepositorioException {
		controladorAtividadeRealizada.excluir(id);
	}

	public void acionamentoRemover(int id) throws SQLException,
			AcionamentoNaoEncontradoException, RepositorioException {
		controladorAcionamento.excluir(id);
	}

	public void bombaRemover(int id) throws SQLException,
			BombaNaoEncontradaException {
		controladorBomba.remover(id);
	}

	public void repositorioRemover(int id) throws SQLException,
			RepositorioNaoEncontradoException {
		controladorRepositorio.remover(id);
	}

	public void usuarioRemover(int id) throws SQLException,
			UsuarioNaoEncontradoException, RepositorioException {
		controladorUsuario.remover(id);
	}

	public void atividadeRemover(int id) throws SQLException,
			AtividadeNaoEncontradaException, RepositorioException {
		controladorAtividade.remover(id);
	}

	public List<AtividadeRealizada> atividadeRealizadaListar()
			throws SQLException, ListaVaziaException, RepositorioException {
		return controladorAtividadeRealizada.listar();
	}

	public List<AtividadeRealizada> atividadeRealizadaListar(int id)
			throws SQLException, ListaVaziaException, RepositorioException {
		return controladorAtividadeRealizada.listar(id);
	}

	public ArrayList<Acionamento> acionamentoListar() throws SQLException,
			ListaVaziaException, RepositorioException {
		return controladorAcionamento.listar();
	}

	public ArrayList<Acionamento> getUltimosAcionamentos() throws SQLException,
			ListaVaziaException, RepositorioException {
		return controladorAcionamento.getUltimosAcionamentos();
	}

	public ArrayList<Bomba> bombaListar() throws SQLException,
			ListaVaziaException {
		return controladorBomba.listar();
	}

	public ArrayList<Repositorio> repositorioListar() throws SQLException,
			RepositorioException {
		return controladorRepositorio.listar();

	}

	public List<Atividade> atividadeListar() throws SQLException,
			ListaVaziaException, RepositorioException,
			ListaUsuarioVaziaException {
		return controladorAtividade.listar();
	}

	public List<Usuario> getUsuarioLista() throws SQLException,
			RepositorioException {
		return controladorUsuario.getLista();

	}

	public AtividadeRealizada atividadeRealizadaProcurar(int id)
			throws SQLException, AtividadeNaoEncontradaException,
			RepositorioException {
		return controladorAtividadeRealizada.procurar(id);
	}

	public Acionamento acionamentoProcurar(int id)
			throws AcionamentoNaoEncontradoException, SQLException,
			RepositorioException {
		return controladorAcionamento.procurar(id);
	}

	public Bomba bombaProcurar(int id) throws SQLException,
			BombaNaoEncontradaException {
		return controladorBomba.procurar(id);
	}

	public Bomba bombaProcurarPorRepositorio(int idRepositorio) throws SQLException,
			BombaNaoEncontradaException {
		return controladorBomba.procurarPorRepositorio(idRepositorio);
	}

	public Repositorio repositorioProcurar(int id) throws SQLException,
			RepositorioNaoEncontradoException {
		return controladorRepositorio.procurar(id);
	}

	public Usuario usuarioProcurar(int id) throws SQLException,
			UsuarioNaoEncontradoException, RepositorioException {
		return controladorUsuario.procurar(id);
	}

	public Atividade atividadeProcurar(int id) throws SQLException,
			AtividadeNaoEncontradaException, RepositorioException {
		return controladorAtividade.procurar(id);
	}

	public Usuario loginFacebook(String email) throws RepositorioException,
			SQLException {
		return controladorUsuario.loginFacebook(email);
	}

	public void alterarSenhaUsuario(int id, String senha) throws SQLException,
			UsuarioNaoEncontradoException, RepositorioException {
		controladorUsuario.alterarSenha(id, senha);
	}

	public boolean login(String usuario, String senha)
			throws UsuarioInativoException, RepositorioException, SQLException {
		return controladorUsuario.login(usuario, senha);
	}

	public List<AtividadeRealizada> getUltimasAtividades()
			throws RepositorioException, SQLException {
		return controladorAtividadeRealizada.getUltimasAtividades();
	}

}
