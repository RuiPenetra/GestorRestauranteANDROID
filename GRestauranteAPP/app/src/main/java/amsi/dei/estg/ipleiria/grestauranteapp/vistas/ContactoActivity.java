package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import amsi.dei.estg.ipleiria.grestauranteapp.R;

public class ContactoActivity extends AppCompatActivity {
private ImageButton call;
private String number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        call=findViewById(R.id.call);
        number="915899612";
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dial= "tel:"+number;
                startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
            }
        });
    }
}