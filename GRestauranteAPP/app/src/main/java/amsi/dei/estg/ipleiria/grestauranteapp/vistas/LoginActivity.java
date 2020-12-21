package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import amsi.dei.estg.ipleiria.grestauranteapp.R;

public class LoginActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.fundo)));
        setTitle("Login");

        getSupportActionBar().setElevation(0);




    }

    public void onClickLogin(View view) {

        Intent intent = new Intent(this, RegistarActivity.class);
        startActivity(intent);
    }

}