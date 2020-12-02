package amsi.dei.estg.ipleiria.grestauranteapp.views.modelos;

public class Produto {

    private int id;
    private String nome, ingredientes, categoria;
    private float preco;

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public String getCategoria() {
        return categoria;
    }

    public float getPreco() {
        return preco;
    }

    public Produto(int id, String nome, String ingredientes, String categoria, float preco) {
        this.id = id;
        this.nome = nome;
        this.ingredientes = ingredientes;
        this.categoria = categoria;
        this.preco = preco;
    }
}
