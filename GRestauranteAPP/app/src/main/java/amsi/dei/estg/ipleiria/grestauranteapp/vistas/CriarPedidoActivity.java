package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    private LinearLayout lilRestaurante;
    private LinearLayout lilTakeaway;
    private Button btnCriarPedido;
    private EditText edtMesa;
    private Pedido pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Criar Pedido");

        setContentView(R.layout.activity_criar_pedido);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lilRestaurante=findViewById(R.id.lilRestaurante);
        lilTakeaway=findViewById(R.id.lilTakeaway);
        btnCriarPedido=findViewById(R.id.btn_criarPedido);
        edtMesa=findViewById(R.id.edt_mesa);

        lilTakeaway.setVisibility(View.INVISIBLE);

        SingletonGestorRestaurante.getInstance(getApplicationContext()).setPedidosListener(this);

        btnCriarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Generic.isConnectionInternet(getApplicationContext())) {
                    if (validarPedido() == true) {
                        //TODO:Validar tipo de utilizador e consoante o cargo guardado na shared preferences
                        //TODO: Criar pedido takeaway consoante o cargo

                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String strDate = sdf.format(c.getTime());

                        pedido = new Pedido(0, 1, Integer.parseInt(edtMesa.getText().toString()), null, null, 0, 0, strDate);

                        SingletonGestorRestaurante.getInstance(getApplicationContext()).adicionarPedidoAPI(pedido, getApplicationContext());

                    }
                }else{
                    Toast.makeText(getApplicationContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validarPedido() {

        String n_mesa=edtMesa.getText().toString();
        //TODO:Guardar valor da editText NomePedido

        //String nomePedido=edtNomepedido.getText().toString();


        if (n_mesa.length()==0) {
            edtMesa.setError("Mesa invalida");
            return false;
        }

        //TODO:Fazer validação nome pedido
        /*if (nomePedido.length()<3) {
            edtNomePedido.setError("Serie Invalido");
            return false;
        }*/

        return true;
    }


    @Override
    public void onRefreshListaPedidos(ArrayList<Pedido> pedidos) {

    }

    @Override
    public void onRefreshCriar() {
        setResult(RESULT_OK);
        finish();
    }
}