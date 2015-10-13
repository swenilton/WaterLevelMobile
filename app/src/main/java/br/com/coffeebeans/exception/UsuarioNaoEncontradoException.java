package br.com.coffeebeans.exception;

public class UsuarioNaoEncontradoException extends Exception {

	public UsuarioNaoEncontradoException() {
		super("O usuario nao foi encontrado");
	}

}
