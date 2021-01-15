package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

    private EditText editText_data_nascimento;
    private EditText edt_nome;
    private EditText edt_apelido;
    private EditText edt_morada;
    private EditText edt_codPostal;
    private EditText edt_telemovel;
    private EditText edt_nacionalidade;
    private RadioButton rb_masculino;
    private RadioButton rb_feminino;
    private EditText edt_username;
    private EditText edt_email;
    private EditText edt_password;
    private Button btn_dataNascimento;
    private Perfil perfil;

    private int year;
    private int month;
    private int day;
    private DatePickerDialog.OnDateSetListener onDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.fundo)));
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

        editText_data_nascimento = findViewById(R.id.edt_dataNascimento);

        btn_dataNascimento = findViewById(R.id.btn_dataNascimento);

        editText_data_nascimento.setEnabled(false);

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

                editText_data_nascimento.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };
    }

    public void onClickRegistar(View view) {

            if(validarRegisto()==true){



                 //   perfil.setNome(edt_nome.getText().toString());
                  //  perfil.setApelido(edt_apelido.getText().toString());
                    perfil.setMorada(edt_morada.getText().toString());
                    perfil.setCodigo_postal(edt_codPostal.getText().toString());
                    perfil.setTelemovel(edt_telemovel.getText().toString());
                    perfil.setNacionalidade(edt_nacionalidade.getText().toString());
                    if(rb_masculino.isChecked()){
                    perfil.setGenero("1");
                     }else{
                    perfil.setGenero("0");

                }

                    perfil.setUsername(edt_username.getText().toString());
                    perfil.setEmail(edt_email.getText().toString());
                    perfil.setNova_password(edt_password.getText().toString());

                    SingletonGestorRestaurante.getInstance(getApplicationContext()).adicionarUserAPI(perfil, getApplicationContext());


            }
    }



    private boolean validarRegisto(){
    String nome=edt_nome.getText().toString();
    String apelido=edt_apelido.getText().toString();
    String morada=edt_morada.getText().toString();
    String cod_postal=edt_codPostal.getText().toString();
    String telemovel=edt_telemovel.getText().toString();
    String nacionalidade=edt_nacionalidade.getText().toString();
        if(!rb_masculino.isChecked()||!rb_feminino.isChecked())
        {
            rb_masculino.setError("Tem que Selecionar 1 genero");
            rb_feminino.setError("Tem que Selecionar 1 genero");
        }
        String username=edt_username.getText().toString();
        String email=edt_email.getText().toString();
        String password=edt_password.getText().toString();

        if (nome.length()<3)
        {
            edt_nome.setError("Nome Invalido");
        }

        if (apelido.length()<3)
        {
            edt_apelido.setError("Apelido Invalido");
        }

        if (morada.length()<3)
        {
            edt_morada.setError("Morda Invalido");
        }

        if (cod_postal.length()<8)
        {
            edt_codPostal.setError("Codigo Postal Invalido");
        }

        if(telemovel.length()>9)
        {
            edt_telemovel.setError("Numero de telemovel invalido");
        }

        if(nacionalidade.length()<3)
        {
            edt_nacionalidade.setError("Nacionalidade Invalido");
        }

        if(!rb_masculino.isChecked()||!rb_feminino.isChecked())
        {
            rb_masculino.setError("Tem que Selecionar 1 genero");
            rb_feminino.setError("Tem que Selecionar 1 genero");
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