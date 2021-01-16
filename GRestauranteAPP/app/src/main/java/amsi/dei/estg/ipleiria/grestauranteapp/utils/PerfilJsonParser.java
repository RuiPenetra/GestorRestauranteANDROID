package amsi.dei.estg.ipleiria.grestauranteapp.utils;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Perfil;

public class PerfilJsonParser {

    public static Perfil parserJsonPerfil(String response) {
        Perfil auxPerfil = null;

        try {
            JSONObject perfil = new JSONObject(response);
            int id = perfil.getInt("id_user");
            Log.i("--->", "" + id);

            String nome = perfil.getString("nome");
            String apelido = perfil.getString("apelido");
            String morada = perfil.getString("morada");
            String dataNascimento = perfil.getString("datanascimento");
            String codigoPostal = perfil.getString("codigopostal");
            String nacionalidade = perfil.getString("nacionalidade");
            String telemovel = perfil.getString("telemovel");
            int genero= perfil.getInt("genero");
            String cargo = perfil.getString("cargo");
            String username = perfil.getString("username");
            String email = perfil.getString("email");

            auxPerfil = new Perfil(id, username, null, email, nome, apelido, morada, nacionalidade, cargo, codigoPostal, genero, telemovel, dataNascimento);
            Log.i("--->", "" + auxPerfil);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return auxPerfil;
    }*/
}
