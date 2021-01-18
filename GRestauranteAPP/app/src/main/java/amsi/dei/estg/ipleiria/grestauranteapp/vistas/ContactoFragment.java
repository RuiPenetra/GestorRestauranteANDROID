package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import amsi.dei.estg.ipleiria.grestauranteapp.R;

import static androidx.core.content.ContextCompat.checkSelfPermission;


public class ContactoFragment extends Fragment {
    private ImageButton call,mail;
    private String number;


    public ContactoFragment() {
        // Required empty public constructor
    }

//FRAGMENTO DE CONTACTOS DA PARA TELEFONAR E MANDAR MAIL DA PERMISSAO EM RUNTIME
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacto, container, false);
        getActivity().setTitle("Contactos");


        call = view.findViewById(R.id.call);
        number = "915899612";
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPermissionGranted()) {
                    call_action();
                }

            }
        });


        mail = view.findViewById(R.id.mail);
        mail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String[] TO_EMAILS = {"2180687@my.ipleiria.pt"};

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, TO_EMAILS);

                intent.putExtra(Intent.EXTRA_SUBJECT, "EMAIL [Cliente]");

                startActivity(Intent.createChooser(intent, "Choose one application"));

            }
        });
        return view;
    }

    private void call_action() {
        String dial = "tel:" + number;
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(getContext(),android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {

                Log.v("TAG", "Permission is revoked");
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity().getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    call_action();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

}


