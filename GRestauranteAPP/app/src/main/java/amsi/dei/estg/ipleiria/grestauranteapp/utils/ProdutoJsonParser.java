package amsi.dei.estg.ipleiria.grestauranteapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Produto;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProdutoJsonParser {

    public static ArrayList<Produto> parserJsonProdutos(JSONArray response) {

         ArrayList<Produto> produtos = new ArrayList<>();

        try{
            for (int i = 0;i < response.length();i++){
                JSONObject produto = (JSONObject) response.get(i);
                int id = produto.getInt("id");
                String nome = produto.getString("nome");
                String ingredientes = produto.getString("ingredientes");
                int categoria = produto.getInt("id_categoria");
                String preco = produto.getString("preco");

                Produto auxProduto = new Produto(id,nome,ingredientes,preco,categoria);
                produtos.add(auxProduto);
            }

        }catch(JSONException e){
            e.printStackTrace();
        }
        return produtos;
    }

    public static Produto parserJsonProduto(String response) {
        Produto auxProduto = null;

        try {
            JSONObject produto = new JSONObject(response);
            int id = produto.getInt("id");
            String nome = produto.getString("nome");
            String ingredientes = produto.getString("ingredientes");
            int categoria = produto.getInt("id_categoria");
            String preco = produto.getString("preco");

            auxProduto = new Produto(id,nome,ingredientes,preco,categoria);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return auxProduto;
    }

    public static String parserJsonLogin(String response) {
        String token = null;

        try {
            JSONObject login = new JSONObject(response);
            if (login.getBoolean("success"))
                token = login.getString("auth_key");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;

    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni=cm.getActiveNetworkInfo();

        return ni !=null &&ni.isConnected();
    }
}
