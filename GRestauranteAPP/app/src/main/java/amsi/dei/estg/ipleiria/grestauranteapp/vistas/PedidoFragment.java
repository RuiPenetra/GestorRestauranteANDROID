package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.adaptadores.ListaPedidoAdaptador;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PedidosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Pedido;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;

public class PedidoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, PedidosListener {

    private static final int CRIAR = 1 ;
    private static final int EDITAR = 2 ;
    private static final int ID_UTILIZADOR =1 ;
    private ListView lvlPedidos;
    private CardView cvNovoPedido;
    private ImageView imgvTipo;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String ip,token,cargo;


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
        imgvTipo=view.findViewById(R.id.imgv_Tipo);
        swipeRefreshLayout.setOnRefreshListener(this);

        SharedPreferences sharedPrefInfoUser = getActivity().getSharedPreferences(MenuActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        ip= sharedPrefInfoUser.getString(MenuActivity.IP,null);
        token= sharedPrefInfoUser.getString(MenuActivity.TOKEN,null);
        cargo= sharedPrefInfoUser.getString(MenuActivity.CARGO,null);

        if(!cargo.equals("cliente")){
            imgvTipo.setImageResource(R.drawable.restaurante);
        }else{
            imgvTipo.setImageResource(R.drawable.takeaway);
        }

        SingletonGestorRestaurante.getInstance(getContext()).setPedidosListener(this);
        SingletonGestorRestaurante.getInstance(getContext()).getPedidosAPI(ip,token,getContext());

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
                    SingletonGestorRestaurante.getInstance(getContext()).getPedidosAPI(ip,token,getContext());
                    Toast.makeText(getContext(),"Produto adicionado com sucesso", Toast.LENGTH_LONG).show();
                    break;
                case EDITAR:
                    SingletonGestorRestaurante.getInstance(getContext()).getPedidosAPI(ip,token,getContext());
                    Toast.makeText(getContext(),"Pedido Produto editado/removido com sucesso",Toast.LENGTH_LONG).show();
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
    public void onCreatePedido() {
        //EMPTY
    }

    @Override
    public void onRefresh() {
        SingletonGestorRestaurante.getInstance(getContext()).getPedidosAPI(ip,token,getContext());
        swipeRefreshLayout.setRefreshing(false);


    }

    @Override
    public void onResume() {
        super.onResume();
        SingletonGestorRestaurante.getInstance(getContext()).setPedidosListener(this);

    }
}
