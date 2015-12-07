package br.com.coffeebeans.usuario;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Login {
	private int idUser;
	private String senha;

	public Login(int idUser, String senha) {
		this.idUser = idUser;
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "Login [idUser=" + idUser + ", senha=" + senha + "]";
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Login() {
	}

}
