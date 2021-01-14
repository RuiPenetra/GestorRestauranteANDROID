package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.adaptadores.ListaPedidoAdaptador;
import amsi.dei.estg.ipleiria.grestauranteapp.adaptadores.PageAdapter;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PedidosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Pedido;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;

public class PedidoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, PedidosListener {

/*    private TabLayout tabPedidos;
    private ViewPager viewPager;
    private TabItem tabAtivos,tabConcluidos;
    private PageAdapter pagerAdapter;*/
    private static final int EDITAR = 1 ;
    private static final int CRIAR =2 ;
    private static final int APAGAR =3 ;
    private static final int ID_UTILIZADOR =1 ;
    private ListView lvlPedidos;
    private CardView cvNovoPedido;
    private SwipeRefreshLayout swipeRefreshLayout;


    public PedidoFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pedidos,container,false);

        lvlPedidos=view.findViewById(R.id.lvListaPedidos);
        cvNovoPedido=view.findViewById(R.id.cvNovoPedido);
        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayoutPedidos);
        swipeRefreshLayout.setOnRefreshListener(this);

        SingletonGestorRestaurante.getInstance(getContext()).setPedidosListener(this);
        SingletonGestorRestaurante.getInstance(getContext()).getPedidosAPI(getContext());

/*        tabPedidos=view.findViewById(R.id.tabPedidos);
        tabAtivos=view.findViewById(R.id.tab_item_ativo);
        tabConcluidos=view.findViewById(R.id.tab_item_concluido);
        viewPager=view.findViewById(R.id.viewpager);*/

        lvlPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),DetalhesPedidoActivity.class);
                intent.putExtra(DetalhesPedidoActivity.ID, (int) id);
                startActivityForResult(intent,EDITAR);
            }
        });

        cvNovoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),CriarPedidoActivity.class);
                intent.putExtra(CriarPedidoActivity.ID,ID_UTILIZADOR);
                startActivityForResult(intent,CRIAR);
            }
        });
/*        pagerAdapter= new PageAdapter(getChildFragmentManager(),tabPedidos.getTabCount());
        viewPager.setAdapter(pagerAdapter);*/

       /* tabPedidos.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0){
                    pagerAdapter.notifyDataSetChanged();
                }else{
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabPedidos));
        return view;
    }

    /**
     *
     * @param requestCode código enviado para a atividade
     * @param resultCode o código recebido pela atividade
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case CRIAR:
                    SingletonGestorRestaurante.getInstance(getContext()).getPedidosAPI(getContext());
                    Toast.makeText(getContext(),"Pedido criado com sucesso", Toast.LENGTH_LONG).show();
                    break;
                case EDITAR:
                    SingletonGestorRestaurante.getInstance(getContext()).getPedidosAPI(getContext());
                    Toast.makeText(getContext(),"Livro editado com sucesso",Toast.LENGTH_LONG).show();
                    break;
                case APAGAR:
                    SingletonGestorRestaurante.getInstance(getContext()).getPedidosAPI(getContext());
                    Toast.makeText(getContext(),"Livro apagado com sucesso",Toast.LENGTH_LONG).show();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRefreshListaPedidos(ArrayList<Pedido> listapedidos) {

        if(listapedidos != null)
            lvlPedidos.setAdapter(new ListaPedidoAdaptador(getContext(),listapedidos));
    }

    @Override
    public void onRefreshCriar() {
        //EMPTY
    }

    @Override
    public void onRefresh() {
        SingletonGestorRestaurante.getInstance(getContext()).getPedidosAPI(getContext());
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        SingletonGestorRestaurante.getInstance(getContext()).setPedidosListener(this);

    }
}
