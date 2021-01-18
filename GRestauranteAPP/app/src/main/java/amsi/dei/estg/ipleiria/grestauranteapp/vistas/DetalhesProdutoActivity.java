package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PedidosProdutoListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.ProdutosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Pedido;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.PedidoProduto;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Produto;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;

public class DetalhesProdutoActivity extends AppCompatActivity implements PedidosProdutoListener {

    public static final String ID_PRODUTO = "ID_PRODUTO";
    public static final String ID_PEDIDO= "ID_PEDIDO";
    private Produto produto;
    private TextView tvCategoria,tvNome,tvIngredientes,tvPreco,tvQuantida;
    private CardView cvSomar,cvSubtrair;
    private Button btnCriarPedidoProduto;
    private ImageView imgvCategoriaProduto;
    private int id_produto;
    private int id_pedido;
    private int id;
    private String ip,token;
    private PedidoProduto pedidoProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Detalhes Produto");
        setContentView(R.layout.activity_detalhes_produto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id_produto = getIntent().getIntExtra(ID_PRODUTO, -1);
        id_pedido = getIntent().getIntExtra(ID_PEDIDO, -1);

        produto = SingletonGestorRestaurante.getInstance(this).getProduto(id_produto);

        SingletonGestorRestaurante.getInstance(getApplicationContext()).setPedidoProdutosListener(this);

        tvCategoria=findViewById(R.id.tvCategoria);
        tvNome=findViewById(R.id.tvNomeProduto);
        tvIngredientes=findViewById(R.id.tvIngredientes);
        tvPreco=findViewById(R.id.tvPreco);
        tvQuantida=findViewById(R.id.tvQuantidadePedida);

        cvSomar=findViewById(R.id.cvSomar);
        cvSubtrair=findViewById(R.id.cvSubtrair);
        imgvCategoriaProduto=findViewById(R.id.imgvCategoriaProduto);

        btnCriarPedidoProduto=findViewById(R.id.btnCriarPedidoProduto);

        SharedPreferences sharedPrefInfoUser = getSharedPreferences(MenuActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        ip= sharedPrefInfoUser.getString(MenuActivity.IP,null);
        token= sharedPrefInfoUser.getString(MenuActivity.TOKEN,null);

        carregarDetalhesProduto();

        if(id_pedido<=0){
            btnCriarPedidoProduto.setVisibility(View.INVISIBLE);
        }
        cvSomar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantidade=Integer.parseInt(tvQuantida.getText().toString());
                quantidade=quantidade+1;
                tvQuantida.setText(""+quantidade);

                float preco= Float.parseFloat(produto.getPreco());

                preco=preco*quantidade;

                tvPreco.setText(String.format("%.2f", preco).replace(',', '.'));
            }
        });

        cvSubtrair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantidade=Integer.parseInt(tvQuantida.getText().toString());
                float preco= Float.parseFloat(produto.getPreco());

                if(quantidade>1){
                    quantidade=quantidade-1;
                    preco= preco*quantidade;

                    if(quantidade>0){
                        tvQuantida.setText(""+quantidade);
                        tvPreco.setText(String.format("%.2f", preco).replace(',', '.'));

                    }else{
                        tvQuantida.setText("1");
                        tvPreco.setText(String.format("%.2f", produto.getPreco()).replace(',', '.'));
                    }

                }else{
                    tvQuantida.setText("1");
                    tvPreco.setText(produto.getPreco());

                }
            }
        });

        btnCriarPedidoProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int produto_id=produto.getId();
                int quantidade=Integer.parseInt(tvQuantida.getText().toString());
                int estado=0;
                float preco=Float.parseFloat(tvPreco.getText().toString().replace(',', '.'));

                pedidoProduto = new PedidoProduto(0,produto_id,id_pedido,quantidade,preco,estado);

                SingletonGestorRestaurante.getInstance(getApplicationContext()).adicionarPedidoProdutoAPI(ip,token,pedidoProduto, getApplicationContext());
            }
        });


    }
    //CARERGA INFORMAÇÕES DE 1 PRODUTO
    private void carregarDetalhesProduto(){

        tvNome.setText(produto.getNome());
        tvPreco.setText(produto.getPreco());
        tvQuantida.setText(String.valueOf(1));

        if (produto.getIngredientes()!= null){

            tvIngredientes.setText(produto.getIngredientes());

        }else {
            tvIngredientes.setText("Não existe informações");

        }

        switch (produto.getCategoria()){
            case 1:
                tvCategoria.setText("Entrada");
                imgvCategoriaProduto.setImageResource(R.drawable.aperitivo);
                break;
            case 2:
                tvCategoria.setText("Sopa");
                imgvCategoriaProduto.setImageResource(R.drawable.sopa);
                break;
            case 3:
                tvCategoria.setText("Carne");
                imgvCategoriaProduto.setImageResource(R.drawable.bife);
                break;
            case 4:
                tvCategoria.setText("Peixe");
                imgvCategoriaProduto.setImageResource(R.drawable.peixe);
                break;
            case 5:
                tvCategoria.setText("Sobremesa");
                imgvCategoriaProduto.setImageResource(R.drawable.sobremesa);
                break;
            case 6:
                tvCategoria.setText("Bebida");
                imgvCategoriaProduto.setImageResource(R.drawable.bebidas);
                break;

            default:
                tvCategoria.setText("Outros");
                imgvCategoriaProduto.setImageResource(R.drawable.food);
                break;
        }
    }

    @Override
    public void onRefreshListaPedidosProduto(ArrayList<PedidoProduto> pedidoProdutos) {
        //empty
    }

    @Override
    public void onCriar() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onDetalhes() {
        //empty
    }

}