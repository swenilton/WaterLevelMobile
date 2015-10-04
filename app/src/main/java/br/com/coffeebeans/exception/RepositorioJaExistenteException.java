package br.com.coffeebeans.exception;

public class RepositorioJaExistenteException extends Exception {

public RepositorioJaExistenteException () {
 super("O repositório já existe!");	
}
}
