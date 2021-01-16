package amsi.dei.estg.ipleiria.grestauranteapp.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class AutenticacaoJsonParser {

    public static String parserJsonLogin(String response) {
        String token = null;

        try {
            JSONObject login = new JSONObject(response);
            if (login.getBoolean("success"))
                token = login.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;

    }
}
