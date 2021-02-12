package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Carrinho;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;

public class MenuClienteActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static final String PREF_INFO_USER ="PREF_INFO_USER";
    public static final String IP="IP";
    public static final String ID="ID";
    public static final String USERNAME="USERNAME";
    public static final String PASSWORD ="PASSWORD";
    public static final String TOKEN="TOKEN";
    public static final String EMAIL ="EMAIL";
    public static final String RELEMBRAR = "RELEMBRAR";
    public static final String NOMECOMPLETO = "NOMECOMPLETO";
    public static final String GENERO = "GENERO";
    public static final String CARGO ="CARGO";
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);

        setTitle("Bem-Vindo");
        bottomNavigationView =findViewById(R.id.bottom_navigation_view);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();

        carregarFragmentoInicial();

        FloatingActionButton fab_carrinho= findViewById(R.id.fabCarrinho);

        fab_carrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CarrinhoActivity.class);
                startActivity(intent);
            }
        });


    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment= null;
        switch (item .getItemId()) {
            case R.id.navB_produtos:
                fragment = new ProdutoFragment();
                setTitle(item.getTitle());
                break;
            case R.id.navB_pedidos:
                fragment = new PedidoFragment();
                setTitle(item.getTitle());
                break;
            case R.id.navB_contactos:
                fragment = new ContactoFragment();
                setTitle(item.getTitle());
                break;
            case R.id.navB_perfil:
                fragment = new PerfilFragment();
                setTitle(item.getTitle());
                break;
        }
        if (fragment != null)
            fragmentManager.beginTransaction().replace(R.id.container_menu, fragment).commit();

        return true;
    }

    private void carregarFragmentoInicial(){
        bottomNavigationView.setSelectedItemId(R.id.nav_bemvindo);
        Fragment fragment=new BemVindoFragment();
        fragmentManager.beginTransaction().replace(R.id.container_menu,fragment).commit();
    }

    @Override
    //BOTAO DE LOGOUT
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

    //CONFIRMAÇÃO DE LOGOUT
    private void dialogLogout() {
        AlertDialog.Builder builder;
        builder= new AlertDialog.Builder(this);
        builder.setTitle("Sair")
                .setMessage("Pretende mesmo terminar a sessão?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        limparShared();

                        Intent intent= new Intent(MenuClienteActivity.this, LoginActivity.class);
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
    //LIMPA A SHARED PREFERENCE
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