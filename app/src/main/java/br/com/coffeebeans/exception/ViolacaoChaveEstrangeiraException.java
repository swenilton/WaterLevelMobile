package br.com.coffeebeans.exception;

public class ViolacaoChaveEstrangeiraException extends Exception {

	public ViolacaoChaveEstrangeiraException() {
		super(
				"Voc� est� tentando gravar na tabela filha sem que haja refer�ncia na tabela pai.");
	}
}
