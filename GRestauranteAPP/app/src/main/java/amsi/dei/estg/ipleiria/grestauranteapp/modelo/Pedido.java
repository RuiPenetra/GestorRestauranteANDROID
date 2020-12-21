package amsi.dei.estg.ipleiria.grestauranteapp.modelo;

import java.util.Date;

public class Pedido {

    private int id, id_utilizador, id_mesa;
    private String nome_pedido, nota, estado, tipo;
    private Date data;

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

    public String getEstado() {
        return estado;
    }

    public String getTipo() {
        return tipo;
    }

    public Date getData() {
        return data;
    }

    public Pedido(int id, int id_utilizador, int id_mesa, String nome_pedido, String nota, String estado, String tipo, Date data) {
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
