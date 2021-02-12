package amsi.dei.estg.ipleiria.grestauranteapp.listeners;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Carrinho;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Pedido;

public interface CarrinhoListener {
    void onRefreshListaItemsCarrinho(ArrayList<Carrinho> itemsCarrinho);

    void onAddItems();

    //Estado : ATUALIZADO OU APAGADO
    void onDetalhes(boolean estado);

}
