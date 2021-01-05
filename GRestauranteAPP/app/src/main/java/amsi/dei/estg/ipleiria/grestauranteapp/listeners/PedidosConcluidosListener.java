package amsi.dei.estg.ipleiria.grestauranteapp.listeners;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Pedido;

public interface PedidosConcluidosListener {
    void onRefreshPedidos(ArrayList<Pedido> pedidos);
    void onRefreshPedidosUpdate();
}
