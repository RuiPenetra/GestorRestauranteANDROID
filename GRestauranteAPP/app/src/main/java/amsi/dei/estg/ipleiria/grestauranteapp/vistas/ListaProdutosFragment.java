package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.adaptadores.ListaProdutoAdaptador;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.ProdutosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Produto;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;


public class ListaProdutosFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ProdutosListener {

    private ListView lvListaProdutos;
    private int id_categoria;
    private ArrayList<Produto> listaProdutos;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ListaProdutosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_produtos, container, false);
        lvListaProdutos = view.findViewById(R.id.lvListaProdutos);
        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        Bundle bundle = this.getArguments();

        id_categoria=bundle.getInt("id_categoria");
        SingletonGestorRestaurante.getInstance(getContext()).setProdutosListener(this);
        SingletonGestorRestaurante.getInstance(getContext()).getProdutosCategoriaAPI(getContext(),id_categoria);


        lvListaProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),DetalhesProdutoActivity.class);
                intent.putExtra(DetalhesProdutoActivity.ID_PRODUTO, (int) id);
                startActivity(intent);
                //startActivityForResult(intent,EDITAR);
            }
        });


        return view;
    }



    @Override
    public void onRefresh() {
        SingletonGestorRestaurante.getInstance(getContext()).getProdutosCategoriaAPI(getContext(),id_categoria);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshListaPordutos(ArrayList<Produto> listaProdutos) {
        if(listaProdutos != null)
            lvListaProdutos.setAdapter(new ListaProdutoAdaptador(getContext(),listaProdutos));
    }

    @Override
    public void onRefreshDetalhes() {

    }
}