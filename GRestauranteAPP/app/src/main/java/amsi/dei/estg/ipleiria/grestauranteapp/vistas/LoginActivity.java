package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.LoginListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;

import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.ProdutoJsonParser;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    public static final String USERNAME = "USERNAME";
    public static final String TOKEN = "TOKEN";
    public static final String PASSWORD = "PASSWORD";
    private static final String PREF_INFO_USER = "PREF_INFO_USER";
    private String username = "";
    private String password = "";
    private EditText etUsername, etPassword;
    private CheckBox RememberMe;
    private Button login;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean savelogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.fundo)));
        setTitle("Login");

        getSupportActionBar().setElevation(0);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        RememberMe = findViewById(R.id.Remember);
        login = findViewById(R.id.btn_login);
        sharedPreferences=getSharedPreferences(PREF_INFO_USER,MODE_PRIVATE);
        editor=sharedPreferences.edit();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            login();

            }
        });
            savelogin=sharedPreferences.getBoolean("savelogin",true);
            if (savelogin==true){
                etUsername.setText(sharedPreferences.getString("username",null));
                etPassword.setText(sharedPreferences.getString("password",null));

                RememberMe.setChecked(true);
            }
            else
                {

                }

        SingletonGestorRestaurante.getInstance(getApplicationContext()).setLoginListener(this);

    }

    private boolean isPasswordValida(String password) {
        if (password == null)
            return false;

        return password.length() >= 4;
    }

    public void login() {
        String username= etUsername.getText().toString();
        String password=etPassword.getText().toString();

        if (RememberMe.isChecked()){
            editor.putBoolean("savelogin",true);
            editor.putString("username",username);
            editor.putString("password",password);
            editor.putString("token",TOKEN);
            editor.commit();

        }else {
            editor.putBoolean("savelogin",false);
            editor.remove("username");
            editor.remove("password");
            editor.remove("token");
            editor.commit();

        }

        if (ProdutoJsonParser.isConnectionInternet(getApplicationContext())) {
            SingletonGestorRestaurante.getInstance(getApplicationContext()).loginAPI(username, password, getApplicationContext());
        } else
            Toast.makeText(getApplicationContext(), "Não existe ligação a internet", Toast.LENGTH_SHORT).show();
    }

    public void onClickLogin(View view) {
        if (ProdutoJsonParser.isConnectionInternet(getApplicationContext())) {
            SharedPreferences preferences = getSharedPreferences(PREF_INFO_USER, MODE_PRIVATE);
            if(TOKEN!=null)
            {
                onValidateLogin(TOKEN,username,password);
            }
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();


            SingletonGestorRestaurante.getInstance(getApplicationContext()).loginAPI(username, password, getApplicationContext());
        }
    }


    public void onClickRegistar(View view) {
        Intent intent = new Intent(this, RegistarActivity.class);
        startActivity(intent);
        if (Generic.isConnectionInternet(getApplicationContext())) {

            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            //VERICA SE A PASSWORD TEM 4 CARACTERES
            /** if(!isPasswordValida(password)){
             etPassword.setError("Password Invalida");
             return;
             }**/
            SingletonGestorRestaurante.getInstance(getApplicationContext()).loginAPI(username, password, getApplicationContext());

        } else
            Toast.makeText(getApplicationContext(), "Não existe ligação à internet", Toast.LENGTH_SHORT).show();

        /**Intent intent = new Intent(this, RegistarActivity.class);
         startActivity(intent);**/
    }

    @Override
    public void onValidateLogin(String token, String username, String password) {
        if (token != null) {
            Intent intent= new Intent(this,MenuFuncionarioActivity.class);
            startActivity(intent);
        } else {

            Toast.makeText(this, "Erro ao ir buscar token", Toast.LENGTH_SHORT).show();
        }
    }

}