package amsi.dei.estg.ipleiria.grestauranteapp.modelo;

public class Produto {

    private int id,categoria;
    private String nome, ingredientes,preco;

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public int getCategoria() {
        return categoria;
    }

    public String getPreco() {
        return preco;
    }

    public Produto(int id, String nome, String ingredientes, String preco, int categoria) {
        this.id = id;
        this.nome = nome;
        this.ingredientes = ingredientes;
        this.categoria = categoria;
        this.preco = preco;
    }
}
