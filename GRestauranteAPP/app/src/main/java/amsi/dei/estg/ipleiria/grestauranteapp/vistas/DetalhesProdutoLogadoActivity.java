package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.CarrinhoListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Carrinho;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.PedidoProduto;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Produto;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;

public class DetalhesProdutoLogadoActivity extends AppCompatActivity implements CarrinhoListener {

    public static final String ID_PRODUTO = "ID_PRODUTO";
    private TextView tvCategoria,tvNome,tvIngredientes,tvPreco,tvQuantida;
    private CardView cvSomar,cvSubtrair;
    private ImageView imgvCategoriaProduto;
    private int id_produto,id_user;
    private Produto produto;
    private Button btn_adicionar_itemCar;
    private Carrinho itemCarrinho;
    private String cargo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_produto_logado);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        id_produto = getIntent().getIntExtra(ID_PRODUTO, -1);

        produto = SingletonGestorRestaurante.getInstance(this).getProduto(id_produto);

        setTitle(produto.getNome());


        tvCategoria=findViewById(R.id.tvDetCategoria);
        tvNome=findViewById(R.id.tvDetNomeProduto);
        tvIngredientes=findViewById(R.id.tvDelIngredientes);
        tvPreco=findViewById(R.id.tvDetPreco);
        tvQuantida=findViewById(R.id.tvDetQuantidadePedida);

        cvSomar=findViewById(R.id.cvDetSomar);
        cvSubtrair=findViewById(R.id.cvDetSubtrair);
        imgvCategoriaProduto=findViewById(R.id.imgvDetCategoriaProduto);
        btn_adicionar_itemCar=findViewById(R.id.btn_adicionar_itemCar);

        SingletonGestorRestaurante.getInstance(getApplicationContext()).setCarrinhoListener(this);

        SharedPreferences sharedPrefInfoUser = getSharedPreferences(MenuActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        cargo= sharedPrefInfoUser.getString(MenuClienteActivity.CARGO,null);
        id_user= sharedPrefInfoUser.getInt(MenuClienteActivity.ID,-1);
        Log.i("##>",""+id_user);

        if(!cargo.equals("cliente")){
            btn_adicionar_itemCar.setVisibility(View.GONE);
        }

        carregarDetalhesProduto();


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

        btn_adicionar_itemCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCarrinho = new Carrinho(0,id_user, produto.getId(),produto.getCategoria(),produto.getNome(),Integer.parseInt(tvQuantida.getText()+""),tvPreco.getText()+"");
                SingletonGestorRestaurante.getInstance(getApplicationContext()).adicionarItemCarrinhoBD(itemCarrinho);

            }
        });
    }

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
    public void onRefreshListaItemsCarrinho(ArrayList<Carrinho> itemsCarrinho) {
        //EMPTY
    }

    @Override
    public void onAddItems() {
        //EMPTY
    }

    @Override
    public void onDetalhes(boolean estado) {
        setResult(RESULT_OK);
        finish();

    }

}