package br.com.coffeebeans.exception;

public class AtividadeJaExistenteException extends Exception {

	public AtividadeJaExistenteException() {
		super("A atividade ja existe");
	}
}
