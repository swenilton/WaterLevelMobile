package br.com.coffeebeans.exception;

public class BombaJaExistenteException extends Exception {
	public BombaJaExistenteException() {
		super("A bomba já existe");
	}
}
