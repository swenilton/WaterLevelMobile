package br.com.coffeebeans.exception;

public class ListaUsuarioVaziaException extends Exception {

	public ListaUsuarioVaziaException() {
		super("A lista de usu�rios est� vazia");
	}
}
