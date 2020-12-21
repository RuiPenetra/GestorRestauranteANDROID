package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import amsi.dei.estg.ipleiria.grestauranteapp.R;

public class ProdutoFragment extends Fragment {

   private CardView btn_categoria;
   private FragmentTransaction transaction;
   private CardView card_entrada;
   private CardView card_sopa;
   private CardView card_carne;
   private CardView card_peixe;
   private CardView card_sobremesa;
   private CardView card_bebida;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


       View view = inflater.inflate(R.layout.fragment_produtos, container, false);

       card_entrada=view.findViewById(R.id.card_Entrada);
       card_sopa=view.findViewById(R.id.card_sopa);
       card_carne=view.findViewById(R.id.card_Carne);
       card_peixe=view.findViewById(R.id.card_Peixe);
       card_sobremesa=view.findViewById(R.id.card_Sobremesa);
       card_bebida=view.findViewById(R.id.card_Bebida);

        card_entrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= null;
                Bundle b = new Bundle();
                switch (v.getId()) {
                    case R.id.card_Entrada:
                        b.putInt("id_categoria", 1);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                    case R.id.card_sopa:
                        b.putInt("id_categoria", 2);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);;
                        break;
                    case R.id.card_Carne:
                        b.putInt("id_categoria", 3);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                    case R.id.card_Peixe:
                        b.putInt("id_categoria", 4);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                    case R.id.card_Sobremesa:
                        b.putInt("id_categoria", 5);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                    case R.id.card_Bebida:
                        b.putInt("id_categoria", 6);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;

                    default:
                        b.putInt("id_categoria", 0);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                }
                if (fragment != null)
                    transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_produto, fragment).commit();
            }
        });

       card_sopa.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Fragment fragment= null;
               Bundle b = new Bundle();
               switch (v.getId()) {
                   case R.id.card_Entrada:
                       b.putInt("id_categoria", 1);
                       fragment= new ListaProdutosFragment();
                       fragment.setArguments(b);
                       break;
                   case R.id.card_sopa:
                       b.putInt("id_categoria", 2);
                       fragment= new ListaProdutosFragment();
                       fragment.setArguments(b);;
                       break;
                   case R.id.card_Carne:
                       b.putInt("id_categoria", 3);
                       fragment= new ListaProdutosFragment();
                       fragment.setArguments(b);
                       break;
                   case R.id.card_Peixe:
                       b.putInt("id_categoria", 4);
                       fragment= new ListaProdutosFragment();
                       fragment.setArguments(b);
                       break;
                   case R.id.card_Sobremesa:
                       b.putInt("id_categoria", 5);
                       fragment= new ListaProdutosFragment();
                       fragment.setArguments(b);
                       break;
                   case R.id.card_Bebida:
                       b.putInt("id_categoria", 6);
                       fragment= new ListaProdutosFragment();
                       fragment.setArguments(b);
                       break;

                   default:
                       b.putInt("id_categoria", 0);
                       fragment= new ListaProdutosFragment();
                       fragment.setArguments(b);
                       break;
               }
               if (fragment != null)
                   transaction = getChildFragmentManager().beginTransaction();
               transaction.replace(R.id.fragment_container_produto, fragment).commit();
           }
       });
        card_carne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= null;
                Bundle b = new Bundle();
                switch (v.getId()) {
                    case R.id.card_Entrada:
                        b.putInt("id_categoria", 1);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                    case R.id.card_sopa:
                        b.putInt("id_categoria", 2);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);;
                        break;
                    case R.id.card_Carne:
                        b.putInt("id_categoria", 3);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                    case R.id.card_Peixe:
                        b.putInt("id_categoria", 4);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                    case R.id.card_Sobremesa:
                        b.putInt("id_categoria", 5);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                    case R.id.card_Bebida:
                        b.putInt("id_categoria", 6);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;

                    default:
                        b.putInt("id_categoria", 0);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                }
                if (fragment != null)
                    transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_produto, fragment).commit();
            }
        });
        card_peixe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= null;
                Bundle b = new Bundle();
                switch (v.getId()) {
                    case R.id.card_Entrada:
                        b.putInt("id_categoria", 1);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                    case R.id.card_sopa:
                        b.putInt("id_categoria", 2);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);;
                        break;
                    case R.id.card_Carne:
                        b.putInt("id_categoria", 3);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                    case R.id.card_Peixe:
                        b.putInt("id_categoria", 4);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                    case R.id.card_Sobremesa:
                        b.putInt("id_categoria", 5);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                    case R.id.card_Bebida:
                        b.putInt("id_categoria", 6);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;

                    default:
                        b.putInt("id_categoria", 0);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                }
                if (fragment != null)
                    transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_produto, fragment).commit();
            }
        });
        card_sobremesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= null;
                Bundle b = new Bundle();
                switch (v.getId()) {
                    case R.id.card_Entrada:
                        b.putInt("id_categoria", 1);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                    case R.id.card_sopa:
                        b.putInt("id_categoria", 2);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);;
                        break;
                    case R.id.card_Carne:
                        b.putInt("id_categoria", 3);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                    case R.id.card_Peixe:
                        b.putInt("id_categoria", 4);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                    case R.id.card_Sobremesa:
                        b.putInt("id_categoria", 5);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                    case R.id.card_Bebida:
                        b.putInt("id_categoria", 6);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;

                    default:
                        b.putInt("id_categoria", 0);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                }
                if (fragment != null)
                    transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_produto, fragment).commit();
            }
        });
        card_bebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= null;
                Bundle b = new Bundle();
                switch (v.getId()) {
                    case R.id.card_Entrada:
                        b.putInt("id_categoria", 1);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                    case R.id.card_sopa:
                        b.putInt("id_categoria", 2);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);;
                        break;
                    case R.id.card_Carne:
                        b.putInt("id_categoria", 3);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                    case R.id.card_Peixe:
                        b.putInt("id_categoria", 4);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                    case R.id.card_Sobremesa:
                        b.putInt("id_categoria", 5);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                    case R.id.card_Bebida:
                        b.putInt("id_categoria", 6);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;

                    default:
                        b.putInt("id_categoria", 0);
                        fragment= new ListaProdutosFragment();
                        fragment.setArguments(b);
                        break;
                }
                if (fragment != null)
                    transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container_produto, fragment).commit();
            }
        });
        return view;
    }

}
