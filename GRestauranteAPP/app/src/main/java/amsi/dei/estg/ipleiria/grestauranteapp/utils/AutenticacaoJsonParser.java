package amsi.dei.estg.ipleiria.grestauranteapp.utils;

import org.json.JSONException;
import org.json.JSONObject;

import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Perfil;

public class AutenticacaoJsonParser {

    public static Perfil parserJsonLogin(String response) {
        Perfil auxPerfil=null;
        try {
            JSONObject perfil = new JSONObject(response);
                int id = perfil.getInt("id");
                String username = perfil.getString("username");
                String email = perfil.getString("email");
                String token = perfil.getString("auth_key");
                 auxPerfil = new Perfil(id, username, null, email, null, null, null, null, null, null, 0, null, null, token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return auxPerfil;

    }

    public static boolean parserJsonRegistar(String response) {
        boolean reposta=false;
        try {
            JSONObject rest = new JSONObject(response);
            reposta= rest.getBoolean("SaveError");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reposta;

    }
}
