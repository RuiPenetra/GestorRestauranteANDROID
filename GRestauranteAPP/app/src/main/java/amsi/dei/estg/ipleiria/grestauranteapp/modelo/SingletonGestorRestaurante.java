package amsi.dei.estg.ipleiria.grestauranteapp.modelo;


import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import amsi.dei.estg.ipleiria.grestauranteapp.listeners.CarrinhoListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.AutenticacaoListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PedidosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PedidosProdutoListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PerfilListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.ProdutosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.AutenticacaoJsonParser;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.PedidoJsonParser;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.PedidosProdutoJsonParser;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.PerfilJsonParser;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.ProdutoJsonParser;

public class SingletonGestorRestaurante {
    private static SingletonGestorRestaurante instance = null;
    private ArrayList<Produto> produtos;
    private ArrayList<Carrinho> itemsCarrinho;
    private ArrayList<Pedido> pedidos;
    private ArrayList<PedidoProduto> pedidoProdutos;
    private Perfil perfil;
    private GRestauranteBDHelper produtosBD;
    private GRestauranteBDHelper itemsCarrinhoBD;
    private static RequestQueue volleyQueue = null;
    private static final String BaseUrl = "http://";
    private static final String mUrlAPIUser = "/GestorRestauranteAPI/API/web/v1/user/";
    private static final String mUrlAPISemAutenticacao = "/GestorRestauranteAPI/API/web/v1/noauth";
    private static final String mUrlAPIPedidos = "/GestorRestauranteAPI/API/web/v1/pedido";
    private static final String mUrlAPIPedidosProduto = "/GestorRestauranteAPI/API/web/v1/pedidoproduto";
    private static final String mUrlAPIPerfil = "/GestorRestauranteAPI/API/web/v1/perfil";
    private ProdutosListener produtosListener;
    private AutenticacaoListener loginListener;
    private PerfilListener perfilListener;
    private PedidosListener pedidosListener;
    private AutenticacaoListener autenticacaoListener;
    private CarrinhoListener carrinhoListener;
    private PedidosProdutoListener pedidosProdutoListener;

