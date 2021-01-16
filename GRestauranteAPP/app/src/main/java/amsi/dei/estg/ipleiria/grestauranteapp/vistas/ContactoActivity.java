package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import amsi.dei.estg.ipleiria.grestauranteapp.R;

public class ContactoActivity extends AppCompatActivity {
private ImageButton call,mail;
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

            mail=findViewById(R.id.mail);
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
}