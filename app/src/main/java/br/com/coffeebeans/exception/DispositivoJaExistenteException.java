package br.com.coffeebeans.exception;

public class DispositivoJaExistenteException extends Exception {

	public DispositivoJaExistenteException() {
		super("O dispositivo ja existe");
	}
}
