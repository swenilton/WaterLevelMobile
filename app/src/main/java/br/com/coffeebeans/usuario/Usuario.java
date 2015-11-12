package br.com.coffeebeans.usuario;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Usuario {
    private int id;
    private String nome;
    private String login;
    private String senha;
    private String email;
    private String telefone;
    private String ativo;
    private String foto;
    private String perfil;

    public Usuario(String nome, String login, String senha, String email,
                   String ativo, String perfil) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.email = email;
        this.ativo = ativo;
        this.perfil = perfil;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", nome=" + nome + ", login=" + login
                + ", senha=" + senha + ", email=" + email + ", telefone="
                + telefone + ", ativo=" + ativo + ", foto=" + foto
                + ", perfil=" + perfil + "]";
    }

    public Usuario() {

    }
}
