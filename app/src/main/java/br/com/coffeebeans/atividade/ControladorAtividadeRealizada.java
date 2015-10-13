package br.com.coffeebeans.atividade;

public class ControladorAtividadeRealizada {
	private IAtividadeRealizadaDAO iAtividadeRealizada;

	public ControladorAtividadeRealizada() throws Exception {
		//this.iAtividadeRealizada = new AtividadeRealizadaDAO();
	}

	/*public void cadastrar(AtividadeRealizada atividadeRealizada)
			throws SQLException, AtividadeJaExistenteException,
			ViolacaoChaveEstrangeiraException, AtividadeNaoEncontradaException,
			RepositorioException {

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
					"Impossível a data hora de inicio ser maior ou igual a data hora fim");

		}
		iAtividadeRealizada.cadastrar(atividadeRealizada);
	}

	public List<AtividadeRealizada> listar() throws SQLException,
			ListaVaziaException, RepositorioException {
		List<AtividadeRealizada> ar = iAtividadeRealizada.listar();
		List<AtividadeRealizada> ar2 = new ArrayList<AtividadeRealizada>();
		try {
			Fachada f = Fachada.getInstance();
			for (AtividadeRealizada atividadeRealizada : ar) {
				ar2.add(new AtividadeRealizada(
						f.atividadeProcurar(atividadeRealizada.getIdAtividade()),
						atividadeRealizada.getDataHoraInicio(),
						atividadeRealizada.getDataHoraFim(), f
								.usuarioProcurar(atividadeRealizada
										.getIdUsuario()), atividadeRealizada
								.getGasto()));
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
			Fachada f = Fachada.getInstance();
			for (AtividadeRealizada atividadeRealizada : ar) {
				ar2.add(new AtividadeRealizada(
						f.atividadeProcurar(atividadeRealizada.getIdAtividade()),
						atividadeRealizada.getDataHoraInicio(),
						atividadeRealizada.getDataHoraFim(), f
								.usuarioProcurar(atividadeRealizada
										.getIdUsuario()), atividadeRealizada
								.getGasto()));
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

	public AtividadeRealizada procurar(String descricao) throws SQLException,
			AtividadeNaoEncontradaException, RepositorioException {
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
					"Impossível a data hora de inicio ser maior ou igual a data hora fim");

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
			Fachada f = Fachada.getInstance();
			for (AtividadeRealizada atividadeRealizada : ar) {
				ar2.add(new AtividadeRealizada(
						f.atividadeProcurar(atividadeRealizada.getIdAtividade()),
						atividadeRealizada.getDataHoraInicio(),
						atividadeRealizada.getDataHoraFim(), f
								.usuarioProcurar(atividadeRealizada
										.getIdUsuario()), atividadeRealizada
								.getGasto()));
			}
		} catch (Exception e) {
			throw new RepositorioException(e);
		}
		return ar2;
	}*/
}
