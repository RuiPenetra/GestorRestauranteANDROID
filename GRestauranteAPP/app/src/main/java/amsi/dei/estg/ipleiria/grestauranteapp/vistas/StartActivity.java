package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import amsi.dei.estg.ipleiria.grestauranteapp.R;

public class StartActivity extends AppCompatActivity {

    private Button btnLogin;
    private FloatingActionButton fabContactos,fabListaProdutos,fabConfiguracoes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnLogin=findViewById(R.id.btn_login);
        fabContactos=findViewById(R.id.fabConttactos);
        fabListaProdutos=findViewById(R.id.fabListaProdutos);
        fabConfiguracoes=findViewById(R.id.fabConfiguracoes);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        fabContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(StartActivity.this, ContactoActivity.class);
                startActivity(intent);
            }
        });

        fabListaProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(StartActivity.this, ListaProdutosActivity.class);
                startActivity(intent);
            }
        });


        fabListaProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(StartActivity.this, ListaProdutosActivity.class);
                startActivity(intent);
            }
        });


        fabConfiguracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(StartActivity.this, ConfiguracaoActivity.class);
                startActivity(intent);
            }
        });
    }
    
}