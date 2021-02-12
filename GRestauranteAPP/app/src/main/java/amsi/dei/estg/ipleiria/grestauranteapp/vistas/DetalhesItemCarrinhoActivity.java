package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;

public class DetalhesItemCarrinhoActivity extends AppCompatActivity implements CarrinhoListener {

    public static final String ID_ITEM ="ID_ITEM" ;
    private TextView tv_itemCar_Quantidade,tv_itemCar_Preco,tv_itemCar_ProdNome,tv_itemCar_Categoria,tv_itemCar_Ingredientes;
    private ImageView img_itemCar_Categoria;
    private CardView cv_itemCar_Somar,cv_itemCar_Subtrair;
    private Carrinho itemCarrinho;
    private Produto produto;
    private Button btn_itemCar_Atualizar,btn_itemCar_Remover;
    private String ip,token;
    private int id_itemCarrinho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_item_carrinho);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_itemCar_Quantidade=findViewById(R.id.tv_itemCar_QuantidadePedida);
        tv_itemCar_Preco=findViewById(R.id.tv_itemCar_Preco);
        tv_itemCar_ProdNome=findViewById(R.id.tv_itemCar_nomeProduto);
        tv_itemCar_Categoria=findViewById(R.id.tv_itemCar_categoria);
        tv_itemCar_Ingredientes=findViewById(R.id.tv_itemCar_ingredientes);
        img_itemCar_Categoria=findViewById(R.id.img_itemCar_ProdutoCategoria);
        cv_itemCar_Somar=findViewById(R.id.cv_itemCar_Somar);
        cv_itemCar_Subtrair=findViewById(R.id.cv_itemCar_Subtrair);
        btn_itemCar_Atualizar=findViewById(R.id.btn_itemCar_atualizar);
        btn_itemCar_Remover=findViewById(R.id.btn_itemCar_remover);

        SharedPreferences sharedPrefInfoUser = getSharedPreferences(MenuActivity.PREF_INFO_USER, Context.MODE_PRIVATE);

        id_itemCarrinho= getIntent().getIntExtra(ID_ITEM, -1);

        SingletonGestorRestaurante.getInstance(getApplicationContext()).setCarrinhoListener(this);
        itemCarrinho= SingletonGestorRestaurante.getInstance(this).getItemCarrinho(id_itemCarrinho);
        produto= SingletonGestorRestaurante.getInstance(this).getProduto(itemCarrinho.getId_produto());

        setTitle(produto.getNome());

        carregarDadosItemCarrinho();

        cv_itemCar_Somar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantidade=Integer.parseInt(tv_itemCar_Quantidade.getText().toString());
                quantidade=quantidade+1;
                tv_itemCar_Quantidade.setText(""+quantidade);

                float preco= Float.parseFloat(produto.getPreco());

                preco=preco*quantidade;

                tv_itemCar_Preco.setText(String.format("%.2f", preco).replace(',', '.'));
            }
        });

        cv_itemCar_Subtrair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantidade=Integer.parseInt(tv_itemCar_Quantidade.getText().toString());
                float preco= Float.parseFloat(produto.getPreco());

                if(quantidade>1){
                    quantidade=quantidade-1;
                    preco= preco*quantidade;

                    if(quantidade>0){
                        tv_itemCar_Quantidade.setText(""+quantidade);
                        tv_itemCar_Preco.setText(String.format("%.2f", preco).replace(',', '.'));

                    }else{
                        tv_itemCar_Quantidade.setText("1");
                        tv_itemCar_Preco.setText(String.format("%.2f", produto.getPreco()).replace(',', '.'));
                    }

                }else{
                    tv_itemCar_Quantidade.setText("1");
                    tv_itemCar_Preco.setText(produto.getPreco());

                }
            }
        });

        btn_itemCar_Atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    itemCarrinho.setQuantidade(Integer.parseInt(tv_itemCar_Quantidade.getText().toString()));
                    itemCarrinho.setPreco(tv_itemCar_Preco.getText().toString().replace(',', '.'));

                    SingletonGestorRestaurante.getInstance(getApplicationContext()).editarItemCarrinhoBD(itemCarrinho);
            }
        });

        btn_itemCar_Remover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogRemover();
            }
        });
    }

    //CARREGA OS DADOS DE CADA PRODUTO DENTRO DO PEDIDO
    private void carregarDadosItemCarrinho() {

        tv_itemCar_Quantidade.setText(String.valueOf(itemCarrinho.getQuantidade()));
        tv_itemCar_Preco.setText(String.valueOf(itemCarrinho.getPreco()));

        tv_itemCar_ProdNome.setText(produto.getNome());
        tv_itemCar_Ingredientes.setText(produto.getIngredientes());

        switch (produto.getCategoria()) {
            case 1:
                tv_itemCar_Categoria.setText("Entrada");
                img_itemCar_Categoria.setImageResource(R.drawable.aperitivo);
                break;
            case 2:
                tv_itemCar_Categoria.setText("Sopa");
                img_itemCar_Categoria.setImageResource(R.drawable.sopa);
                break;
            case 3:
                tv_itemCar_Categoria.setText("Carne");
                img_itemCar_Categoria.setImageResource(R.drawable.bife);
                break;
            case 4:
                tv_itemCar_Categoria.setText("Peixe");
                img_itemCar_Categoria.setImageResource(R.drawable.peixe);
                break;
            case 5:
                tv_itemCar_Categoria.setText("Sobremesa");
                img_itemCar_Categoria.setImageResource(R.drawable.sobremesa);
                break;
            case 6:
                tv_itemCar_Categoria.setText("Bebida");
                img_itemCar_Categoria.setImageResource(R.drawable.bebidas);
                break;
        }

    }
    //CONFIRMA SE QUER REMOVER O PRODUTO DENTRO DO PEDIDO
    private void dialogRemover() {
        AlertDialog.Builder builder;
        builder= new AlertDialog.Builder(this);
        builder.setTitle("Remover do carrinho")
                .setMessage("Pretende mesmo remover o produto do carrinho?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SingletonGestorRestaurante.getInstance(getApplicationContext()).removerItemCarrinhoBD(itemCarrinho.getId());
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
    public void onRefreshListaItemsCarrinho(ArrayList<Carrinho> itemsCarrinho) {

    }

    @Override
    public void onAddItems() {

    }

    @Override
    public void onDetalhes(boolean estado) {
        if(!estado){
            //Item removido
            setResult(RESULT_CANCELED);
            finish();
        }else{
            //Item Atualizado
            setResult(RESULT_OK);
            finish();
        }
    }

}