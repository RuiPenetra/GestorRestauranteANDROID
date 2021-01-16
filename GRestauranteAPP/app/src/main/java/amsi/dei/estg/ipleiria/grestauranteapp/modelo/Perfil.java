package amsi.dei.estg.ipleiria.grestauranteapp.modelo;

import java.util.Date;

public class Perfil {

    private int id,genero;
    private String username, nova_password, email, nome,apelido,morada, nacionalidade, cargo, codigo_postal,  telemovel, datanascimento;

    public int getId() {
        return id;
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

//    public String getAcess_token() {
//        return acess_token;
//    }

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

    public String getNovaPassword() {
        return nova_password;
    }

    public String getCargo() {
        return cargo;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public int getGenero() {
        return genero;
    }

    public String getTelemovel() {
        return telemovel;
    }

    public String getDatanascimento() {
        return datanascimento;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNova_password(String nova_password) {
        this.nova_password = nova_password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

    public void setTelemovel(String telemovel) {
        this.telemovel = telemovel;
    }

    public void setDatanascimento(String datanascimento) {
        this.datanascimento = datanascimento;
    }

    public Perfil(int id, String username, String nova_password, String email, String nome, String apelido, String morada, String nacionalidade, String cargo, String codigo_postal, int genero, String telemovel, String datanascimento) {

        this.id = id;
        this.nome = nome;
        this.apelido = apelido;
        this.morada = morada;
        this.nacionalidade = nacionalidade;
        this.codigo_postal = codigo_postal;
        this.genero = genero;
        this.datanascimento = datanascimento;
        this.telemovel = telemovel;
        this.cargo = cargo;
        this.username = username;
        this.email = email;
        this.nova_password = nova_password;
    }

}
