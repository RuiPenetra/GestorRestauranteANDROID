package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PedidosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Pedido;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;

public class TabListaPedidosAtivos extends Fragment implements SwipeRefreshLayout.OnRefreshListener, PedidosListener {

    private ListView lvListaPedidos;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int TAB_PEDIDO_ATIVOS=0;

    public TabListaPedidosAtivos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lista_pedidos_ativos, container, false);

        lvListaPedidos = view.findViewById(R.id.lvListaPedidosAtivos);
        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayoutPedidosAtivos);
        swipeRefreshLayout.setOnRefreshListener(this);

        SingletonGestorRestaurante.getInstance(getContext()).setPedidosListener(this);
        SingletonGestorRestaurante.getInstance(getContext()).getPedidosAPI(getContext());

        return view;
    }


    @Override
    public void onRefresh() {
        SingletonGestorRestaurante.getInstance(getContext()).getPedidosAPI(getContext());
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshListaPedidos(ArrayList<Pedido> pedidos) {
        SingletonGestorRestaurante.getInstance(getContext()).getPedidosAPI(getContext());
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onCreatePedido() {

    }

    @Override
    public void onDeletePedido() {
        //empty

    }
}