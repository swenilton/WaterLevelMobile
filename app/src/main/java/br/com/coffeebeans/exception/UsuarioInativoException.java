package br.com.coffeebeans.exception;

import br.com.coffeebeans.usuario.Usuario;

public class UsuarioInativoException extends Exception {

	public UsuarioInativoException(Usuario u) {
		super("Usuário '" + u.getLogin() + "' inativo");
	}

}
