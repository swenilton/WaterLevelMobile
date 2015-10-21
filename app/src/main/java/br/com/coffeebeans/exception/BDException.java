package br.com.coffeebeans.exception;

/**
 * Created by AndréFillipe on 16/10/2015.
 */
public class BDException extends Exception {

public BDException() {
  super("banco nao existe ou nao foi aberto");
}
}
