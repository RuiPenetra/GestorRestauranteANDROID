package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

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
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;

public class LoginActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    private EditText etUsername,etPassword;
    private CheckBox RememberMe;
    private Button login;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean savelogin;

    private static final String SHARED_PREF_NAME="userdata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.fundo)));
        setTitle("Login");

        getSupportActionBar().setElevation(0);

        etUsername=findViewById(R.id.etUsername);
        etPassword=findViewById(R.id.etPassword);
        login=findViewById(R.id.btn_login);

        SingletonGestorRestaurante.getInstance(getApplicationContext()).setLoginListener(this);

        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        RememberMe=findViewById(R.id.Remember);
        editor=sharedPreferences.edit();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        savelogin=sharedPreferences.getBoolean("savelogin",true);
        if(savelogin==true){
            etUsername.setText(sharedPreferences.getString("username",null));
            etPassword.setText(sharedPreferences.getString("password",null));
        }
    }

    private boolean isPasswordValida(String password){
        if(password==null)
            return false;

        return password.length()>=4;
    }

    public void login(){
        String username=etUsername.getText().toString();
        String password=etPassword.getText().toString();
        if(RememberMe.isChecked()){
            editor.putBoolean("savelogin",true);
            editor.putString("username",username);
            editor.putString("password",password);
            editor.commit();
        }
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