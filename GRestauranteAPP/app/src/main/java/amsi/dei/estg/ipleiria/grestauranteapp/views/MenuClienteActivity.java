package amsi.dei.estg.ipleiria.grestauranteapp.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import amsi.dei.estg.ipleiria.grestauranteapp.R;

public class MenuClienteActivity extends AppCompatActivity {

    MeowBottomNavigation bottomNavigation;
    private final static int ID_PERFIL=1;
    private final static int ID_PRODUTO=2;
    private final static int ID_PEDIDOS=3;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.fundo)));

        getSupportActionBar().setElevation(0);

        btn_login=findViewById(R.id.btn_login);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.perfil));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.produto));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.pedidos));

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new BemVindoFragment()).commit();
        setTitle("Bem vindo");

        
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {


                switch (item.getId()){

                    case ID_PERFIL:
                        Toast.makeText(MenuClienteActivity.this, "Perfil", Toast.LENGTH_SHORT).show();
                        break;
                    case ID_PRODUTO:
                        Toast.makeText(MenuClienteActivity.this, "Produtos", Toast.LENGTH_SHORT).show();
                        break;
                    case ID_PEDIDOS:
                        Toast.makeText(MenuClienteActivity.this, "Take-away", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                Fragment fragmento_selecionado=null;

                switch (item.getId()){

                    case ID_PERFIL:
                        fragmento_selecionado=new PerfilFragment();
                        setTitle("Perfil");

                        break;
                    case ID_PRODUTO:
                        fragmento_selecionado=new ProdutoFragment();
                        setTitle("Produtos");
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionBar)));
                        break;
                    case ID_PEDIDOS:
                        fragmento_selecionado=new PedidoFragment();
                        setTitle("Take-away");
                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.actionBar)));

                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragmento_selecionado).commit();
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

                Fragment fragmento_selecionado=null;

                switch (item.getId()){

                    case ID_PERFIL:

                        fragmento_selecionado=new PerfilFragment();
                        break;
                    case ID_PRODUTO:
                        fragmento_selecionado=new ProdutoFragment();
                        break;
                    case ID_PEDIDOS:
                        fragmento_selecionado=new PedidoFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragmento_selecionado).commit();
            }
        });
    }
}