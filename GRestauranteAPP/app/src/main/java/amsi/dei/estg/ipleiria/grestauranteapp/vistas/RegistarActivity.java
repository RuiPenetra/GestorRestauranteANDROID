package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import java.util.Calendar;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PerfilListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Perfil;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;

public class RegistarActivity extends AppCompatActivity implements PerfilListener {

    public EditText editText_data_nascimento;
    public EditText nome;
    public EditText apelido;
    public EditText morada;
    public EditText codPostal;
    public EditText telemovel;
    public EditText nacionalidade;
    public RadioButton masculino;
    public RadioButton feminino;
    public EditText edt_username;
    public EditText edt_email;
    public EditText edt_password;
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


        nome=findViewById(R.id.et_nome);
        apelido=findViewById(R.id.et_apelido);
        morada=findViewById(R.id.et_morada);
        codPostal=findViewById(R.id.et_codpostal);
        telemovel=findViewById(R.id.et_telemovel);
        nacionalidade=findViewById(R.id.et_nacionalidade);
        masculino=findViewById(R.id.radio_pirates);
        feminino=findViewById(R.id.radio_ninjas);
        edt_username=findViewById(R.id.et_username);
        edt_email=findViewById(R.id.et_email);
        edt_password=findViewById(R.id.et_password);

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


                    perfil.setUsername(edt_username.getText().toString());
                    perfil.setEmail(edt_email.getText().toString());
                    perfil.setNova_password(edt_password.getText().toString());
                    SingletonGestorRestaurante.getInstance(getApplicationContext()).adicionarUserAPI(perfil, getApplicationContext());


            }
    }

    private boolean validarRegisto(){
        String username=edt_username.getText().toString();
        String email=edt_email.getText().toString();
        String password=edt_password.getText().toString();

        if(username.length()<3){
            edt_username.setError("Username invalido");
            return false;
        }

        if(email.length()<3){
            edt_email.setError("Email invalido");
        }

        if(password.length()<3){
            edt_password.setError("Password invalida");
        }
        return true;
    }

    @Override
    public void onRefreshPerfil(Perfil perfil) {
        //EMPTY
    }

    @Override
    public void onRefreshPerfilUpdate() {
        //EMPTY
    }

    @Override
    public void onRefreshRegistar() {
    setResult(RESULT_OK);
    finish();
    }
}