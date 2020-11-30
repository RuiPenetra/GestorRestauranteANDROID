package amsi.dei.estg.ipleiria.grestauranteapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import amsi.dei.estg.ipleiria.grestauranteapp.R;

public class RegistarActivity extends AppCompatActivity {

    public EditText editText_data_nascimento;
    private Button btn_dataNascimento;

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

        Intent intent = new Intent(this, MenuClienteActivity.class);
        startActivity(intent);
    }
}