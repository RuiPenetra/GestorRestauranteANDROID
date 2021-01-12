package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.ProdutosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Produto;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;

public class DetalhesProdutoActivity extends AppCompatActivity implements ProdutosListener {

    public static final String ID = "ID";
    private Produto produto;
    private TextView tvCategoria,tvNome,tvIngredientes,tvPreco,tvQuantida;
    private CardView cvSomar,cvSubtrair;
    private ImageView imgvCategoriaProduto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Detalhes Produto");
        setContentView(R.layout.activity_detalhes_produto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        int id = getIntent().getIntExtra(ID, -1);
        produto = SingletonGestorRestaurante.getInstance(this).getProduto(id);


        //TODO: Duvida se é preciso ou para que servira
        // SharedPreferences sharedPrefInfoUser = getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        // sharedPrefInfoUser.getString(MenuMainActivity.TOKEN,null);

        SingletonGestorRestaurante.getInstance(getApplicationContext()).setProdutosListener(this);

        tvCategoria=findViewById(R.id.tvCategoria);
        tvNome=findViewById(R.id.tvNomeProduto);
        tvIngredientes=findViewById(R.id.tvIngredientes);
        tvPreco=findViewById(R.id.tvPreco);
        tvQuantida=findViewById(R.id.tvQuantidade);

        cvSomar=findViewById(R.id.cvSomar);
        cvSubtrair=findViewById(R.id.cvSubtrair);

        imgvCategoriaProduto=findViewById(R.id.imgvCategoriaProduto);

        carregarDetalhesProduto();


    }

    private void carregarDetalhesProduto(){

        tvNome.setText(produto.getNome());
        tvPreco.setText(String.valueOf(produto.getPreco()));

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
    public void onRefreshListaPordutos(ArrayList<Produto> listaProdutos) {

    }

    @Override
    public void onRefreshDetalhes() {
        setResult(RESULT_OK);
        finish();
    }
}