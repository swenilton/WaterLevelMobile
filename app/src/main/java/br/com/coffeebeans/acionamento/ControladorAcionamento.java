package br.com.coffeebeans.acionamento;

import android.content.Context;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.coffeebeans.exception.AcionamentoJaExistenteException;
import br.com.coffeebeans.exception.AcionamentoNaoEncontradoException;
import br.com.coffeebeans.exception.ListaVaziaException;
import br.com.coffeebeans.exception.RepositorioException;

public class ControladorAcionamento {
	private IAcionamentoDAO iAcionamento;

	public ControladorAcionamento(Context context) throws Exception {
		this.iAcionamento = new AcionamentoDaoWs();
	}

	public void cadastrar(Acionamento acionamento) throws SQLException,
			AcionamentoNaoEncontradoException, AcionamentoJaExistenteException,
			RepositorioException {
		if (acionamento == null) {
			throw new NullPointerException();
		}
		if (iAcionamento.procurar(acionamento.getId()) != null) {
			throw new AcionamentoJaExistenteException();
		}
		// se ja tiver um acionamento nesse instante
		if (iAcionamento.procurarIni(acionamento.getDataHoraInicio(),
				acionamento.getDataHoraInicio()) != null) {
			throw new AcionamentoJaExistenteException();
		}
		if (acionamento.getDataHoraInicio().after(acionamento.getDataHoraFim())
				|| acionamento.getDataHoraInicio().equals(
						acionamento.getDataHoraFim())) {
			throw new IllegalArgumentException(
					"Impossivel a data hora de inicio ser maior ou igual a data hora fim");
		}
		iAcionamento.cadastrar(acionamento);
	}

	public List<Acionamento> listar() throws SQLException,
			ListaVaziaException, RepositorioException {
		List<Acionamento> acs = iAcionamento.listar();
		try {
			for (Acionamento ac : acs) {
				//ac.setBomba(Fachada.getInstance()
						//.bombaProcurar(ac.getIdBomba()));
			}
		} catch (Exception e) {
			throw new RepositorioException(e);
		}
		return acs;
	}

	public List<Acionamento> getUltimosAcionamentos() throws SQLException,
			ListaVaziaException, RepositorioException {
		List<Acionamento> acs = iAcionamento.getUltimosAcionamentos();
		try {
			for (Acionamento ac : acs) {
				//ac.setBomba(Fachada.getInstance()
						//.bombaProcurar(ac.getIdBomba()));
			}
		} catch (Exception e) {
			throw new RepositorioException(e);
		}
		return acs;
	}

	public Acionamento procurar(int id)
			throws AcionamentoNaoEncontradoException, SQLException,
			RepositorioException {
		if (iAcionamento.procurar(id) == null) {
			throw new AcionamentoNaoEncontradoException();
		}
		return iAcionamento.procurar(id);

	}

	public Acionamento procurarIni(Date data1, Date data2)
			throws SQLException, AcionamentoNaoEncontradoException,
			RepositorioException {
		if (iAcionamento.procurarIni(data1, data2) == null) {
			throw new AcionamentoNaoEncontradoException();
		}
		return iAcionamento.procurarIni(data1, data2);

	}

	public Acionamento procurarFim(Date data1, Date data2)
			throws SQLException, AcionamentoNaoEncontradoException,
			RepositorioException {
		if (iAcionamento.procurarIni(data1, data2) == null) {
			throw new AcionamentoNaoEncontradoException();
		}
		return iAcionamento.procurarFim(data1, data2);

	}

	public void atualizar(Acionamento acionamento) throws SQLException,
			AcionamentoNaoEncontradoException, AcionamentoJaExistenteException,
			RepositorioException {
		if (acionamento == null) {
			throw new NullPointerException();
		}
		if (iAcionamento.procurar(acionamento.getId()) == null) {
			throw new AcionamentoNaoEncontradoException();
		}
		// se j� tiver um acionamento nesse instante
		if (iAcionamento.procurarIni(acionamento.getDataHoraInicio(),
				acionamento.getDataHoraInicio()) != null) {
			throw new AcionamentoJaExistenteException();
		}
		if (acionamento.getDataHoraInicio().after(acionamento.getDataHoraFim())
				|| acionamento.getDataHoraInicio().equals(
						acionamento.getDataHoraFim())) {
			throw new IllegalArgumentException(
					"Imposs�vel a data hora de inicio ser maior ou igual a data hora fim");
		}
		iAcionamento.atualizar(acionamento);
	}

	public void excluir(int id) throws SQLException,
			AcionamentoNaoEncontradoException, RepositorioException {
		if (iAcionamento.procurar(id) == null) {
			throw new AcionamentoNaoEncontradoException();
		} else {
			iAcionamento.excluir(id);
		}
	}

}
