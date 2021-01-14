package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.Calendar;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.adaptadores.ListaProdutoAdaptador;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PerfilListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.ProdutosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Perfil;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.PerfilJsonParser;

public class PerfilFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, PerfilListener {

    private EditText edt_nome,edt_apelido,edt_morada, edt_codigoPostal, edt_telemovel, edt_nacionalidade, edt_email, edt_username, edt_nova_password, edt_data_nascimento;
    private RadioButton rb_masculino, rb_feminino;
    private Button btn_dataNascimento;
    private Button btn_atualizar;
    private Perfil auxPerfil;
    private int year;
    private int month;
    private int day;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ActionBar actionBar = null;
        
        View view= inflater.inflate(R.layout.fragment_perfil,container,false);
        btn_dataNascimento = view.findViewById(R.id.btn_dataNascimento);
        edt_nome= view.findViewById(R.id.edt_nome);
        edt_apelido= view.findViewById(R.id.edt_apelido);
        edt_morada= view.findViewById(R.id.edt_morada);
        edt_codigoPostal= view.findViewById(R.id.edt_codigopostal);
        edt_data_nascimento = view.findViewById(R.id.edt_dataNascimento);
        edt_nacionalidade= view.findViewById(R.id.edt_nacionalidade);
        edt_username= view.findViewById(R.id.edt_username);
        edt_telemovel= view.findViewById(R.id.edt_telemovel);
        edt_email= view.findViewById(R.id.edt_email);
        edt_nova_password= view.findViewById(R.id.edt_password);
        rb_masculino= view.findViewById(R.id.rb_masculino);
        rb_feminino= view.findViewById(R.id.rb_feminino);
        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        btn_atualizar=view.findViewById(R.id.btn_atualizar);

        swipeRefreshLayout.setOnRefreshListener(this);

        //TODO: SharedPreferences get Token user
        SingletonGestorRestaurante.getInstance(getContext()).getPerfilAPI(getContext());
        Toast.makeText(getContext(), ""+auxPerfil, Toast.LENGTH_SHORT).show();
        SingletonGestorRestaurante.getInstance(getContext()).setPerfilListener(this);
//        Toast.makeText(getContext(), ""+perfil, Toast.LENGTH_SHORT).show();

        edt_data_nascimento.setEnabled(false);
        btn_dataNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, onDateSetListener, day, month, year);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();

            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                edt_data_nascimento.setText(year  + "-" + month + "-" + dayOfMonth);
            }
        };

        btn_atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Generic.isConnectionInternet(getContext())) {

                    if (validarPerfil() == true) {

                        auxPerfil.setNome(edt_nome.getText().toString());
                        auxPerfil.setApelido(edt_apelido.getText().toString());
                        auxPerfil.setMorada(edt_morada.getText().toString());
                        auxPerfil.setCodigo_postal(edt_codigoPostal.getText().toString());
                        auxPerfil.setDatanascimento(edt_data_nascimento.getText().toString());
                        auxPerfil.setNacionalidade(edt_nacionalidade.getText().toString());
                        auxPerfil.setTelemovel(edt_telemovel.getText().toString());

                            if(rb_masculino.isChecked()){
                                auxPerfil.setGenero("1");
                            }else{
                                auxPerfil.setGenero("0");

                            }
                        auxPerfil.setUsername(edt_username.getText().toString());
                        auxPerfil.setEmail(edt_email.getText().toString());
                            if(edt_nova_password.getText().toString()!=""){
                                auxPerfil.setNova_password(edt_nova_password.getText().toString());
                            }
                            SingletonGestorRestaurante.getInstance(getContext()).updatePerfilAPI(auxPerfil, getContext());

                        }else{
                            Toast.makeText(getContext(), "Errrrooo", Toast.LENGTH_SHORT).show();
                        }

                }else{
                    Toast.makeText(getContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;

    }

    private boolean validarPerfil() {
        return true;
    }


    @Override
    public void onRefreshPerfil(Perfil perfil) {
        edt_nome.setText(perfil.getNome());
        edt_apelido.setText(perfil.getApelido());
        edt_morada.setText(perfil.getMorada());
        edt_codigoPostal.setText(perfil.getCodigo_postal());
        edt_data_nascimento.setText(perfil.getDatanascimento());
        edt_nacionalidade.setText(perfil.getNacionalidade());
        edt_telemovel.setText(perfil.getTelemovel());
        edt_username.setText(perfil.getUsername());
        edt_email.setText(perfil.getEmail());

        if(perfil.getGenero().equals("Masculino")){
            rb_masculino.setChecked(true);
        }else{
            rb_feminino.setChecked(true);

        }
        auxPerfil=perfil;
//        edt_nova_password.setText(perfil.getPassword());

    }

    @Override
    public void onRefreshPerfilUpdate() {
        Toast.makeText(getContext(), "Perfil Atualizado", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRefreshRegistar() {
        //empty
    }



    @Override
    public void onRefresh() {
        SingletonGestorRestaurante.getInstance(getContext()).getPerfilAPI(getContext());
        swipeRefreshLayout.setRefreshing(false);
    }
}
