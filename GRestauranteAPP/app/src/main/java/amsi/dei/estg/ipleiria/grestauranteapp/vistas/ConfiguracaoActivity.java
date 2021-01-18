package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import amsi.dei.estg.ipleiria.grestauranteapp.R;

public class ConfiguracaoActivity extends AppCompatActivity {

    public static final String IP="IP";
    public static final String PREF_INFO_USER="PREF_INFO_USER";
    private Button btn_guardar;
    private EditText edt_endereçoIP;
    private String enderecoIP;
    @Override
    // GUARDA O IP DA API CASO SEJA IP VALIDO
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        setTitle("Configurações");
        btn_guardar=findViewById(R.id.bnGuardarIp);
        edt_endereçoIP=findViewById(R.id.edtEnderecoIP);

        SharedPreferences sharedPrefUser = getSharedPreferences(PREF_INFO_USER, Context.MODE_PRIVATE);
        enderecoIP=sharedPrefUser.getString(IP,null);

        edt_endereçoIP.setText(enderecoIP);

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enderecoIP=edt_endereçoIP.getText().toString();
                enderecoIP=enderecoIP.trim();

                if(enderecoIP.length()<10){

                    edt_endereçoIP.setError("IP inválido");
                }else{
                    SharedPreferences sharedPrefUser = getSharedPreferences(LoginActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPrefUser.edit();
                    editor.putString(IP,enderecoIP);
                    editor.apply();
                    Toast.makeText(ConfiguracaoActivity.this, "Ip guardado com sucesso", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}