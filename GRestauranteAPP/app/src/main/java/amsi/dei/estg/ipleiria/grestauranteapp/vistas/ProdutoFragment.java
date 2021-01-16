package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.adaptadores.ListaProdutoAdaptador;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.ProdutosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Produto;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;

public class ProdutoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ProdutosListener {

    private static final int TODAS_CATEGORIAS =0 ;
    private static final int CATEG_ENTRADA =1 ;
    private static final int CATEG_SOPA =2 ;
    private static final int CATEG_CARNE=3 ;
    private static final int CATEG_PEIXE =4 ;
    private static final int CATEG_SOBREMESA =5 ;
    private static final int CATEG_BEBIDA =6 ;
    private static final int CATEG_OUTRAS =-1 ;
    private CardView btn_categoria;
    private FragmentTransaction transaction;
    private CardView card_entrada;
    private CardView card_sopa;
    private CardView card_carne;
    private CardView card_peixe;
    private CardView card_sobremesa;
    private CardView card_bebida;
    private ListView lvlProdutos;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String ip;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


       View view = inflater.inflate(R.layout.fragment_produtos, container, false);

       card_entrada=view.findViewById(R.id.cv_entrada);
       card_sopa=view.findViewById(R.id.cv_sopa);
       card_carne=view.findViewById(R.id.cv_carne);
       card_peixe=view.findViewById(R.id.cv_peixe);
       card_sobremesa=view.findViewById(R.id.cv_sobremesa);
       card_bebida=view.findViewById(R.id.cv_bebida);

       lvlProdutos=view.findViewById(R.id.lvl_ListaProdutos);

        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout_ListaProdutosFragmento);

        SharedPreferences sharedPrefInfoUser = getActivity().getSharedPreferences(MenuActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        ip= sharedPrefInfoUser.getString(MenuActivity.IP,null);

        swipeRefreshLayout.setOnRefreshListener(this);

        SingletonGestorRestaurante.getInstance(getContext()).setProdutosListener(this);
        SingletonGestorRestaurante.getInstance(getContext()).getProdutosCategoriaAPI(ip,TODAS_CATEGORIAS,getContext());

        card_entrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonGestorRestaurante.getInstance(getContext()).getProdutosCategoriaAPI(ip,CATEG_ENTRADA,getContext());

            }
        });

       card_sopa.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SingletonGestorRestaurante.getInstance(getContext()).getProdutosCategoriaAPI(ip,CATEG_SOPA,getContext());

           }
       });

        card_carne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonGestorRestaurante.getInstance(getContext()).getProdutosCategoriaAPI(ip,CATEG_CARNE,getContext());

            }
        });

        card_peixe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonGestorRestaurante.getInstance(getContext()).getProdutosCategoriaAPI(ip,CATEG_PEIXE,getContext());
            }
        });

        card_sobremesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonGestorRestaurante.getInstance(getContext()).getProdutosCategoriaAPI(ip,CATEG_SOBREMESA,getContext());

            }
        });

        card_bebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonGestorRestaurante.getInstance(getContext()).getProdutosCategoriaAPI(ip,CATEG_BEBIDA,getContext());

            }
        });
        return view;
    }

    @Override
    public void onRefreshListaPordutos(ArrayList<Produto> listaProdutos) {
        if(listaProdutos != null)
            lvlProdutos.setAdapter(new ListaProdutoAdaptador(getContext(),listaProdutos));

    }

    @Override
    public void onRefreshDetalhes() {
        //empty
    }

    @Override
    public void onRefresh() {
        SingletonGestorRestaurante.getInstance(getContext()).getProdutosCategoriaAPI(ip,TODAS_CATEGORIAS,getContext());
        swipeRefreshLayout.setRefreshing(false);
    }
}
