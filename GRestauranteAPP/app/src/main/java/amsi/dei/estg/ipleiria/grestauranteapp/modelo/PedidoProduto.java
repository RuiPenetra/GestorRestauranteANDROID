package amsi.dei.estg.ipleiria.grestauranteapp.modelo;

public class PedidoProduto {

    private int id, id_produto, quantidade,id_pedido,estado;
    private Float preco;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Float getPreco() {
        return preco;
    }

    public void setPreco(Float preco) {
        this.preco = preco;
    }

    public PedidoProduto(int id, int id_produto, int id_pedido, int quantidade, float preco, int estado) {
        this.id = id;
        this.id_produto = id_produto;
        this.id_pedido = id_pedido;
        this.preco = preco;
        this.quantidade = quantidade;
        this.estado = estado;
    }
}
