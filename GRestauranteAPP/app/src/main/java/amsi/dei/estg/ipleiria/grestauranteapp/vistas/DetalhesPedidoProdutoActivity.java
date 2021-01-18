package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
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
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PedidosProdutoListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.PedidoProduto;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Produto;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;

public class DetalhesPedidoProdutoActivity extends AppCompatActivity implements PedidosProdutoListener {

    public static final String ID_PEDIDO_PRODUTO ="ID_PEDIDO_PRODUTO" ;
    private TextView tv_PedProd_Estado,tv_PedProd_Quantidade,tv_PedProd_Preco,tv_ProdNome,tv_ProdCategoria,tv_ProdIngredientes;
    private ImageView img_Categoria;
    private CardView cvSomar,cvSubtrair;
    private PedidoProduto pedidoProduto;
    private Produto produto;
    private Button btnAtualizar,btnRemover;
    private String ip,token;

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
        cvSomar=findViewById(R.id.cvQuantSomar);
        cvSubtrair=findViewById(R.id.cvQuantSubtrair);
        btnAtualizar=findViewById(R.id.btnAtualizarPedidoProduto);
        btnRemover=findViewById(R.id.btnRemoverPedidoProduto);

        SharedPreferences sharedPrefInfoUser = getSharedPreferences(MenuActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        ip= sharedPrefInfoUser.getString(MenuActivity.IP,null);
        token= sharedPrefInfoUser.getString(MenuActivity.TOKEN,null);

        int id_pedido= getIntent().getIntExtra(ID_PEDIDO_PRODUTO, -1);
        SingletonGestorRestaurante.getInstance(getApplicationContext()).setPedidoProdutosListener(this);
        pedidoProduto= SingletonGestorRestaurante.getInstance(this).getPedidoProduto(id_pedido);
        produto= SingletonGestorRestaurante.getInstance(this).getProduto(pedidoProduto.getId_produto());


        setTitle("Pedido Produto: "+pedidoProduto.getId());

        carregarDadosPedidoProduto();

        cvSomar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantidade=Integer.parseInt(tv_PedProd_Quantidade.getText().toString());
                quantidade=quantidade+1;
                tv_PedProd_Quantidade.setText(""+quantidade);

                float preco= Float.parseFloat(produto.getPreco());

                preco=preco*quantidade;

                tv_PedProd_Preco.setText(String.format("%.2f", preco).replace(',', '.'));
            }
        });

        cvSubtrair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantidade=Integer.parseInt(tv_PedProd_Quantidade.getText().toString());
                float preco= Float.parseFloat(produto.getPreco());

                if(quantidade>1){
                    quantidade=quantidade-1;
                    preco= preco*quantidade;

                    if(quantidade>0){
                        tv_PedProd_Quantidade.setText(""+quantidade);
                        tv_PedProd_Preco.setText(String.format("%.2f", preco).replace(',', '.'));

                    }else{
                        tv_PedProd_Quantidade.setText("1");
                        tv_PedProd_Preco.setText(String.format("%.2f", produto.getPreco()).replace(',', '.'));
                    }

                }else{
                    tv_PedProd_Quantidade.setText("1");
                    tv_PedProd_Preco.setText(produto.getPreco());

                }
            }
        });

        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Generic.isConnectionInternet(getApplicationContext())) {

                    pedidoProduto.setQuantidade(Integer.parseInt(tv_PedProd_Quantidade.getText().toString()));
                    pedidoProduto.setPreco(Float.parseFloat(tv_PedProd_Preco.getText().toString().replace(',', '.')));

                    SingletonGestorRestaurante.getInstance(getApplicationContext()).updatePedidoProdutoAPI(ip,token,pedidoProduto,getApplicationContext());
                }else{
                    Toast.makeText(getApplicationContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              dialogRemover();
            }
        });
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

    private void dialogRemover() {
        AlertDialog.Builder builder;
        builder= new AlertDialog.Builder(this);
        builder.setTitle("Apagar Produto Pedido")
                .setMessage("Pretende mesmo apagar o produto pedido?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Generic.isConnectionInternet(getApplicationContext())) {

                            SingletonGestorRestaurante.getInstance(getApplicationContext()).removerPedidoProdutoAPI(ip, token, pedidoProduto, getApplicationContext());
                        }else{
                            Toast.makeText(getApplicationContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();

                        }
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
        //EMPTY
    }

    @Override
    public void onCriar() {
        //Empty
    }

    @Override
    public void onDetalhes() {

        setResult(RESULT_OK);
        finish();
    }


}