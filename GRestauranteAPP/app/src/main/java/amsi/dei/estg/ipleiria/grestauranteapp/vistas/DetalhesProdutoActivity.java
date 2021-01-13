package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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
    private CardView cvSomar,cvSubtrair,cvCriarPedidoProduto;
    private ImageView imgvCategoriaProduto;
    private int id_produto;
    private PedidoProduto pedidoProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Detalhes Produto");
        setContentView(R.layout.activity_detalhes_produto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id_produto = getIntent().getIntExtra(ID_PRODUTO, -1);

        produto = SingletonGestorRestaurante.getInstance(this).getProduto(id_produto);


        //TODO: Duvida se é preciso ou para que servira
        // SharedPreferences sharedPrefInfoUser = getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        // sharedPrefInfoUser.getString(MenuMainActivity.TOKEN,null);

        SingletonGestorRestaurante.getInstance(getApplicationContext()).setPedidoProdutosListener(this);

        tvCategoria=findViewById(R.id.tvCategoria);
        tvNome=findViewById(R.id.tvNomeProduto);
        tvIngredientes=findViewById(R.id.tvIngredientes);
        tvPreco=findViewById(R.id.tvPreco);
        tvQuantida=findViewById(R.id.tvQuantidadePedida);

        cvSomar=findViewById(R.id.cvSomar);
        cvSubtrair=findViewById(R.id.cvSubtrair);
        imgvCategoriaProduto=findViewById(R.id.imgvCategoriaProduto);

        cvCriarPedidoProduto=findViewById(R.id.cvCriarPedidoProduto);

        carregarDetalhesProduto();

        cvSomar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantidade=Integer.parseInt(tvQuantida.getText().toString());
                quantidade=quantidade+1;
                tvQuantida.setText(""+quantidade);

                float preco= Float.parseFloat(produto.getPreco());

                preco=preco*quantidade;

                tvPreco.setText(""+preco);
            }
        });

        cvSubtrair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantidade=Integer.parseInt(tvQuantida.getText().toString());
                if(quantidade>=1){
                    quantidade=quantidade-1;
                    if(quantidade!=0){
                        tvQuantida.setText(""+quantidade);
                    }else{
                        tvQuantida.setText("1");
                    }

                    float preco= Float.parseFloat(produto.getPreco());

                    preco=preco*quantidade;

                    tvPreco.setText(String.valueOf(preco));
                }else{
                    tvQuantida.setText("1");
                    tvPreco.setText(produto.getPreco());

                }
            }
        });

        cvCriarPedidoProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id_pedido=getIntent().getIntExtra(ID_PEDIDO, -1);
                int produto_id=produto.getId();
                int quantidade=Integer.parseInt(tvQuantida.getText().toString());
                int estado=0;
                float preco=Float.parseFloat(tvPreco.getText().toString());

                pedidoProduto = new PedidoProduto(0,produto_id,id_pedido,quantidade,preco,estado);

                SingletonGestorRestaurante.getInstance(getApplicationContext()).adicionarPedidoProdutoAPI(pedidoProduto, getApplicationContext());
            }
        });


    }

    private void carregarDetalhesProduto(){

        tvNome.setText(produto.getNome());
        tvPreco.setText(String.valueOf(produto.getPreco()));
        tvQuantida.setText(String.valueOf(1));

        if (produto.getIngredientes()!= null){

            tvIngredientes.setText(produto.getIngredientes());

        }else {
            tvIngredientes.setText("Não existe informações");

        }

        switch (produto.getCategoria()){
            case 1:
                tvCategoria.setText("Entrada");
                imgvCategoriaProduto.setImageResource(R.drawable.entradas);
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
    public void onRefreshCriar() {
        setResult(RESULT_OK);
        finish();
    }
}