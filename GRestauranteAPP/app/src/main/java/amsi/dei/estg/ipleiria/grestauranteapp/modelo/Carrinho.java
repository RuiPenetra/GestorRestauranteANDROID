package amsi.dei.estg.ipleiria.grestauranteapp.modelo;

public class Carrinho {

    private int id,id_utilizador, id_produto, id_categoria,quantidade;
    private String nome_produto, preco;
    private static int autoIncrementedId=1;

    public int getId_utilizador() {
        return id_utilizador;
    }

    public int getId_produto() {
        return id_produto;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public String getNome_produto() {
        return nome_produto;
    }

    public int getId() {
        return id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getPreco() {
        return preco;
    }

    public void setId_utilizador(int id_utilizador) {
        this.id_utilizador = id_utilizador;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public void setNome_produto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public Carrinho(int id,int id_utilizador, int id_produto, int id_categoria, String nome_produto, int quantidade, String preco) {
        this.id=id;
        this.id_utilizador = id_utilizador;
        this.id_produto = id_produto;
        this.id_categoria = id_categoria;
        this.nome_produto = nome_produto;
        this.quantidade = quantidade;
        this.preco = preco;
    }
}
