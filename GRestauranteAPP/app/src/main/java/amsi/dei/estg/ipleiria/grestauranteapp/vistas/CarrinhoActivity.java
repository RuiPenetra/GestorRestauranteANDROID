package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.adaptadores.ListaItemsCarrinhoAdaptador;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.CarrinhoListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Carrinho;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;

public class CarrinhoActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, CarrinhoListener {

    private static final int CRIAR = 1;
    private static final int EDITAR = 2;
    public static final int REMOVER = 3;
    private ListView lvlItemsCarrinho;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button btnConfPedido;
    private  ArrayList<Carrinho> itemsCarrinho;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        setTitle("Carrinho");

        btnConfPedido=findViewById(R.id.btnConfirmPedido);
        lvlItemsCarrinho=findViewById(R.id.lvListaItemsCarrinho);
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayoutCarrinho);
        swipeRefreshLayout.setOnRefreshListener(this);

        SingletonGestorRestaurante.getInstance(getApplicationContext()).setCarrinhoListener(this);

        SharedPreferences sharedPrefInfoUser = getSharedPreferences(MenuClienteActivity.PREF_INFO_USER, Context.MODE_PRIVATE);

        id= sharedPrefInfoUser.getInt(MenuClienteActivity.ID,-1);

        Log.i("##>",""+id);

        SingletonGestorRestaurante.getInstance(getApplicationContext()).getItemsCarrinhoBD(id);

        lvlItemsCarrinho.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.i("##>",""+id);
                Intent intent = new Intent(getApplicationContext(),DetalhesItemCarrinhoActivity.class);
                intent.putExtra(DetalhesItemCarrinhoActivity.ID_ITEM, (int) id);
                Toast.makeText(CarrinhoActivity.this, ""+(int) id, Toast.LENGTH_SHORT).show();
                startActivityForResult(intent,EDITAR);
            }
        });


        btnConfPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemsCarrinho=SingletonGestorRestaurante.getInstance(getApplicationContext()).getItemsCarrinho();
                Log.i("##>",""+itemsCarrinho);

                if(itemsCarrinho.size()!=0){

                    Log.i("##>",""+id);
                    Intent intent = new Intent(getApplicationContext(),CriarPedidoActivity.class);
                    //TODO: Configurar o id_utilizador guardado na shared
                    intent.putExtra(CriarPedidoActivity.ID,id);
                    startActivityForResult(intent,CRIAR);

                }else{
                    Toast.makeText(CarrinhoActivity.this, "Carrinho vazio", Toast.LENGTH_SHORT).show();
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
                case CRIAR:
                    Toast.makeText(this,"Pedido criado com sucesso", Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case EDITAR:
                    //TODO:ID UTILIZADOR GUARDADO NA SHARED
                    SingletonGestorRestaurante.getInstance(getApplicationContext()).getItemsCarrinhoBD(id);
                    Toast.makeText(this,"Pedido Produto Atualizado com sucesso",Toast.LENGTH_LONG).show();
                    break;
            }
        }else if(resultCode == Activity.RESULT_CANCELED){
            SingletonGestorRestaurante.getInstance(getApplicationContext()).getItemsCarrinhoBD(id);
            Toast.makeText(this,"Produto removido com sucesso",Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRefreshListaItemsCarrinho(ArrayList<Carrinho> itemsCarrinho) {

        if(itemsCarrinho != null)
            lvlItemsCarrinho.setAdapter(new ListaItemsCarrinhoAdaptador(getApplicationContext(),itemsCarrinho));
    }

    @Override
    public void onAddItems() {

    }

    @Override
    public void onDetalhes(boolean estado) {
        //Empty
    }


    @Override
    public void onRefresh() {
        //TODO:ID UTILIZADOR GUARDADO NA SHARED

        SingletonGestorRestaurante.getInstance(getApplicationContext()).getItemsCarrinhoBD(id);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        SingletonGestorRestaurante.getInstance(getApplicationContext()).setCarrinhoListener(this);

        //TODO:ID UTILIZADOR GUARDADO NA SHARED
        SingletonGestorRestaurante.getInstance(getApplicationContext()).getItemsCarrinhoBD(id);

        super.onResume();

    }
}