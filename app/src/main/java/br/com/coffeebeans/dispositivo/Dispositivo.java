package br.com.coffeebeans.dispositivo;

/**
 * Created by swenilton on 08/11/15.
 */
public class Dispositivo {

    private int id;
    private String nome;
    private String host;
    private int porta;

    public Dispositivo(String host, int porta, String nome) {
        this.host = host;
        this.porta = porta;
        this.nome = nome;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
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

    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    @Override
    public String toString() {
        return "Dispositivo{" +
                "host='" + host + '\'' +
                ", id=" + id +
                ", nome='" + nome + '\'' +
                ", porta=" + porta +
                '}';
    }
}
