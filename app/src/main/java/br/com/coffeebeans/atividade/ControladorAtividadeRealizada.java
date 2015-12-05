package br.com.coffeebeans.atividade;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.coffeebeans.exception.AtividadeJaExistenteException;
import br.com.coffeebeans.exception.AtividadeNaoEncontradaException;
import br.com.coffeebeans.exception.AtividadeRealizadaJaExistenteException;
import br.com.coffeebeans.exception.DAOException;
import br.com.coffeebeans.exception.ListaVaziaException;
import br.com.coffeebeans.exception.RepositorioException;
import br.com.coffeebeans.exception.ViolacaoChaveEstrangeiraException;
import br.com.coffeebeans.fachada.Fachada;
import br.com.coffeebeans.usuario.Usuario;

public class ControladorAtividadeRealizada {
	private IAtividadeRealizadaDAO iAtividadeRealizada;
	private Context context;
	public ControladorAtividadeRealizada(Context context) throws Exception {
		this.iAtividadeRealizada = new AtividadeRealizadaDaoWs();
		this.context=context;
	}

	public void cadastrar(AtividadeRealizada atividadeRealizada)
			throws SQLException, AtividadeJaExistenteException,
			ViolacaoChaveEstrangeiraException, AtividadeNaoEncontradaException,
			RepositorioException, DAOException,
			AtividadeRealizadaJaExistenteException {

		if (atividadeRealizada == null) {
			throw new NullPointerException();
		}
		if (iAtividadeRealizada.procurar(atividadeRealizada.getId()) != null) {
			throw new AtividadeJaExistenteException();
		}
		if (atividadeRealizada.getDataHoraInicio().after(
				atividadeRealizada.getDataHoraFim())
				|| atividadeRealizada.getDataHoraInicio().equals(
						atividadeRealizada.getDataHoraFim())) {
			throw new IllegalArgumentException(
					"Impossivel a data hora de inicio ser maior ou igual a data hora fim");
		}
		if (existe(atividadeRealizada.getIdUsuario(),
				atividadeRealizada.getIdAtividade(),
				atividadeRealizada.getDataHoraInicio(),
				atividadeRealizada.getDataHoraFim())) {
			throw new AtividadeRealizadaJaExistenteException();
		}

		iAtividadeRealizada.cadastrar(atividadeRealizada);
	}

	public List<AtividadeRealizada> listar() throws SQLException,
			ListaVaziaException, RepositorioException {
		List<AtividadeRealizada> ar = iAtividadeRealizada.listar();
		List<AtividadeRealizada> ar2 = new ArrayList<AtividadeRealizada>();
		try {
			Fachada f = Fachada.getInstance(context);
			for (AtividadeRealizada atividadeRealizada : ar) {
				AtividadeRealizada obj = new AtividadeRealizada(
						f.atividadeProcurar(atividadeRealizada.getIdAtividade()),
						atividadeRealizada.getDataHoraInicio(),
						atividadeRealizada.getDataHoraFim(), f
								.usuarioProcurar(atividadeRealizada
										.getIdUsuario()), atividadeRealizada
								.getGasto());
				obj.setId(atividadeRealizada.getId());
				ar2.add(obj);
			}
		} catch (Exception e) {
			throw new RepositorioException(e);
		}
		return ar2;
	}

	public List<AtividadeRealizada> listar(int id) throws SQLException,
			ListaVaziaException, RepositorioException {
		List<AtividadeRealizada> ar = iAtividadeRealizada.listar(id);
		List<AtividadeRealizada> ar2 = new ArrayList<AtividadeRealizada>();
		try {
			Fachada f = Fachada.getInstance(context);
			for (AtividadeRealizada atividadeRealizada : ar) {
				AtividadeRealizada obj = new AtividadeRealizada(
						f.atividadeProcurar(atividadeRealizada.getIdAtividade()),
						atividadeRealizada.getDataHoraInicio(),
						atividadeRealizada.getDataHoraFim(), f
								.usuarioProcurar(atividadeRealizada
										.getIdUsuario()), atividadeRealizada
								.getGasto());
				obj.setId(atividadeRealizada.getId());
				ar2.add(obj);
			}
		} catch (Exception e) {
			throw new RepositorioException(e);
		}
		return ar2;
	}

	public AtividadeRealizada procurar(int id) throws SQLException,
			AtividadeNaoEncontradaException, RepositorioException {
		if (iAtividadeRealizada.procurar(id) == null) {
			throw new AtividadeNaoEncontradaException();
		}
		return iAtividadeRealizada.procurar(id);

	}

	public List<AtividadeRealizada> procurar(String descricao)
			throws SQLException, AtividadeNaoEncontradaException,
			RepositorioException {
		if (iAtividadeRealizada.procurar(descricao) == null) {
			throw new AtividadeNaoEncontradaException();
		}
		return iAtividadeRealizada.procurar(descricao);
	}

	public void atualizar(AtividadeRealizada atividadeRealizada)
			throws AtividadeNaoEncontradaException, SQLException,
			RepositorioException {

		if (atividadeRealizada == null) {
			throw new NullPointerException();
		}

		if (atividadeRealizada.getDataHoraInicio().after(
				atividadeRealizada.getDataHoraFim())
				|| atividadeRealizada.getDataHoraInicio().equals(
						atividadeRealizada.getDataHoraFim())) {
			throw new IllegalArgumentException(
					"Impossivel a data hora de inicio ser maior ou igual a data hora fim");

		}
		iAtividadeRealizada.atualizar(atividadeRealizada);
	}

	public void excluir(int id) throws AtividadeNaoEncontradaException,
			SQLException, RepositorioException {
		if (iAtividadeRealizada.procurar(id) == null) {
			throw new AtividadeNaoEncontradaException();
		} else {
			iAtividadeRealizada.excluir(id);
		}
	}

	public List<AtividadeRealizada> getUltimasAtividades()
			throws RepositorioException, SQLException {
		List<AtividadeRealizada> ar = iAtividadeRealizada
				.getUltimasAtividades();
		List<AtividadeRealizada> ar2 = new ArrayList<AtividadeRealizada>();
		try {
			Fachada f = Fachada.getInstance(context);
			for (AtividadeRealizada atividadeRealizada : ar) {
				AtividadeRealizada obj = new AtividadeRealizada(
						f.atividadeProcurar(atividadeRealizada.getIdAtividade()),
						atividadeRealizada.getDataHoraInicio(),
						atividadeRealizada.getDataHoraFim(), f
								.usuarioProcurar(atividadeRealizada
										.getIdUsuario()), atividadeRealizada
								.getGasto());
				obj.setId(atividadeRealizada.getId());
				ar2.add(obj);

			}
		} catch (Exception e) {
			throw new RepositorioException(e);
		}
		return ar2;
	}

	public boolean existe(int id_usuario, int id_atividade,
			Date dataHoraInicio, Date dataHoraFim) throws SQLException,
			DAOException {
		return iAtividadeRealizada.existe(id_usuario, id_atividade,
				dataHoraInicio, dataHoraFim);
	}
}
