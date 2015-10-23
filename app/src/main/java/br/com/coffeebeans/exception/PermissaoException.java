package br.com.coffeebeans.exception;

/**
 * Created by AndréFillipe on 23/10/2015.
 */
public class PermissaoException extends Exception {
    public PermissaoException() {
        super("Voce nao tem o perfil necessario para a realizacao dessa acao");
    }
}
