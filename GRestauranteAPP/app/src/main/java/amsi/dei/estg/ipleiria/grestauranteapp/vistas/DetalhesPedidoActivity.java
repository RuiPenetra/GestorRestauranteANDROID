package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.adaptadores.ListaPedidoAdaptador;
import amsi.dei.estg.ipleiria.grestauranteapp.adaptadores.ListaPedidosProdutoAdaptador;
import amsi.dei.estg.ipleiria.grestauranteapp.adaptadores.ListaProdutoAdaptador;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PedidosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PedidosProdutoListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Pedido;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.PedidoProduto;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Produto;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.PedidosProdutoJsonParser;

public class DetalhesPedidoActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, PedidosProdutoListener{

    public static final String ID ="ID" ;
    private static final int EDITAR_PEDIDOPRODUTO= 3 ;
    private static final int ADICIONAR_PEDIDOPRODUTO =4 ;
    private FloatingActionButton fab_criarPedidoProduto;
    private SwipeRefreshLayout swipeRefreshLayoutPedidosProduto;
    private ListView lvlPedidosProduto;
    private TextView tvNPedido,tvDataHora,tvTipo,tvEstado;
    private ImageView imgvTipo;
    private int id_pedido;
    private ArrayList<Produto> produtos;
    private Pedido pedido;
    private String ip,token,cargo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_pedido);

        tvTipo=findViewById(R.id.tv_tipo);
        tvDataHora=findViewById(R.id.tv_DataHora);
        tvEstado=findViewById(R.id.tv_estado);
        tvNPedido=findViewById(R.id.tv_NPedido);
        imgvTipo=findViewById(R.id.imgVtipo);
        swipeRefreshLayoutPedidosProduto=findViewById(R.id.swipeRefreshLayoutPedidoProdutos);
        lvlPedidosProduto=findViewById(R.id.lvl_pedidoProdutos);


        id_pedido = getIntent().getIntExtra(ID, -1);

        setTitle("Detalhes Pedido: "+id_pedido);

        SharedPreferences sharedPrefInfoUser = getSharedPreferences(MenuActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        ip= sharedPrefInfoUser.getString(MenuActivity.IP,null);
        token= sharedPrefInfoUser.getString(MenuActivity.TOKEN,null);
        cargo= sharedPrefInfoUser.getString(MenuActivity.CARGO,null);


        swipeRefreshLayoutPedidosProduto.setOnRefreshListener(this);
        SingletonGestorRestaurante.getInstance(getApplicationContext()).setPedidoProdutosListener(this);

        produtos=SingletonGestorRestaurante.getInstance(getApplicationContext()).getProdutosBD();
        pedido=SingletonGestorRestaurante.getInstance(getApplicationContext()).getPedido(id_pedido);

        SingletonGestorRestaurante.getInstance(getApplicationContext()).getPedidosProdutoAPI(ip,token,getApplicationContext(),id_pedido);

        FloatingActionButton fab_criarPedidoProduto = findViewById(R.id.fab_criarPedidoProduto);

        fab_criarPedidoProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Generic.isConnectionInternet(getApplicationContext())){
                    Intent intent=new Intent(getApplicationContext(), ListaProdutosActivity.class);
                    intent.putExtra(DetalhesProdutoActivity.ID_PEDIDO, id_pedido);
                    startActivityForResult(intent,ADICIONAR_PEDIDOPRODUTO);
                }else{
                    Toast.makeText(getApplicationContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();

                }
            }
        });

        lvlPedidosProduto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),DetalhesPedidoProdutoActivity.class);
                intent.putExtra(DetalhesPedidoProdutoActivity.ID_PEDIDO_PRODUTO, (int) id);
                startActivityForResult(intent,EDITAR_PEDIDOPRODUTO);
            }
        });


        carregarDadosPedido(pedido);
    }

    private void carregarDadosPedido(Pedido pedido) {

        tvNPedido.setText(String.valueOf(pedido.getId()));
        tvTipo.setText(String.valueOf(pedido.getId_mesa()));
        tvDataHora.setText(pedido.getData());

        if(!cargo.equals("cliente")){
            tvTipo.setText(String.valueOf(pedido.getId_mesa()));
            imgvTipo.setImageResource(R.drawable.mesa);
        }else{
            tvTipo.setText(String.valueOf(pedido.getNome_pedido()));
            imgvTipo.setImageResource(R.drawable.ic_perfil);
        }

        switch (pedido.getEstado()){
            case 0:
                tvEstado.setText(" Em Processo ");
                tvEstado.setBackgroundResource(R.drawable.badge_processo);
                break;
            case 1:
                tvEstado.setText(" Em Progresso ");
                tvEstado.setBackgroundResource(R.drawable.badge_progresso);
                break;
            case 2:
                tvEstado.setText(" Concluido ");
                tvEstado.setBackgroundResource(R.drawable.badge_concluido);
                break;
        }

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
                case ADICIONAR_PEDIDOPRODUTO:
                    SingletonGestorRestaurante.getInstance(getApplicationContext()).getPedidosProdutoAPI(ip,token,getApplicationContext(),id_pedido);
                    Toast.makeText(getApplicationContext(),"Produto adicionado com sucesso", Toast.LENGTH_LONG).show();
                    break;
                case EDITAR_PEDIDOPRODUTO:
                    SingletonGestorRestaurante.getInstance(getApplicationContext()).getPedidosProdutoAPI(ip,token,getApplicationContext(),id_pedido);
                    Toast.makeText(getApplicationContext(),"Pedido Produto editado/removido com sucesso",Toast.LENGTH_LONG).show();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(pedido!=null){
            MenuInflater menuInflater=getMenuInflater();
            menuInflater.inflate(R.menu.menu_detalhes_pedido,menu);
            return super.onCreateOptionsMenu(menu);
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.itemRemover:
                if(Generic.isConnectionInternet(getApplicationContext())){
                    dialogRemover();
                }else{
                    Toast.makeText(this, R.string.noInternet, Toast.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialogRemover() {
        AlertDialog.Builder builder;
        builder= new AlertDialog.Builder(this);
        builder.setTitle("Apagar Pedido")
                .setMessage("Pretende mesmo apagar o pedido?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SingletonGestorRestaurante.getInstance(getApplicationContext()).removerPedidoAPI(ip,token,pedido,getApplicationContext());
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_delete)
                .show();
    }

    @Override
    public void onRefreshListaPedidosProduto(ArrayList<PedidoProduto> pedidoProdutos) {
        if(pedidoProdutos != null)
            lvlPedidosProduto.setAdapter(new ListaPedidosProdutoAdaptador(getApplicationContext(),pedidoProdutos,produtos));
    }

    @Override
    public void onCriar() {
        //EMPTY
    }

    @Override
    public void onRefresh() {
        SingletonGestorRestaurante.getInstance(getApplicationContext()).getPedidosProdutoAPI(ip,token,getApplicationContext(),id_pedido);
        swipeRefreshLayoutPedidosProduto.setRefreshing(false);
    }

    @Override
    public void onDetalhes() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onResume() {
        SingletonGestorRestaurante.getInstance(getApplicationContext()).setPedidoProdutosListener(this);

        super.onResume();
    }
}