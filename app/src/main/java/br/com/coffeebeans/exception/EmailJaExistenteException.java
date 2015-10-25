package br.com.coffeebeans.exception;

/**
 * Created by AndréFillipe on 25/10/2015.
 */
public class EmailJaExistenteException extends Exception {

    public EmailJaExistenteException() {
        super("Email ja cadastrado");
    }
}
