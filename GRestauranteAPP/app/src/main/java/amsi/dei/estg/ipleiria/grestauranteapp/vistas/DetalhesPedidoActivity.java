package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;

public class DetalhesPedidoActivity extends AppCompatActivity {

    public static final String ID ="ID" ;
    private static final int EDITAR = 1 ;
    private static final int ADICIONAR =2 ;
    private static final int APAGAR =3 ;
    private FloatingActionButton fab_criarPedidoProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_pedido);

        FloatingActionButton fab_criarPedidoProduto = (FloatingActionButton) findViewById(R.id.fab_criarPedidoProduto);

        fab_criarPedidoProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Generic.isConnectionInternet(getApplicationContext())){
                    Intent intent=new Intent(getApplicationContext(), ListaProdutosActivity.class);
                    //startActivity(intent);
                    startActivityForResult(intent,ADICIONAR);
                }else{
                    Toast.makeText(getApplicationContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

  /*  *//**
     *
     * @param requestCode código enviado para a atividade
     * @param resultCode o código recebido pela atividade
     * @param data
     *//*
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case ADICIONAR:
                    SingletonGestorRestaurante.getInstance(getApplicationContext()).getPedidosAPI(getApplicationContext());
                    Toast.makeText(getApplicationContext(),"Pedido criado com sucesso", Toast.LENGTH_LONG).show();
                    break;
                case EDITAR:
                    SingletonGestorRestaurante.getInstance(getApplicationContext()).getPedidosAPI(getApplicationContext());
                    Toast.makeText(getApplicationContext(),"Livro editado com sucesso",Toast.LENGTH_LONG).show();
                    break;
                case APAGAR:
                    SingletonGestorRestaurante.getInstance(getApplicationContext()).getPedidosAPI(getApplicationContext());
                    Toast.makeText(getApplicationContext(),"Livro apagado com sucesso",Toast.LENGTH_LONG).show();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/

}