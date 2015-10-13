package br.com.coffeebeans.exception;

public class ViolacaoChaveEstrangeiraException extends Exception {

	public ViolacaoChaveEstrangeiraException() {
		super(
				"Voce esta tentando gravar na tabela filha sem que haja referencia na tabela pai.");
	}
}
