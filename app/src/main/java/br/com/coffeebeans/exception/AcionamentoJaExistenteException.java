package br.com.coffeebeans.exception;

public class AcionamentoJaExistenteException extends Exception {
	public AcionamentoJaExistenteException() {
		super("Esse acionamento já existe");
	}
}
