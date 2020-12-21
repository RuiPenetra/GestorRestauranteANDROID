package amsi.dei.estg.ipleiria.grestauranteapp.modelo;

import java.util.Date;

public class Utilizador {

    private int id, status;
    private String username,email,acess_token,nome,apelido,morada, nacionalidade, password, cargo, codigo_postal, genero, telemovel;
    private Date datanascimento;

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getAcess_token() {
        return acess_token;
    }

    public String getNome() {
        return nome;
    }

    public String getApelido() {
        return apelido;
    }

    public String getMorada() {
        return morada;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public String getPassword() {
        return password;
    }

    public String getCargo() {
        return cargo;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public String getGenero() {
        return genero;
    }

    public String getTelemovel() {
        return telemovel;
    }

    public Date getDatanascimento() {
        return datanascimento;
    }

    public Utilizador(int id, int status, String username, String email, String acess_token, String nome, String apelido, String morada, String nacionalidade, String password, String cargo, String codigo_postal, String genero, String telemovel, Date datanascimento) {

        this.id = id;
        this.status = status;
        this.username = username;
        this.email = email;
        this.acess_token = acess_token;
        this.nome = nome;
        this.apelido = apelido;
        this.morada = morada;
        this.nacionalidade = nacionalidade;
        this.password = password;
        this.cargo = cargo;
        this.codigo_postal = codigo_postal;
        this.genero = genero;
        this.telemovel = telemovel;
        this.datanascimento = datanascimento;
    }

}
