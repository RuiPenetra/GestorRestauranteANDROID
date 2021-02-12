package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.CarrinhoListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PedidosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Carrinho;
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
    private ArrayList<Carrinho> itemsCarrinho;
    private int id_user;

    @Override
    //VAI BUSCAR SHARED PREFERENCE CRIA PEDIDO TAKEAWAY CASO SEJA CLIENTE E PEDIDO DE RESTAURANTE CASO O CARGO NAO SEJA CLIENTE
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Criar Pedido");

        setContentView(R.layout.activity_criar_pedido);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTipo=findViewById(R.id.tvTipo);
        edtTipo=findViewById(R.id.edt_tipo);
        imgvTipo=findViewById(R.id.imgvTipo);
        btnCriarPedido=findViewById(R.id.btn_criarPedido);


        //TODO:Ainda a ver
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
            imgvTipo.setImageResource(R.drawable.takeaway);
            btnCriarPedido.setText("Concluir Pedido");
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
                    if (validarPedido()==true) {

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
    //VALIDAÇÃO DO PEDIDO
    private boolean validarPedido() {
        if (cargo.equals("cliente")) {

            String nomePedido=edtTipo.getText().toString();


            if (nomePedido.isEmpty()) {
                edtTipo.setError("O nome do Pedido nao pode estar vazio");
                return false;
            }

            if (nomePedido.length() < 3) {
                edtTipo.setError("Nome Pedido demasiado pequeno");
                return false;
            }
            if(!nomePedido.toString().matches("[a-zA-Z ]+")){
                edtTipo.setError("Nome Pedido so pode conter letras");
                return false;
            }

        } else {
           String n_mesa = edtTipo.getText().toString();

            if(TextUtils.isEmpty(n_mesa)) {
                edtTipo.setError("Não pode ser vazio");
                return false;
            }
        }

        return true;
    }

    public void guardarItemsCarrinhoAPI(){
        itemsCarrinho=SingletonGestorRestaurante.getInstance(getApplicationContext()).getItemsCarrinho();

        for (Carrinho item:itemsCarrinho) {

            SingletonGestorRestaurante.getInstance(getApplicationContext()).adicionarItemsCarrinhoAPI(ip, token, item,itemsCarrinho.size(), getApplicationContext());
        }

        SingletonGestorRestaurante.getInstance(getApplicationContext()).removerItemsCarrinhoBD(id_user);
    }

    @Override
    public void onRefreshListaPedidos(ArrayList<Pedido> pedidos) {

    }

    @Override
    public void onCriarPedidoTakeaway() {
        guardarItemsCarrinhoAPI();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onCriarPedidoRestaurante() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        SingletonGestorRestaurante.getInstance(this).setPedidosListener(this);
    }

}