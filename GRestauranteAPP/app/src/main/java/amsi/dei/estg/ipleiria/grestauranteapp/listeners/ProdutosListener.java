package amsi.dei.estg.ipleiria.grestauranteapp.listeners;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Produto;

public interface ProdutosListener {
    void onRefreshListaPordutos(ArrayList<Produto> listaProdutos);

}
