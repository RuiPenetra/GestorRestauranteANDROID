package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;
import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PerfilListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Perfil;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;


public class PerfilFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, PerfilListener {

    private EditText edt_nome,edt_apelido,edt_morada, edt_codigoPostal, edt_telemovel, edt_nacionalidade, edt_email, edt_username, edt_nova_password, edt_data_nascimento;
    private RadioButton rb_masculino, rb_feminino;
    private Button btn_dataNascimento;
    private Button btn_atualizar;
    private ImageView imgPerfil;
    private TextView tvNomeCompleto,tvCargo;
    private int year,month,day, genero;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String ip,nomeCompleto,token,username,email;
    private Perfil auxPerfil;
    private NavigationView navigationView;


    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_perfil,container,false);


        btn_dataNascimento = view.findViewById(R.id.btnDataNascimento);
        edt_nome= view.findViewById(R.id.edtNome);
        edt_apelido= view.findViewById(R.id.edtApelido);
        edt_morada= view.findViewById(R.id.edtMorada);
        edt_codigoPostal= view.findViewById(R.id.edtCodigopostal);
        edt_data_nascimento = view.findViewById(R.id.edtDataNascimento);
        edt_nacionalidade= view.findViewById(R.id.edtNacionalidade);
        edt_username= view.findViewById(R.id.edtusername);
        edt_telemovel= view.findViewById(R.id.edtTelemovel);
        edt_email= view.findViewById(R.id.edtEmail);
        edt_nova_password= view.findViewById(R.id.edtPassword);
        rb_masculino= view.findViewById(R.id.rb_masculino);
        rb_feminino= view.findViewById(R.id.rb_feminino);
        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        btn_atualizar=view.findViewById(R.id.btn_atualizar);
        tvNomeCompleto=view.findViewById(R.id.tvNome_completo);
        tvCargo= view.findViewById(R.id.tvCargo);
        imgPerfil=view.findViewById(R.id.img_perfil);
         navigationView = view.findViewById(R.id.nav_view);

        swipeRefreshLayout.setOnRefreshListener(this);

        SharedPreferences sharedPrefInfoUser = getActivity().getSharedPreferences(MenuActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        ip= sharedPrefInfoUser.getString(MenuActivity.IP,null);
        token= sharedPrefInfoUser.getString(MenuActivity.TOKEN,null);
        username= sharedPrefInfoUser.getString(MenuActivity.USERNAME,null);
        email= sharedPrefInfoUser.getString(MenuActivity.EMAIL,null);

        SingletonGestorRestaurante.getInstance(getContext()).setPerfilListener(this);
        SingletonGestorRestaurante.getInstance(getContext()).getPerfilAPI(ip,token,getContext());


        //Atualziar password futuramente
        edt_nova_password.setEnabled(false);
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
                month=month+1;
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
                                auxPerfil.setGenero(1);
                            }else{
                                auxPerfil.setGenero(0);

                            }
                            auxPerfil.setUsername(edt_username.getText().toString());
                            auxPerfil.setEmail(edt_email.getText().toString());
                            if(edt_nova_password.getText().toString()!=""){
                                auxPerfil.setNova_password(edt_nova_password.getText().toString());
                            }
                            SingletonGestorRestaurante.getInstance(getContext()).updatePerfilAPI(ip,token,auxPerfil,getContext());

                        }else{
                            Toast.makeText(getContext(), "Verifique os campos", Toast.LENGTH_SHORT).show();
                        }

                }else{
                    Toast.makeText(getContext(), R.string.noInternet, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;

    }

    private boolean validarPerfil() {
        String nome=edt_nome.getText().toString();
        String apelido=edt_apelido.getText().toString();
        String morada=edt_morada.getText().toString();
        String cod_postal=edt_codigoPostal.getText().toString();
        String telemovel=edt_telemovel.getText().toString();
        String nacionalidade=edt_nacionalidade.getText().toString();
        String dataNascimento = edt_data_nascimento.getText().toString();
        String username=edt_username.getText().toString();
        String email=edt_email.getText().toString();
        //String password=edt_password.getText().toString();

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
            edt_codigoPostal.setError("Codigo Postal Invalido");
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
            edt_data_nascimento.setError("Data Nascimento Invalida");
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

    /*    if (!isPasswordValida(password)) {
            edt_password.setError("Password Invalida");
            return false;
        }*/
        return true;
    }
    //VALIDAÇÂO DO EMAIL
    private boolean isEmailValido(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    //VALIDAÇÃO DA PASSWORD
    private boolean isPasswordValida(String password){
        if(password==null)
            return false;

        return password.length()>=4;
    }

    private void carregardDadosPerfil(Perfil perfil) {

        SharedPreferences sharedPrefInfoUser = getActivity().getSharedPreferences(MenuActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        nomeCompleto= sharedPrefInfoUser.getString(MenuActivity.NOMECOMPLETO,null);
        genero= sharedPrefInfoUser.getInt(MenuActivity.GENERO,-1);
        username= sharedPrefInfoUser.getString(MenuActivity.USERNAME,null);

        tvNomeCompleto.setText(perfil.getNome()+ " " + perfil.getApelido());
        tvCargo.setText(perfil.getCargo());

        if(genero==1){
            imgPerfil.setImageResource(R.drawable.male);
        }else{
            imgPerfil.setImageResource(R.drawable.female);
        }

        edt_nome.setText(perfil.getNome());
        edt_apelido.setText(perfil.getApelido());
        edt_morada.setText(perfil.getMorada());
        edt_codigoPostal.setText(perfil.getCodigo_postal());
        edt_data_nascimento.setText(perfil.getDatanascimento());
        edt_nacionalidade.setText(perfil.getNacionalidade());
        edt_telemovel.setText(perfil.getTelemovel());
        edt_username.setText(username);
        edt_email.setText(email);

        if(perfil.getGenero()==1){
            rb_masculino.setChecked(true);
            imgPerfil.setImageResource(R.drawable.male);
        }else{
            rb_feminino.setChecked(true);
            imgPerfil.setImageResource(R.drawable.female);
        }

        //Guardar o perfil para ser acessado dentro do onClickListener do butão atualizar
        auxPerfil=perfil;


        //Guardar na Shared dados atualizados
        SharedPreferences.Editor editor = sharedPrefInfoUser.edit();
        editor.putString(MenuActivity.CARGO,perfil.getCargo());
        editor.putString(MenuActivity.NOMECOMPLETO,perfil.getNome()+" "+perfil.getApelido());
        editor.putInt(MenuActivity.GENERO,perfil.getGenero());

        editor.apply();
    }


    @Override
    public void onRefreshPerfil(Perfil perfil) {

        carregardDadosPerfil(perfil);

    }

    @Override
    //MANDA TOAST CASO O PERFIL FOI ATUALZIADO COM SUCESSO
    public void onUpdatePerfil() {
        SharedPreferences sharedPrefUser = getActivity().getSharedPreferences(MenuActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefUser.edit();
        editor.putString(MenuActivity.USERNAME,edt_username.getText()+"");
        editor.putString(MenuActivity.EMAIL,edt_email.getText()+"");
        editor.apply();

        tvNomeCompleto.setText(edt_nome.getText()+ " " + edt_apelido.getText());
        if(rb_masculino.isChecked()){
            imgPerfil.setImageResource(R.drawable.male);
        }else{
            imgPerfil.setImageResource(R.drawable.female);
        }

        Toast.makeText(getContext(), "Perfil atualizado com sucesso", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        SingletonGestorRestaurante.getInstance(getContext()).getPerfilAPI(ip,token,getContext());
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onResume() {
        super.onResume();
        SingletonGestorRestaurante.getInstance(getContext()).setPerfilListener(this);

    }
}
