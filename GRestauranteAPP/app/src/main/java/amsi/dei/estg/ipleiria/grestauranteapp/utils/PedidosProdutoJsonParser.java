package amsi.dei.estg.ipleiria.grestauranteapp.utils;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Pedido;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.PedidoProduto;

public class PedidosProdutoJsonParser {

    public static ArrayList<PedidoProduto> parserJsonPedidosProduto(JSONArray response){
        ArrayList<PedidoProduto> pedidosProdutos = new ArrayList<>();

        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject pedidosProduto = (JSONObject) response.get(i);

                int id = pedidosProduto.getInt("id");

                int id_pedido = pedidosProduto.getInt("id_pedido");

                int id_produto = pedidosProduto.getInt("id_produto");

                int estado = pedidosProduto.getInt("estado");

                float preco = Float.parseFloat(pedidosProduto.getString("preco"));

                int quantidade = pedidosProduto.getInt("quant_Pedida");

                PedidoProduto auxPedidosProduto= new PedidoProduto(id,id_produto,id_pedido,quantidade,preco,estado);

                pedidosProdutos.add(auxPedidosProduto);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pedidosProdutos;
    }

    public static PedidoProduto parserJsonPedidoProduto(String response) {
        PedidoProduto auxPedidoProduto= null;

        try {
            JSONObject pedido = new JSONObject(response);
            int id = pedido.getInt("id");
            int id_pedido = pedido.getInt("id_pedido");
            int id_produto = pedido.getInt("id_produto");
            int estado = pedido.getInt("estado");
            float preco = Float.parseFloat(pedido.getString("preco"));
            int quantidade = pedido.getInt("quant_Pedida");

            auxPedidoProduto = new PedidoProduto(id,id_produto,id_pedido,quantidade,preco,estado);
            Log.i("--->", "" + auxPedidoProduto);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return auxPedidoProduto;
    }


    public static boolean parserJsonAddRestaurante(String response) {
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

