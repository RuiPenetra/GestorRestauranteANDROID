package amsi.dei.estg.ipleiria.grestauranteapp.views.modelos;

public class PedidoProduto {

    private int id, id_produto, id_utilizador, quantidade;
    private String estado;

    public int getId() {
        return id;
    }

    public int getId_produto() {
        return id_produto;
    }

    public int getId_utilizador() {
        return id_utilizador;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getEstado() {
        return estado;
    }

    public PedidoProduto(int id, int id_produto, int id_utilizador, int quantidade, String estado) {
        this.id = id;
        this.id_produto = id_produto;
        this.id_utilizador = id_utilizador;
        this.quantidade = quantidade;
        this.estado = estado;
    }
}
