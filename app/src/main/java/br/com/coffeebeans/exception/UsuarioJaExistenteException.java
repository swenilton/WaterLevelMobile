package br.com.coffeebeans.exception;

public class UsuarioJaExistenteException extends Exception {
	public UsuarioJaExistenteException() {
		super("Usu�rio j� cadastrado");
	}
}
