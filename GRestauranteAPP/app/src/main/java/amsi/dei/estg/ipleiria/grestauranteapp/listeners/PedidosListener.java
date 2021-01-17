package amsi.dei.estg.ipleiria.grestauranteapp.listeners;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Pedido;

public interface PedidosListener {
    void onRefreshListaPedidos(ArrayList<Pedido> pedidos);

    void onCreatePedido();

}
