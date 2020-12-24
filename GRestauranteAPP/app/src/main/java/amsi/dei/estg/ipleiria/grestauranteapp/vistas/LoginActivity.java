package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;

public class LoginActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    private EditText etUsername,etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.fundo)));
        setTitle("Login");

        getSupportActionBar().setElevation(0);

        etUsername=findViewById(R.id.etUsername);
        etPassword=findViewById(R.id.etPassword);

        SingletonGestorRestaurante.getInstance(getApplicationContext()).setLoginListener(this);



    }

    private boolean isPasswordValida(String password){
        if(password==null)
            return false;

        return password.length()>=4;
    }

    public void onClickLogin(View view) {
    }

    public void onClickRegistar(View view) {
        Intent intent = new Intent(this, RegistarActivity.class);
        startActivity(intent);
        if(Generic.isConnectionInternet(getApplicationContext())){

            String username=etUsername.getText().toString();
            String password=etPassword.getText().toString();
           //VERICA SE A PASSWORD TEM 4 CARACTERES
           /** if(!isPasswordValida(password)){
               etPassword.setError("Password Invalida");
               return;
           }**/
            SingletonGestorRestaurante.getInstance(getApplicationContext()).loginAPI(username,password,getApplicationContext());

        }else
            Toast.makeText(getApplicationContext(),"Não existe ligação à internet", Toast.LENGTH_SHORT).show();

        /**Intent intent = new Intent(this, RegistarActivity.class);
        startActivity(intent);**/
    }
}