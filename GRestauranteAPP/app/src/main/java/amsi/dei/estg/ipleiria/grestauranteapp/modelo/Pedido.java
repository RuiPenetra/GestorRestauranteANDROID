package amsi.dei.estg.ipleiria.grestauranteapp.modelo;

import java.util.Date;

public class Pedido {

    private int id, id_utilizador, id_mesa,estado,tipo;
    private String nome_pedido, nota, data;

    public int getId() {
        return id;
    }

    public int getId_utilizador() {
        return id_utilizador;
    }

    public int getId_mesa() {
        return id_mesa;
    }

    public String getNome_pedido() {
        return nome_pedido;
    }

    public String getNota() {
        return nota;
    }

    public int getEstado() {
        return estado;
    }

    public int getTipo() {
        return tipo;
    }

    public String getData() {
        return data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId_utilizador(int id_utilizador) {
        this.id_utilizador = id_utilizador;
    }

    public void setId_mesa(int id_mesa) {
        this.id_mesa = id_mesa;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setNome_pedido(String nome_pedido) {
        this.nome_pedido = nome_pedido;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Pedido(int id, int id_utilizador, int id_mesa, String nome_pedido, String nota, int estado, int tipo, String data) {
        this.id = id;
        this.id_utilizador = id_utilizador;
        this.id_mesa = id_mesa;
        this.nome_pedido = nome_pedido;
        this.nota = nota;
        this.estado = estado;
        this.tipo = tipo;
        this.data = data;
    }
}
