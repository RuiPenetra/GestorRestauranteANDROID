package amsi.dei.estg.ipleiria.grestauranteapp.listeners;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Pedido;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.PedidoProduto;

public interface PedidosProdutoListener {

    void onRefreshListaPedidosProduto(ArrayList<PedidoProduto> pedidoProdutos);

    void onCriar();

    void onDetalhes();

}