    public static synchronized SingletonGestorRestaurante getInstance(Context context) {
        if (instance == null) {
            instance = new SingletonGestorRestaurante(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private SingletonGestorRestaurante(Context context) {
        produtos = new ArrayList<>();
        pedidos = new ArrayList<>();
        itemsCarrinho = new ArrayList<>();
        pedidoProdutos = new ArrayList<>();
        produtosBD = new GRestauranteBDHelper(context);
        itemsCarrinhoBD = new GRestauranteBDHelper(context);
    }

    public Produto getProduto(int id){
        produtos=getProdutosBD();
        for (Produto p : produtos){
            if(p.getId() == id)
                return p;
        }
        return null;
    }

    public int getCountItemsCarrinho(){
        return itemsCarrinho.size();
    }
    // # PEDIDOS

    public Pedido getPedido(int id) {
        for (Pedido p : pedidos)
            if (p.getId() == id)
                return p;
        return null;
    }

    public Carrinho getItemCarrinho(int id) {
        for (Carrinho item : itemsCarrinho)
            if (item.getId() == id)
                return item;
        return null;
    }

    public PedidoProduto getPedidoProduto(int id) {
        for (PedidoProduto pedProd: pedidoProdutos)
            if (pedProd.getId() == id)
                return pedProd;
        return null;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public ArrayList<Carrinho> getItemsCarrinho() {
        return itemsCarrinho;
    }

    /************** Listeners ******************************/

    public void setPedidosListener(PedidosListener pedidosListener) {
        this.pedidosListener = pedidosListener;
    }

    public void setProdutosListener(ProdutosListener produtosListener) {
        this.produtosListener = produtosListener;
    }

    public void setLoginListener(AutenticacaoListener loginListener) { this.loginListener = loginListener; }

    public void setPerfilListener(PerfilListener perfilListener){
        this.perfilListener = perfilListener;
    }

    public void setPedidoProdutosListener(PedidosProdutoListener pedidoProdutosListener){
        this.pedidosProdutoListener = pedidoProdutosListener;
    }

    public void setRegistoListener(AutenticacaoListener autenticacaoListener) {
        this.autenticacaoListener = autenticacaoListener;

    }

    public void setCarrinhoListener(CarrinhoListener carrinhoListener) {
        this.carrinhoListener = carrinhoListener;

    }

    /************** Métodos de acesso à BD ******************************/
    // # PRODUTOS
    public ArrayList<Produto> getProdutosBD() {
        produtos = produtosBD.getAllProdutosBD();
        return produtos;
    }

    public void adicionarProdutoBD(Produto produto) {
        Produto p = produtosBD.adicionarProdutoBD(produto);
        if (p != null)
            produtos.add(p);
    }

    public void adicionarProdutosBD(ArrayList<Produto> produtos) {
        produtosBD.removerAllProdutosBD();
        for (Produto p : produtos)
            adicionarProdutoBD(p);
    }

    public void  getItemsCarrinhoBD(int id_utilizador) {
        itemsCarrinho = itemsCarrinhoBD.getItemsCarrinhoBD(id_utilizador);

        if(carrinhoListener!=null){
            carrinhoListener.onRefreshListaItemsCarrinho(itemsCarrinho);
        }
    }

    public void adicionarItemCarrinhoBD(Carrinho itemCarrinho) {

        Carrinho valExistItem=null;
        itemsCarrinho = itemsCarrinhoBD.getItemsCarrinhoBD(itemCarrinho.getId_utilizador());

        for (Carrinho item : itemsCarrinho) {

            if(item.getId_produto()==itemCarrinho.getId_produto()){
                valExistItem=item;
            }

        }

        if(valExistItem!=null){
            valExistItem.setQuantidade(valExistItem.getQuantidade() + itemCarrinho.getQuantidade());

            float precoExist = Float.parseFloat(valExistItem.getPreco());
            float precoNova = Float.parseFloat(itemCarrinho.getPreco());

            valExistItem.setPreco(String.format("%.2f", precoExist + precoNova).replace(',', '.'));

            editarItemCarrinhoBD(valExistItem);
        }else{
            itemsCarrinhoBD.adicionarItemCarrinhoBD(itemCarrinho);
            if(carrinhoListener!=null){
                carrinhoListener.onDetalhes(true);
            }
        }
    }

    public void adicionarItemsCarrinhoBD(ArrayList<Carrinho> itemsCarrinho,int id_utilizador) {
        itemsCarrinhoBD.removerAllItemsCarrinhoBD(id_utilizador);
        for (Carrinho item : itemsCarrinho)
            adicionarItemCarrinhoBD(item);
    }

    public void removerItemCarrinhoBD(int id) {
        Carrinho item = getItemCarrinho(id);

        if (item != null)
            if (itemsCarrinhoBD.removerItemCarrinhoBD(item))
                itemsCarrinho.remove(item);

        if(carrinhoListener!=null){
            carrinhoListener.onDetalhes(false);
        }
    }

    public void removerItemsCarrinhoBD(int id_utilizador) {
        itemsCarrinhoBD.removerAllItemsCarrinhoBD(id_utilizador);

        if(carrinhoListener!=null){
            carrinhoListener.onAddItems();
        }
    }


    public void editarItemCarrinhoBD(Carrinho itemCarrinho) {
        Carrinho item = getItemCarrinho(itemCarrinho.getId());

        if (item != null) {
            if (itemsCarrinhoBD.editarItemCarrinhoBD(item)) {
                item.setQuantidade(itemCarrinho.getQuantidade());
                item.setPreco(itemCarrinho.getPreco());
            }
        }

        if (carrinhoListener!=null){
            carrinhoListener.onDetalhes(true);
        }

    }


    /************** Métodos de acesso à API ******************************/

    public void loginAPI(final String ip, final String username,final String password,final Context context) {

        if (!Generic.isConnectionInternet(context)) {

            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();

        } else {
            StringRequest req = new StringRequest(Request.Method.GET, BaseUrl + ip + mUrlAPIUser, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    perfil = AutenticacaoJsonParser.parserJsonLogin(response);

                    if (loginListener != null)
                        loginListener.onValidarLogin(perfil);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Não existe nenhum utilizador com estes dados de acesso", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    String credentials = username + ":" + password;

                    byte[] login=null;

                    try {
                        login=credentials.getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    HashMap<String, String> headers = new HashMap<>();
                    String base64EncodedCredentials = Base64.encodeToString(login, Base64.NO_WRAP);
                    headers.put("Authorization", "Basic " + base64EncodedCredentials);
                    return headers;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void getProdutosCategoriaAPI(final String ip, final int id_categoria,final Context context) {
        if (!Generic.isConnectionInternet(context)) {
            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();

            if (produtosListener != null) {

                if(id_categoria==0){
                    produtosListener.onRefreshListaPordutos(produtosBD.getAllProdutosBD());
                }else {
                    produtosListener.onRefreshListaPordutos(produtosBD.getProdutosCategoriaBD(id_categoria));
                }

            }
        } else {

            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, BaseUrl + ip + (id_categoria==0 ? mUrlAPISemAutenticacao + "/produto" : mUrlAPISemAutenticacao + "/produtocategoria/" + id_categoria), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    produtos = ProdutoJsonParser.parserJsonProdutos(response);

                    if(id_categoria==0){
                        adicionarProdutosBD(produtos);
                    }

                    produtosListener.onRefreshListaPordutos(produtos);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Não foi possivel aceder aos produtos porfavor tente mais tarde", Toast.LENGTH_SHORT).show();
                }
            });

            volleyQueue.add(req);

        }
    }

    public void getPerfilAPI(final String ip,final String token,final Context context) {
        if (!Generic.isConnectionInternet(context)) {

            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();

        } else {
            StringRequest req = new StringRequest (Request.Method.GET, BaseUrl + ip + mUrlAPIPerfil + "?access-token=" + token,new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    perfil = PerfilJsonParser.parserJsonPerfil(response);
                    if(perfilListener!=null ){
                        perfilListener.onRefreshPerfil(perfil);
                    }else{
                        Toast.makeText(context, "Erro ao carregar o Perfil", Toast.LENGTH_SHORT).show();;
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            volleyQueue.add(req);
        }
    }

    public void updatePerfilAPI(final String ip, final String token,final Perfil perfil, final Context context) {

        if (!Generic.isConnectionInternet(context)) {

            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();

        } else {
            StringRequest req = new StringRequest(Request.Method.PUT, BaseUrl + ip + mUrlAPIPerfil + "/" + perfil.getId() + "?access-token=" + token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    boolean save= PerfilJsonParser.parserJsonAtualizar(response);

                    if(save==true){
                        if (perfilListener != null) {
                            perfilListener.onUpdatePerfil();
                        }
                    }else{
                        Toast.makeText(context, "Impossivel atualizar o perfil", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("nome", perfil.getNome());
                    params.put("apelido", perfil.getApelido());
                    params.put("morada", perfil.getMorada());
                    params.put("datanascimento", perfil.getDatanascimento());
                    params.put("codigopostal", perfil.getCodigo_postal());
                    params.put("nacionalidade", perfil.getNacionalidade());
                    params.put("telemovel", perfil.getTelemovel());
                    params.put("genero", perfil.getGenero() + "");
                    params.put("username", perfil.getUsername());
                    params.put("email", perfil.getEmail());

                    if (perfil.getNovaPassword() != "")
                        params.put("nova_password", perfil.getNovaPassword());

                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void adicionarUserAPI(final String ip , final Perfil perfil, final Context context) {

        if (!Generic.isConnectionInternet(context)) {
            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest req = new StringRequest(Request.Method.POST, BaseUrl + ip + mUrlAPISemAutenticacao +"/registaruser", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    boolean save= AutenticacaoJsonParser.parserJsonRegistar(response);

                    if(save==true){
                        if (autenticacaoListener != null)
                            autenticacaoListener.onValidarRegisto();
                    }else{
                        Toast.makeText(context, "Impossivel registar utilizador", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", perfil.getUsername());
                    params.put("email", perfil.getEmail());
                    params.put("password", perfil.getNovaPassword());
                    params.put("nome", perfil.getNome());
                    params.put("apelido", perfil.getApelido());
                    params.put("morada", perfil.getMorada());
                    params.put("datanascimento", perfil.getDatanascimento());
                    params.put("codigopostal", perfil.getCodigo_postal());
                    params.put("nacionalidade", perfil.getNacionalidade());
                    params.put("telemovel", perfil.getTelemovel());
                    params.put("genero", perfil.getGenero() + "");

                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void getPedidosAPI(final String ip,final String token,final Context context) {
        if (!Generic.isConnectionInternet(context)) {
            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET,BaseUrl + ip + mUrlAPIPedidos +"?access-token=" + token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    pedidos = PedidoJsonParser.parserJsonPedidos(response);
                    if(pedidosListener!=null)
                        pedidosListener.onRefreshListaPedidos(pedidos);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    String responseBody = null;
                    try {
                        responseBody = new String(error.networkResponse.data, "utf-8");
                        JSONObject data = new JSONObject(responseBody);

                        String message = data.getString("message");

                        Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();

                    } catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            volleyQueue.add(req);

        }
    }

    public void adicionarPedidoAPI(final String ip,final String token, final Pedido pedido, final Context context) {
        if (!Generic.isConnectionInternet(context)) {

            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();

        } else {
            StringRequest req = new StringRequest(Request.Method.POST, BaseUrl + ip + (pedido.getTipo()==0 ? mUrlAPIPedidos + "/restaurante" : mUrlAPIPedidos + "/takeaway") + "?access-token=" + token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    boolean save= PedidoJsonParser.parserJsonRegistar(response);

                    if(save==true){
                        if (pedidosListener != null)
                            switch (pedido.getTipo()){
                                case 0:
                                    pedidosListener.onCriarPedidoRestaurante();
                                    break;
                                case 1:
                                    pedidosListener.onCriarPedidoTakeaway();
                                    break;
                            }
                    }else{
                        if(pedido.getTipo()==0)
                            Toast.makeText(context, "Mesa encontra-se indisponivel", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("data", pedido.getData());
                    params.put("id_perfil", pedido.getId_utilizador() + "");
                    if(pedido.getTipo()==0){
                        params.put("id_mesa", pedido.getId_mesa() + "");
                    }else{
                        params.put("nome_pedido", pedido.getNome_pedido());
                    }
                    params.put("estado", pedido.getEstado() + "");
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void adicionarItemsCarrinhoAPI(final String ip,final String token, final Carrinho item, final int quantItems, final Context context) {

        if (!Generic.isConnectionInternet(context)) {

            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();

        } else {

                StringRequest req = new StringRequest(Request.Method.POST, BaseUrl + ip + mUrlAPIPedidosProduto + "/takeaway"+ "?access-token=" + token, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Empty
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {

                        Map<String,String> params=new HashMap<>();
                        params.put("id_produto",item.getId_produto()+"");
                        params.put("quant",item.getQuantidade()+"");
                        params.put("preco",item.getPreco()+"");
                        params.put("items",quantItems+"");

                        Log.i("##>",""+params);

                        return params;
                    }
                };
                volleyQueue.add(req);

        }
    }

    public void removerPedidoAPI(final String ip, final String token, final Pedido pedido,final Context context) {

        if (!Generic.isConnectionInternet(context)) {
            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();
        } else {

            StringRequest req = new StringRequest(Request.Method.DELETE, BaseUrl + ip + mUrlAPIPedidos + "/" + pedido.getId() + "?access-token=" + token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (pedidosProdutoListener != null){
                        pedidosProdutoListener.onDetalhes();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            volleyQueue.add(req);

        }

    }

    public void getPedidosProdutoAPI(final String ip,final String token, final Context context, final int id_pedido) {
        if (!Generic.isConnectionInternet(context)) {
            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();

        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, BaseUrl + ip + mUrlAPIPedidosProduto + "/" + id_pedido + "?access-token=" + token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    pedidoProdutos = PedidosProdutoJsonParser.parserJsonPedidosProduto(response);

                    if (pedidosProdutoListener != null){
                        pedidosProdutoListener.onRefreshListaPedidosProduto(pedidoProdutos);

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            volleyQueue.add(req);

        }
    }

    public void adicionarPedidoProdutoAPI(final String ip , final String token ,final PedidoProduto pedidoProduto, final Context context) {

        if (!Generic.isConnectionInternet(context)) {
            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();

        } else {

            StringRequest req = new StringRequest(Request.Method.POST, BaseUrl + ip + mUrlAPIPedidosProduto + "/restaurante" + "?access-token=" + token,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    boolean save=PedidosProdutoJsonParser.parserJsonAddRestaurante(response);

                    if(save==true){
                        if (pedidosProdutoListener != null)
                            pedidosProdutoListener.onCriar();
                    }else{
                        Toast.makeText(context, "Impossivel adicionar Produto ao pedido", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String,String> params=new HashMap<>();
                    params.put("id_pedido",pedidoProduto.getId_pedido()+"");
                    params.put("id_produto",pedidoProduto.getId_produto()+"");
                    params.put("quant_Pedida",pedidoProduto.getQuantidade()+"");
                    params.put("preco",pedidoProduto.getPreco()+"");
                    return params;
                }
            };

            volleyQueue.add(req);

        }

    }

    public void updatePedidoProdutoAPI(final String ip, final String token,final PedidoProduto pedidoProduto, final Context context) {

        if (!Generic.isConnectionInternet(context)) {

            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();

        } else {
            StringRequest req = new StringRequest(Request.Method.PUT, BaseUrl + ip + mUrlAPIPedidosProduto + "/" + pedidoProduto.getId() + "?access-token=" + token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //TODO: A ver isso
                    if (pedidosProdutoListener != null) {
                        pedidosProdutoListener.onDetalhes();
                    } else {
                        Toast.makeText(context, "Impossivel atualizar", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("quant_Pedida",pedidoProduto.getQuantidade()+"");
                    params.put("preco",pedidoProduto.getPreco()+"");

                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void removerPedidoProdutoAPI(final String ip, final String token, final PedidoProduto pedidoProduto,final Context context) {

        if (!Generic.isConnectionInternet(context)) {
            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();
        } else {

            StringRequest req = new StringRequest(Request.Method.DELETE, BaseUrl + ip + mUrlAPIPedidosProduto + "/" + pedidoProduto.getId() + "?access-token=" + token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (pedidosProdutoListener != null){
                        pedidosProdutoListener.onDetalhes();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            volleyQueue.add(req);

        }

    }
}
