package amsi.dei.estg.ipleiria.grestauranteapp.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import amsi.dei.estg.ipleiria.grestauranteapp.R;

public class AdminControlerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_controler);
    }

    public void onClickFuncionario(View view) {

  /*      *//**//*Intent intent = new Intent(this, Men.class);
        startActivity(intent);*/
    }

    public void onClickCliente(View view) {
        Intent intent = new Intent(this, MenuClienteActivity.class);
        startActivity(intent);
    }
}