package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PerfilListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Perfil;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;

public class BemVindoFragment extends Fragment {

    private String nomeCompleto;
    private int genero;
    private TextView tvNomeCompleto;
    private ImageView imgvUser;

    @Nullable
    @Override
    //FRAGMENTO INICIAL DEPOIS DO LOGIN SER FEITO MOSTRA O NOME DE UTILIZADOR
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_bemvindo,container,false);


        tvNomeCompleto=view.findViewById(R.id.tv_nom_completo);
        imgvUser=view.findViewById(R.id.imgv_perfil);

        SharedPreferences sharedPrefInfoUser = getActivity().getSharedPreferences(MenuActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        nomeCompleto= sharedPrefInfoUser.getString(MenuActivity.NOMECOMPLETO,null);
        genero= sharedPrefInfoUser.getInt(MenuActivity.GENERO,-1);

        tvNomeCompleto.setText(nomeCompleto);

        if(genero==0){
            imgvUser.setImageResource(R.drawable.female);

        }else{
            imgvUser.setImageResource(R.drawable.male);
        }

        return view;
    }

}
