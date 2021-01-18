package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PerfilListener;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Perfil;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.SingletonGestorRestaurante;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String PREF_INFO_USER ="PREF_INFO_USER";
    public static final String IP="IP";
    public static final String ID="ID";
    public static final String USERNAME="USERNAME";
    public static final String PASSWORD ="PASSWORD";
    public static final String TOKEN="TOKEN";
    public static final String RELEMBRAR = "RELEMBRAR";
    public static final String NOMECOMPLETO = "NOMECOMPLETO";
    public static final String GENERO = "GENERO";
    public static final String CARGO ="CARGO";
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private String nome_completo;
    private int genero;
    private FragmentManager fragmentManager;
    private TextView tvNomeCompleto;
    private ImageView imgPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

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
        carregarCabecalho();
    }

    private void carregarCabecalho() {

        SharedPreferences sharedPrefInfoUser=getSharedPreferences(PREF_INFO_USER, Context.MODE_PRIVATE);
        nome_completo=sharedPrefInfoUser.getString(NOMECOMPLETO,null);
        genero=sharedPrefInfoUser.getInt(GENERO,-1);

        View hView=navigationView.getHeaderView(0);

        tvNomeCompleto=hView.findViewById(R.id.tvNomeCompleto);
        imgPerfil=hView.findViewById(R.id.imgUser);

        tvNomeCompleto.setText(nome_completo);

        if(genero==0){
            imgPerfil.setImageResource(R.drawable.female);

        }else{
            imgPerfil.setImageResource(R.drawable.male);
        }
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
            case R.id.nav_contactos:
                fragment= new ContactoFragment();
                setTitle(item.getTitle());
                break;
        }
        if (fragment != null)
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_logout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.itemLogout:
                dialogLogout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void dialogLogout() {
        AlertDialog.Builder builder;
        builder= new AlertDialog.Builder(this);
        builder.setTitle("Sair")
                .setMessage("Pretende mesmo terminar a sessão?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        limparShared();

                        Intent intent= new Intent(MenuActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_delete)
                .show();
    }

    private void limparShared() {

        SharedPreferences sharedPrefUser = getSharedPreferences(MenuActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefUser.edit();
        editor.remove(RELEMBRAR);
        editor.remove(ID);
        editor.remove(USERNAME);
        editor.remove(PASSWORD);
        editor.remove(TOKEN);
        editor.remove(CARGO);
        editor.remove(NOMECOMPLETO);
        editor.remove(GENERO);
        editor.apply();
    }


}