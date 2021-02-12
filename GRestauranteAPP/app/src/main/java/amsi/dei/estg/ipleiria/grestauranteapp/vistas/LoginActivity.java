package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.AutenticacaoListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PerfilListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Perfil;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;

import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;

public class LoginActivity extends AppCompatActivity implements AutenticacaoListener, PerfilListener {

    public static final String PREF_INFO_USER = "PREF_INFO_USER";
    public static final String IP = "IP";
    public static final String CARGO = "CARGO";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String RELEMBRAR = "RELEMBRAR";
    private static final int CRIAR=1;
    private EditText edtUsername, edtPassword;
    private CheckBox cbRememberMe;
    private boolean relembrar;
    private Button btn_login;
    private String ip,username,email,password,token;
    private int id_user;


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
        SingletonGestorRestaurante.getInstance(getApplicationContext()).setPerfilListener(this);
    }
    //CARREGA A INFORMAÇÃO DA SHARED
    private void lerShared() {
        SharedPreferences sharedPrefInfoUser = getSharedPreferences(PREF_INFO_USER, Context.MODE_PRIVATE);

        ip=sharedPrefInfoUser.getString(IP,null);

        //TODO: Ainda a ver melhor
        relembrar=sharedPrefInfoUser.getBoolean(RELEMBRAR,false);

        if(relembrar==true){

            cbRememberMe.setChecked(true);
            edtUsername.setText(sharedPrefInfoUser.getString(USERNAME,null));
            edtPassword.setText(sharedPrefInfoUser.getString(PASSWORD,null));
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
         username=edtUsername.getText().toString();
         password=edtPassword.getText().toString();

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

        return username.length()>=3;
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
            Toast.makeText(this, "Registo feito com sucesso!", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    //MUDA PARA A ATIVIDADE REGISTAR
    public void onClickRegistar(View view) {
            Intent intent = new Intent(this, RegistarActivity.class);
            startActivityForResult(intent,CRIAR);
    }

    //VE SE O UTILIZADOR EXISTE
    @Override
    public void onValidarLogin(Perfil perfil) {
        if (perfil != null) {
            username=perfil.getUsername();
            email=perfil.getEmail();
            token=perfil.getToken();
            SingletonGestorRestaurante.getInstance(LoginActivity.this).getPerfilAPI(ip,token,getApplicationContext());
        } else {
            Toast.makeText(getApplicationContext(), "Utilizador não existe", Toast.LENGTH_SHORT).show();
        }
    }

    //GUARDA INFORMAÇÃO NA SHARED
    private void guardarInfoShared(Perfil perfil){
        SharedPreferences sharedPrefUser = getSharedPreferences(MenuActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefUser.edit();

        if(!perfil.getCargo().equals("cliente")){
            editor.putInt(MenuActivity.ID,perfil.getId());
            editor.putString(MenuActivity.TOKEN,token);
            editor.putString(MenuActivity.CARGO,perfil.getCargo());
            editor.putString(MenuActivity.EMAIL,email);
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

        }else{
            editor.putInt(MenuClienteActivity.ID,perfil.getId());
            editor.putString(MenuClienteActivity.TOKEN,token);
            editor.putString(MenuClienteActivity.CARGO,perfil.getCargo());
            editor.putString(MenuClienteActivity.EMAIL,email);
            editor.putString(MenuClienteActivity.NOMECOMPLETO,perfil.getNome()+" "+perfil.getApelido());
            editor.putInt(MenuClienteActivity.GENERO,perfil.getGenero());

            if(cbRememberMe.isChecked()){
                editor.putBoolean(MenuClienteActivity.RELEMBRAR,true);
                editor.putString(MenuClienteActivity.USERNAME,edtUsername.getText().toString());
                editor.putString(MenuClienteActivity.PASSWORD,edtPassword.getText().toString());
            }else{
                editor.putBoolean(MenuClienteActivity.RELEMBRAR,false);
            }

            editor.apply();
        }
    }

    @Override
    public void onRefreshPerfil(Perfil perfil) {
        if(perfil!=null){
            guardarInfoShared(perfil);

            Intent intent=null;

            if(!perfil.getCargo().equals("cliente")){
                intent= new Intent(this, MenuActivity.class);
            }else{
                intent= new Intent(this, MenuClienteActivity.class);
            }
            startActivity(intent);
            finish();

        }
    }

    @Override
    public void onUpdatePerfil() {

    }

    @Override
    public void onValidarRegisto() {

    }
}