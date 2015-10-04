package br.com.coffeebeans.exception;

public class ViolacaoChaveEstrangeiraException extends Exception {

	public ViolacaoChaveEstrangeiraException() {
		super(
				"Você está tentando gravar na tabela filha sem que haja referência na tabela pai.");
	}
}
