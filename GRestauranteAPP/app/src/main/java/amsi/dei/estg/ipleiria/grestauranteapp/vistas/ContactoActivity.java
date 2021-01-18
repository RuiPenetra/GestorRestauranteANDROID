package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import amsi.dei.estg.ipleiria.grestauranteapp.R;

import static android.Manifest.permission.CALL_PHONE;

public class ContactoActivity extends AppCompatActivity {
private ImageButton call,mail;
private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        setTitle("Contactos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        call = findViewById(R.id.imgbCall);
        mail=findViewById(R.id.imgbMail);


        number = "915899612";
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPermissionGranted()) {
                    String dial = "tel:" + number;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                }

            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] TO_EMAILS= {"2180687@my.ipleiria.pt"};

                Intent intent= new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL,TO_EMAILS);

                intent.putExtra(Intent.EXTRA_SUBJECT,"EMAIL [Cliente]");

                startActivity(Intent.createChooser(intent,"Choose one application"));


                }
            });
    }

    private void call_action() {
        String dial = "tel:" + number;
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
    }

    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    call_action();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }

    }

}
