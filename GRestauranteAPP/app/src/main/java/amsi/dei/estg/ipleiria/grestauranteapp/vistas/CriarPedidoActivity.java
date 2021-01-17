package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PedidosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Pedido;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;

public class CriarPedidoActivity extends AppCompatActivity implements PedidosListener {

    public static final String ID ="ID" ;
    private TextView tvTipo;
    private EditText edtTipo;
    private ImageView imgvTipo;
    private Button btnCriarPedido;
    private Pedido pedido;
    private String ip, token,cargo;
    private int id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Criar Pedido");

        setContentView(R.layout.activity_criar_pedido);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTipo=findViewById(R.id.tvTipo);
        edtTipo=findViewById(R.id.edt_tipo);
        imgvTipo=findViewById(R.id.imgvTipo);
        btnCriarPedido=findViewById(R.id.btn_criarPedido);


        SharedPreferences sharedPrefInfoUser = getSharedPreferences(MenuActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        ip= sharedPrefInfoUser.getString(MenuActivity.IP,null);
        token= sharedPrefInfoUser.getString(MenuActivity.TOKEN,null);
        cargo= sharedPrefInfoUser.getString(MenuActivity.CARGO,null);
        id_user= sharedPrefInfoUser.getInt(MenuActivity.ID,-1);

        SingletonGestorRestaurante.getInstance(getApplicationContext()).setPedidosListener(this);


        if(cargo.equals("cliente")) {

            tvTipo.setText("Takeaway");
            edtTipo.setInputType(InputType.TYPE_CLASS_TEXT);
            edtTipo.setHint("Nome do pedido");
            imgvTipo.setImageResource(R.drawable.male);
        }else{
            tvTipo.setText("Restaurante");
            edtTipo.setInputType(InputType.TYPE_CLASS_NUMBER);
            edtTipo.setHint("Nº Mesa");
            imgvTipo.setImageResource(R.drawable.img_table);
        }

        btnCriarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Generic.isConnectionInternet(getApplicationContext())) {
                    if (edtTipo.getText() != null) {

                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String strDate = sdf.format(c.getTime());

                        if(cargo.equals("cliente")) {
                            pedido = new Pedido(0, id_user, 0, edtTipo.getText().toString(), null, 0, 1, strDate);
                        }else{
                            pedido = new Pedido(0, id_user, Integer.parseInt(edtTipo.getText().toString()),null, null, 0, 0, strDate);
                        }

                        SingletonGestorRestaurante.getInstance(getApplicationContext()).adicionarPedidoAPI(ip,token,pedido, getApplicationContext());

                    }
                }else{
                    Toast.makeText(getApplicationContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

/*    private boolean validarPedido() {

        String n_mesa=edtMesa.getText().toString();
        //TODO:Guardar valor da editText NomePedido

        //String nomePedido=edtNomepedido.getText().toString();


        if (n_mesa.length()==0) {
            edtMesa.setError("Mesa invalida");
            return false;
        }

        //TODO:Fazer validação nome pedido
        *//*if (nomePedido.length()<3) {
            edtNomePedido.setError("Serie Invalido");
            return false;
        }*//*

        return true;
    }*/


    @Override
    public void onRefreshListaPedidos(ArrayList<Pedido> pedidos) {

    }

    @Override
    public void onCreatePedido() {
        setResult(RESULT_OK);
        finish();
    }

}