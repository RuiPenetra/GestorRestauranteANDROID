package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.adaptadores.ListaProdutoAdaptador;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.ProdutosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Produto;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;

public class ListaProdutosActivity extends AppCompatActivity  {

    private static final int EDITAR = 1 ;
    private static final int CRIAR =2 ;
    private static final int APAGAR =3 ;
    private static int ID_CATEGORIA ;
    private CardView cvEntrada;
    private CardView cvSopa;
    private CardView cvCarne;
    private CardView cvPeixe;
    private CardView cvBedida;
    private CardView cvSobremesa;
    private ListView lvlProdutos;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);

        cvEntrada=findViewById(R.id.cvEntrada);
        cvSopa=findViewById(R.id.cvSopa);
        cvCarne=findViewById(R.id.cvCarne);
        cvPeixe=findViewById(R.id.cvPeixe);
        cvBedida=findViewById(R.id.cvBebida);
        cvSobremesa=findViewById(R.id.cvSobremesa);

        fragmentManager = getSupportFragmentManager();

        Fragment fragment= new ListaProdutosFragment();
        Bundle b = new Bundle();
        b.putInt("id_categoria",0);
        fragment.setArguments(b);

        fragmentManager.beginTransaction().replace(R.id.fragment_container_produto, fragment).commit();

        cvEntrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= null;
                Bundle b = new Bundle();
                b.putInt("id_categoria", 1);
                fragment= new ListaProdutosFragment();
                fragment.setArguments(b);
                if (fragment != null)
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_produto, fragment).commit();

            }
        });
        cvSopa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= null;
                Bundle b = new Bundle();
                b.putInt("id_categoria", 2);
                fragment= new ListaProdutosFragment();
                fragment.setArguments(b);
                if (fragment != null)
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_produto, fragment).commit();

            }
        });

        cvCarne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= null;
                Bundle b = new Bundle();
                b.putInt("id_categoria", 3);
                fragment= new ListaProdutosFragment();
                fragment.setArguments(b);
                if (fragment != null)
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_produto, fragment).commit();

            }
        });

        cvPeixe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= null;
                Bundle b = new Bundle();
                b.putInt("id_categoria",4);
                fragment= new ListaProdutosFragment();
                fragment.setArguments(b);
                if (fragment != null)
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_produto, fragment).commit();

            }
        });

        cvSobremesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= null;
                Bundle b = new Bundle();
                b.putInt("id_categoria",5);
                fragment= new ListaProdutosFragment();
                fragment.setArguments(b);
                if (fragment != null)
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_produto, fragment).commit();

            }
        });

        cvBedida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= null;
                Bundle b = new Bundle();
                b.putInt("id_categoria",6);
                fragment= new ListaProdutosFragment();
                fragment.setArguments(b);
                if (fragment != null)
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_produto, fragment).commit();

            }
        });
    }

}