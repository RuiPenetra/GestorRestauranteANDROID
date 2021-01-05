package amsi.dei.estg.ipleiria.grestauranteapp.listeners;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Pedido;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Perfil;

public interface PedidosAtivosListener {
    void onRefreshPedidosAtivos(ArrayList<Pedido> pedidosAtivos);
    void onRefreshPedidosUpdate();
}
