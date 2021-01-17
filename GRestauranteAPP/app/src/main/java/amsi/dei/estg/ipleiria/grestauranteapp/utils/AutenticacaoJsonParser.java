package amsi.dei.estg.ipleiria.grestauranteapp.utils;

import org.json.JSONException;
import org.json.JSONObject;

import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Perfil;

public class AutenticacaoJsonParser {

    public static Perfil parserJsonLogin(String response) {
        Perfil auxPerfil = null;

        try {
            JSONObject perfil = new JSONObject(response);
            if (perfil.getBoolean("success")) {

                int id = perfil.getInt("id_user");
                String nome = perfil.getString("nome");
                String apelido = perfil.getString("apelido");
                int genero = perfil.getInt("genero");
                String cargo = perfil.getString("cargo");
                String token = perfil.getString("token");

                auxPerfil = new Perfil(id, null, null, null, nome, apelido, null, null, cargo, null, genero, null, null,token);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return auxPerfil;

    }
}
