package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.adaptadores.ListaPedidoAdaptador;
import amsi.dei.estg.ipleiria.grestauranteapp.adaptadores.ListaPedidosProdutoAdaptador;
import amsi.dei.estg.ipleiria.grestauranteapp.adaptadores.ListaProdutoAdaptador;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PedidosProdutoListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.PedidoProduto;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Produto;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.PedidosProdutoJsonParser;

public class DetalhesPedidoActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, PedidosProdutoListener {

    public static final String ID ="ID" ;
    private static final int EDITAR = 1 ;
    private static final int ADICIONAR =2 ;
    private static final int APAGAR =3 ;
    private FloatingActionButton fab_criarPedidoProduto;
    private SwipeRefreshLayout swipeRefreshLayoutPedidosProduto;
    private ListView lvlPedidosProduto;
    private int id_pedido;
    private ArrayList<Produto> produtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_pedido);

        swipeRefreshLayoutPedidosProduto=findViewById(R.id.swipeRefreshLayoutPedidoProdutos);
        id_pedido = getIntent().getIntExtra(ID, -1);
        lvlPedidosProduto=findViewById(R.id.lvl_pedidoProdutos);

        swipeRefreshLayoutPedidosProduto.setOnRefreshListener(this);

        produtos=SingletonGestorRestaurante.getInstance(getApplicationContext()).getProdutosBD();

        SingletonGestorRestaurante.getInstance(getApplicationContext()).setPedidoProdutosListener(this);
        SingletonGestorRestaurante.getInstance(getApplicationContext()).getPedidosProdutoAPI(getApplicationContext(),id_pedido);

        FloatingActionButton fab_criarPedidoProduto = (FloatingActionButton) findViewById(R.id.fab_criarPedidoProduto);

        fab_criarPedidoProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Generic.isConnectionInternet(getApplicationContext())){
                    Intent intent=new Intent(getApplicationContext(), ListaProdutosActivity.class);
                    intent.putExtra(DetalhesProdutoActivity.ID_PEDIDO, id_pedido);
                    startActivityForResult(intent,ADICIONAR);
                }else{
                    Toast.makeText(getApplicationContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();

                }
            }
        });
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
                case ADICIONAR:
                    SingletonGestorRestaurante.getInstance(getApplicationContext()).getPedidosProdutoAPI(getApplicationContext(),id_pedido);
                    Toast.makeText(getApplicationContext(),"Produto adicionado com sucesso", Toast.LENGTH_LONG).show();
                    break;
                case EDITAR:
                    SingletonGestorRestaurante.getInstance(getApplicationContext()).getPedidosProdutoAPI(getApplicationContext(),id_pedido);
                    Toast.makeText(getApplicationContext(),"Livro editado com sucesso",Toast.LENGTH_LONG).show();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRefreshListaPedidosProduto(ArrayList<PedidoProduto> pedidoProdutos) {
        if(pedidoProdutos != null)
            lvlPedidosProduto.setAdapter(new ListaPedidosProdutoAdaptador(getApplicationContext(),pedidoProdutos,produtos));
    }

    @Override
    public void onRefreshCriar() {

    }

    @Override
    public void onRefresh() {
        SingletonGestorRestaurante.getInstance(getApplicationContext()).getPedidosProdutoAPI(getApplicationContext(),id_pedido);
        swipeRefreshLayoutPedidosProduto.setRefreshing(false);
    }
}