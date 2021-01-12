package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.adaptadores.ListaPedidoAdaptador;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Pedido;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;


public class TabListaPedidosConcluidos extends Fragment{

    private ListView lvListaPedidosConcluidos;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int TAB_PEDIDO_CONCLUIDO=1;


    public TabListaPedidosConcluidos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lista_pedidos_concluidos, container, false);
        lvListaPedidosConcluidos = view.findViewById(R.id.lvListaPedidosConcluidos);
        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayoutPedidosConcluidos);

/*
        SingletonGestorRestaurante.getInstance(getContext()).setPedidosConcluidosListener(this);
*/
        SingletonGestorRestaurante.getInstance(getContext()).getPedidosAPI(getContext());


        return view;
    }



}