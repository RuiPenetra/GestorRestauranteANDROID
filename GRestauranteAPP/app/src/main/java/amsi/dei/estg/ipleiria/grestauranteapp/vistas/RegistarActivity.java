package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.Calendar;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PerfilListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.RegistoListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Perfil;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;

public class RegistarActivity extends AppCompatActivity implements RegistoListener {

    private EditText edt_datanascimento,edt_nome,edt_apelido,edt_morada,edt_codPostal,edt_telemovel,edt_nacionalidade,edt_username,edt_email,edt_password;
    private RadioButton rb_masculino;
    private RadioButton rb_feminino;
    private Button btn_dataNascimento,btn_registar;
    private int year;
    private int month;
    private int day;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private Perfil perfil;
    private String ip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);

        setTitle("Registar");

        getSupportActionBar().setElevation(0);


        edt_nome=findViewById(R.id.edt_nome);
        edt_apelido=findViewById(R.id.edt_apelido);
        edt_morada=findViewById(R.id.edt_morada);
        edt_codPostal=findViewById(R.id.edt_codpostal);
        edt_telemovel=findViewById(R.id.edt_telemovel);
        edt_nacionalidade=findViewById(R.id.edt_nacionalidade);
        rb_masculino=findViewById(R.id.radio_masculino);
        rb_feminino=findViewById(R.id.radio_femenino);
        edt_username=findViewById(R.id.edt_username);
        edt_email=findViewById(R.id.edt_email);
        edt_password=findViewById(R.id.edt_password);
        btn_registar=findViewById(R.id.btnRegistar);

        edt_datanascimento = findViewById(R.id.edt_dataNascimento);
        btn_registar=findViewById(R.id.btn_Registar);

        btn_dataNascimento = findViewById(R.id.btn_dataNascimento);

        SharedPreferences sharedPrefInfoUser = getSharedPreferences(MenuActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        ip= sharedPrefInfoUser.getString(MenuActivity.IP,null);

        SingletonGestorRestaurante.getInstance(RegistarActivity.this).setRegistoListener(this);

        edt_datanascimento.setEnabled(false);

        btn_dataNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(RegistarActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();

            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month=month+1;
                edt_datanascimento.setText(year + "-" + month + "-" + dayOfMonth);
            }
        };



        btn_registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validarRegisto()==true){

                    String nome = edt_nome.getText().toString();
                    String apelido = edt_apelido.getText().toString();
                    String morada = edt_morada.getText().toString();
                    String codigoPostal = edt_codPostal.getText().toString();
                    String telemovel = edt_telemovel.getText().toString();
                    String nacionalidade = edt_nacionalidade.getText().toString();
                    String dataNascimento = edt_datanascimento.getText().toString();
                    String username = edt_username.getText().toString();
                    String email = edt_email.getText().toString();
                    String password = edt_password.getText().toString();

                    int genero;
                    if(rb_masculino.isChecked()){
                        genero=1;
                    }else{
                        genero=0;

                    }

                    perfil = new Perfil(0,username,password,email,nome,apelido,morada,nacionalidade,null,codigoPostal,genero,telemovel,dataNascimento,null);

                    SingletonGestorRestaurante.getInstance(getApplicationContext()).adicionarUserAPI(ip,perfil, getApplicationContext());

                }

            }
        });
    }

    private boolean validarRegisto(){

        String nome=edt_nome.getText().toString();
        String apelido=edt_apelido.getText().toString();
        String morada=edt_morada.getText().toString();
        String cod_postal=edt_codPostal.getText().toString();
        String telemovel=edt_telemovel.getText().toString();
        String nacionalidade=edt_nacionalidade.getText().toString();
        String dataNascimento = edt_datanascimento.getText().toString();
        String username=edt_username.getText().toString();
        String email=edt_email.getText().toString();
        String password=edt_password.getText().toString();

        if(!rb_masculino.isChecked()&&!rb_feminino.isChecked())
        {
            rb_masculino.setError("Tem que Selecionar 1 genero");
            rb_feminino.setError("Tem que Selecionar 1 genero");
            return false;

        }

        if (nome.length()<3)
        {
            edt_nome.setError("Nome Invalido");
            return false;
        }

        if (apelido.length()<3)
        {
            edt_apelido.setError("Apelido Invalido");
            return false;
        }

        if (morada.length()<3)
        {
            edt_morada.setError("Morada Invalido");
            return false;
        }

        if (cod_postal.length()<8)
        {
            edt_codPostal.setError("Codigo Postal Invalido");
            return false;
        }

        if(telemovel.length()!=9)
        {
            edt_telemovel.setError("Numero de telemovel invalido");
            return false;
        }

        if(nacionalidade.length()<3)
        {
            edt_nacionalidade.setError("Nacionalidade Invalido");
            return false;
        }

        if(dataNascimento.isEmpty())
        {
            edt_datanascimento.setError("Data Nascimento Invalida");
            return false;
        }

        if(username.length()<3){
            edt_username.setError("Username invalido");
            return false;
        }

        if (!isEmailValido(email)) {
            edt_email.setError("Email Invalido");
            return false;
        }

        if (!isPasswordValida(password)) {
            edt_password.setError("Password Invalida");
            return false;
        }
        return true;
    }

    private boolean isEmailValido(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValida(String password){
        if(password==null)
            return false;

        return password.length()>=4;
    }

    @Override
    public void onRegistar() {
        setResult(RESULT_OK);
        finish();
    }
}