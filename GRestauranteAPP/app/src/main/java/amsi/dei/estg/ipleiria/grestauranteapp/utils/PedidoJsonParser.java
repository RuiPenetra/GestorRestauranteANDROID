package amsi.dei.estg.ipleiria.grestauranteapp.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Pedido;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Perfil;

public class PedidoJsonParser {

    public static ArrayList<Pedido> parserJsonPedidos(JSONArray response){
        ArrayList<Pedido> pedidos = new ArrayList<>();

        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject pedido = (JSONObject) response.get(i);
                int id = pedido.getInt("id");
                int tipo = pedido.getInt("tipo");
                int estado = pedido.getInt("estado");
                int id_mesa = pedido.getInt("id_mesa");
                int id_user = pedido.getInt("id_perfil");
                String nome_pedido = pedido.getString("nome_pedido");
                String data=pedido.getString("data");
                String nota=pedido.getString("nota");

                Pedido auxPedido= new Pedido(id,id_user,id_mesa,nome_pedido,nota,estado,tipo,data);
                pedidos.add(auxPedido);
                Log.i("--->", "" + auxPedido);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    public static Perfil parserJsonPerfilUpdate(String response){
        Perfil auxPerfil = null;

        try {
                JSONObject perfil = new JSONObject(response);
                int id = perfil.getInt("id_user");
                String nome = perfil.getString("nome");
                String apelido = perfil.getString("apelido");
                String morada = perfil.getString("morada");
                String dataNascimento = perfil.getString("datanascimento");
                String codigoPostal = perfil.getString("codigopostal");
                String nacionalidade = perfil.getString("nacionalidade");
                String telemovel = perfil.getString("telemovel");
                String genero;
                if (perfil.getInt("genero") == 0) {
                    genero = "Feminino";
                } else {
                    genero = "Masculino";
                }
                String cargo = perfil.getString("cargo");
                String username = perfil.getString("username");
                String email = perfil.getString("email");

                auxPerfil = new Perfil(id,username,null,email,nome,apelido,morada,nacionalidade,cargo,codigoPostal,genero,telemovel,dataNascimento);
                Log.i("--->", "" + auxPerfil);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return auxPerfil;
    }
}
