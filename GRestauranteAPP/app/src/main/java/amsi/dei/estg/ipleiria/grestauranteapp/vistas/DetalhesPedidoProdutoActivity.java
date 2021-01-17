package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PedidosProdutoListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.PedidoProduto;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Produto;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;

public class DetalhesPedidoProdutoActivity extends AppCompatActivity implements PedidosProdutoListener {

    public static final String ID_PEDIDO_PRODUTO ="ID_PEDIDO_PRODUTO" ;
    private TextView tv_PedProd_Estado,tv_PedProd_Quantidade,tv_PedProd_Preco,tv_ProdNome,tv_ProdCategoria,tv_ProdIngredientes;
    private ImageView img_Categoria;
    private PedidoProduto pedidoProduto;
    private Produto produto;
    private CardView cvAtualizar,cvRemover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_pedido_produto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_PedProd_Estado=findViewById(R.id.tv_PedP_Estado);
        tv_PedProd_Quantidade=findViewById(R.id.tv_PedP_Quantidade);
        tv_PedProd_Preco=findViewById(R.id.tv_PedP_Preco);
        tv_ProdNome=findViewById(R.id.tv_PedP_NomeProduto);
        tv_ProdCategoria=findViewById(R.id.tv_PedP_ProdutoCategoria);
        tv_ProdIngredientes=findViewById(R.id.tv_PedP_ProdutoIngredientes);
        img_Categoria=findViewById(R.id.img_PedP_ProdutoCategoria);

        cvAtualizar=findViewById(R.id.cvAtualizarPedidoProduto);
        cvRemover=findViewById(R.id.cvRemoverPedidoProduto);


        int id_pedido= getIntent().getIntExtra(ID_PEDIDO_PRODUTO, -1);

        pedidoProduto= SingletonGestorRestaurante.getInstance(this).getPedidoProduto(id_pedido);
        produto= SingletonGestorRestaurante.getInstance(this).getProduto(pedidoProduto.getId_produto());


        setTitle("Pedido Produto: "+pedidoProduto.getId());

        carregarDadosPedidoProduto();
    }

    private void carregarDadosPedidoProduto() {

        switch (pedidoProduto.getEstado()){
            case 0:
                tv_PedProd_Estado.setText(" Em Processo ");
                tv_PedProd_Estado.setBackgroundResource(R.drawable.badge_processo);
                break;
            case 1:
                tv_PedProd_Estado.setText(" Em Progresso ");
                tv_PedProd_Estado.setBackgroundResource(R.drawable.badge_progresso);
                break;
            case 2:
                tv_PedProd_Estado.setText(" Concluido ");
                tv_PedProd_Estado.setBackgroundResource(R.drawable.badge_concluido);
                break;
        }

        tv_PedProd_Quantidade.setText(String.valueOf(pedidoProduto.getQuantidade()));
        tv_PedProd_Preco.setText(String.valueOf(pedidoProduto.getPreco()));

        Log.i("---->",""+produto.getNome());
        tv_ProdNome.setText(produto.getNome());
        tv_ProdIngredientes.setText(produto.getIngredientes());

        switch (produto.getCategoria()) {
            case 1:
                tv_ProdCategoria.setText("Entrada");
                img_Categoria.setImageResource(R.drawable.entradas);
                break;
            case 2:
                tv_ProdCategoria.setText("Sopa");
                img_Categoria.setImageResource(R.drawable.sopa);
                break;
            case 3:
                tv_ProdCategoria.setText("Carne");
                img_Categoria.setImageResource(R.drawable.bife);
                break;
            case 4:
                tv_ProdCategoria.setText("Peixe");
                img_Categoria.setImageResource(R.drawable.peixe);
                break;
            case 5:
                tv_ProdCategoria.setText("Sobremesa");
                img_Categoria.setImageResource(R.drawable.sobremesa);
                break;
            case 6:
                tv_ProdCategoria.setText("Bebida");
                img_Categoria.setImageResource(R.drawable.bebidas);
                break;
        }

    }

    @Override
    public void onRefreshListaPedidosProduto(ArrayList<PedidoProduto> pedidoProdutos) {
        //EMPTY
    }

    @Override
    public void onCriar() {
        //EMPTY
    }

    @Override
    public void onDetalhes() {

        setResult(RESULT_OK);
        finish();
    }
}