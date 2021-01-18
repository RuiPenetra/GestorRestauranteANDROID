package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.LoginListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Perfil;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;

import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.ProdutoJsonParser;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    public static final String PREF_INFO_USER = "PREF_INFO_USER";
    public static final String IP = "IP";
    private static final int CRIAR=1;
    private EditText edtUsername, edtPassword;
    private CheckBox cbRememberMe;
    private Button btn_login;
    private String ip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login");

        getSupportActionBar().setElevation(0);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        cbRememberMe = findViewById(R.id.Remember);
        btn_login = findViewById(R.id.btn_login);

        lerShared();

        SingletonGestorRestaurante.getInstance(getApplicationContext()).setLoginListener(this);
    }
    //CARREGA A INFORMAÇÃO DA SHARED
    private void lerShared() {
        SharedPreferences sharedPrefInfoUser = getSharedPreferences(PREF_INFO_USER, Context.MODE_PRIVATE);

        ip=sharedPrefInfoUser.getString("IP",null);

        boolean relembrar=sharedPrefInfoUser.getBoolean(MenuActivity.RELEMBRAR,false);

        if(relembrar==true){

            cbRememberMe.setChecked(true);
            edtUsername.setText(sharedPrefInfoUser.getString(MenuActivity.USERNAME,null));
            edtPassword.setText(sharedPrefInfoUser.getString(MenuActivity.PASSWORD,null));
        }

    }
    //TESTA LIGAÇÃO A INTERNET E TESTA A VALIDAÇÃO DO LOGIN
    public void onClickLogin(View view) {
        if (Generic.isConnectionInternet(getApplicationContext())) {

            if(Validarlogin()==true) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                SingletonGestorRestaurante.getInstance(getApplicationContext()).loginAPI(ip, username, password, getApplicationContext());
            }

        }else{
            Toast.makeText(getApplicationContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();

        }
    }
    //VALIDA LOGIN
    private boolean Validarlogin() {
        String username=edtUsername.getText().toString();
        String password=edtPassword.getText().toString();


        if (!isUsernameValido(username)){
            edtUsername.setError("Username não pode ser vazio");
            return false;
        }
        if (!isPasswordValida(password)){
            edtPassword.setError("Password não pode estar vazia");
            return false;
        }
        if(ip==null){
            Toast.makeText(this, "Tem de defenir o endereço de ip", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    //VALIDA USERNAME
    private boolean isUsernameValido(String username){
        if(username==null)
            return false;

        return username.length()>3;
    }
    //VALIDA PASSWORD
    private boolean isPasswordValida(String password){
        if(password==null)
            return false;

        return password.length()>=4;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode== Activity.RESULT_OK && requestCode==CRIAR)
        {
            Toast.makeText(this, "Utilizador Registado com sucesso!", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    //MUDA PARA A ATIVIDADE REGISTAR
    public void onClickRegistar(View view) {
            Intent intent = new Intent(this, RegistarActivity.class);
            startActivityForResult(intent,CRIAR);
    }

    @Override
    //VE SE O UTILIZADOR EXISTE
    public void onValidateLogin(Perfil perfil) {
        if (perfil != null) {
            guardarInfoShared(perfil);
            Intent intent= new Intent(this, MenuActivity.class);
            startActivity(intent);
            finish();

        } else {

            Toast.makeText(this, "Utilizador não existe", Toast.LENGTH_SHORT).show();
        }
    }
    //GUARDA INFORMAÇÃO NA SHARED
    private void guardarInfoShared(Perfil perfil){

        SharedPreferences sharedPrefUser = getSharedPreferences(MenuActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefUser.edit();
        editor.putInt(MenuActivity.ID,perfil.getId());
        editor.putString(MenuActivity.TOKEN,perfil.getToken());
        editor.putString(MenuActivity.CARGO,perfil.getCargo());
        editor.putString(MenuActivity.NOMECOMPLETO,perfil.getNome()+" "+perfil.getApelido());
        editor.putInt(MenuActivity.GENERO,perfil.getGenero());

        if(cbRememberMe.isChecked()){
            editor.putBoolean(MenuActivity.RELEMBRAR,true);
            editor.putString(MenuActivity.USERNAME,edtUsername.getText().toString());
            editor.putString(MenuActivity.PASSWORD,edtPassword.getText().toString());
        }else{
            editor.putBoolean(MenuActivity.RELEMBRAR,false);
        }

        editor.apply();
    }

}