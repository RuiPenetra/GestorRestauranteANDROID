package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import amsi.dei.estg.ipleiria.grestauranteapp.R;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String PREF_INFO_USER ="PREF_INFO_USER";
    public static final String IP="IP";
    public static final String EMAIL = "EMAIL";
    public static final String USERNAME="USERNAME";
    public static final String PASSWORD ="PASSWORD";
    public static final String TOKEN="TOKEN";
    public static final String RELEMBRAR = "RELEMBRAR";
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private String username="";
    private String email="hjgkygiyg";
    private FragmentManager fragmentManager;
    private TextView tvNome,tvApelido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_funcionario);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.ndOpen, R.string.ndClose);
        toggle.syncState();

        drawer.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();

        carregarFragmentoInicial();
        //carregarCabecalho();
    }

    private void carregarCabecalho() {
       // email=getIntent().getStringExtra(EMAIL);

        SharedPreferences sharedPrefInfoUser=getSharedPreferences(PREF_INFO_USER, Context.MODE_PRIVATE);
        username=sharedPrefInfoUser.getString(USERNAME,"Sem username");
        if(email==null)
            //GUARDAR SHARED
            email=sharedPrefInfoUser.getString(EMAIL,"Sem email");
        else {
            //LER DA SHARED
            SharedPreferences.Editor editor = sharedPrefInfoUser.edit();
            editor.putString(EMAIL,email);
            editor.apply();
        }

        View hView=navigationView.getHeaderView(0);

        tvNome=hView.findViewById(R.id.tvNome);
        tvApelido=hView.findViewById(R.id.tvApelido);
        //tvEmail.setText(email);
        setTitle(R.string.actBemVindo);
    }


    private void carregarFragmentoInicial(){
        navigationView.setCheckedItem(R.id.nav_bemvindo);
        Fragment fragment=new BemVindoFragment();
        fragmentManager.beginTransaction().replace(R.id.contentFragment,fragment).commit();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment= null;
        switch (item .getItemId()) {
            case R.id.nav_bemvindo:
                fragment = new BemVindoFragment();
                setTitle(item.getTitle());
                break;
            case R.id.nav_perfil:
                fragment = new PerfilFragment();
                setTitle(item.getTitle());
                break;
            case R.id.nav_produtos:
                fragment = new ProdutoFragment();
                setTitle(item.getTitle());
                break;
            case R.id.nav_pedidos:
                fragment = new PedidoFragment();
                setTitle(item.getTitle());
                break;
        }
        if (fragment != null)
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}