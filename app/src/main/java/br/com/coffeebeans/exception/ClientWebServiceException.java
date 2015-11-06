package br.com.coffeebeans.exception;

/**
 * Created by AndréFillipe on 06/11/2015.
 */
public class ClientWebServiceException extends Exception {

    public ClientWebServiceException(Exception e) {
        super("Erro na comunicacao com o web service  "+e.getMessage());
    }
}
