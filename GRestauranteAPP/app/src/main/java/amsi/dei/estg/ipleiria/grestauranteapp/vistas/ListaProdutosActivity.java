package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.adaptadores.ListaProdutoAdaptador;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PedidosProdutoListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.ProdutosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.PedidoProduto;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Produto;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;

public class ListaProdutosActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ProdutosListener {

    public static final String ID_PEDIDO ="ID_PEDIDO" ;
    private static final int DETALHES = 1 ;
    private static final int ADICIONAR =2 ;
    private static final int APAGAR =3 ;
    private static final int TODAS_CATEGORIAS =0 ;
    private static final int CATEG_ENTRADA =1 ;
    private static final int CATEG_SOPA =2 ;
    private static final int CATEG_CARNE=3 ;
    private static final int CATEG_PEIXE =4 ;
    private static final int CATEG_SOBREMESA =5 ;
    private static final int CATEG_BEBIDA =6 ;
    private static final int CATEG_OUTRAS =-1 ;
    private CardView cvEntrada,cvSopa,cvCarne,cvPeixe,cvBedida,cvSobremesa,cvOutras;
    private ListView lvlProdutos;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int id_pedido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cvEntrada=findViewById(R.id.cvEntrada);
        cvSopa=findViewById(R.id.cvSopa);
        cvCarne=findViewById(R.id.cvCarne);
        cvPeixe=findViewById(R.id.cvPeixe);
        cvBedida=findViewById(R.id.cvBebida);
        cvSobremesa=findViewById(R.id.cvSobremesa);
        cvOutras=findViewById(R.id.cvOutro);

        lvlProdutos=findViewById(R.id.lvlListaProdutos);

        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout_ListaProdutos);
        swipeRefreshLayout.setOnRefreshListener(this);

        SingletonGestorRestaurante.getInstance(getApplicationContext()).setProdutosListener(this);
        SingletonGestorRestaurante.getInstance(getApplicationContext()).getProdutosCategoriaAPI(getApplicationContext(),TODAS_CATEGORIAS);

        id_pedido = getIntent().getIntExtra(ID_PEDIDO, -1);

        if(id_pedido>0){
            setTitle("Selecione o Produto");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        }else{
            setTitle("Lista Produtos");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        lvlProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),DetalhesProdutoActivity.class);
                intent.putExtra(DetalhesProdutoActivity.ID_PRODUTO, (int) id);
                intent.putExtra(DetalhesProdutoActivity.ID_PEDIDO, id_pedido);
                startActivityForResult(intent,ADICIONAR);
            }
        });

        cvEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonGestorRestaurante.getInstance(getApplicationContext()).getProdutosCategoriaAPI(getApplicationContext(),CATEG_ENTRADA);
            }
        });
        cvSopa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonGestorRestaurante.getInstance(getApplicationContext()).getProdutosCategoriaAPI(getApplicationContext(),CATEG_SOPA);
            }
        });

        cvCarne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonGestorRestaurante.getInstance(getApplicationContext()).getProdutosCategoriaAPI(getApplicationContext(),CATEG_CARNE);
            }
        });

        cvPeixe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonGestorRestaurante.getInstance(getApplicationContext()).getProdutosCategoriaAPI(getApplicationContext(),CATEG_PEIXE);
            }
        });

        cvSobremesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonGestorRestaurante.getInstance(getApplicationContext()).getProdutosCategoriaAPI(getApplicationContext(),CATEG_SOBREMESA);
            }
        });

        cvBedida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonGestorRestaurante.getInstance(getApplicationContext()).getProdutosCategoriaAPI(getApplicationContext(),CATEG_BEBIDA);

            }
        });

        cvOutras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingletonGestorRestaurante.getInstance(getApplicationContext()).getProdutosCategoriaAPI(getApplicationContext(),CATEG_OUTRAS);

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case ADICIONAR:
                    setResult(RESULT_OK);
                    finish();
                    break;
                case DETALHES:
                    SingletonGestorRestaurante.getInstance(getApplicationContext()).getPedidosAPI(getApplicationContext());
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(id_pedido>0){
            MenuInflater menuInflater=getMenuInflater();
            menuInflater.inflate(R.menu.menu_lista_produtos,menu);
            return super.onCreateOptionsMenu(menu);
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.itemFechar:
              finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefreshListaPordutos(ArrayList<Produto> listaProdutos) {
        if(listaProdutos != null)
            lvlProdutos.setAdapter(new ListaProdutoAdaptador(getApplicationContext(),listaProdutos));
    }

    @Override
    public void onRefreshDetalhes() {
        //empty
    }

    @Override
    public void onRefresh() {
        SingletonGestorRestaurante.getInstance(getApplicationContext()).getProdutosCategoriaAPI(getApplicationContext(),TODAS_CATEGORIAS);
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    protected void onPause() {
        int pedido_ID=id_pedido;
        Log.i("--->","Resume-->"+id_pedido);
        super.onPause();

    }

}