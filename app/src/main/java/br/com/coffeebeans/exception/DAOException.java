package br.com.coffeebeans.exception;

/**
 * Created by AndréFillipe on 16/10/2015.
 */
public class DAOException extends Exception {
    public DAOException(Exception e) {
    super("Erro no DAO\n"+e.getMessage());
    }
}
