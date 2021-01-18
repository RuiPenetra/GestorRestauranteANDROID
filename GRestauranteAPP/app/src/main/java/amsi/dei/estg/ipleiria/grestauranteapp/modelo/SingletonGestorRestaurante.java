package amsi.dei.estg.ipleiria.grestauranteapp.modelo;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import amsi.dei.estg.ipleiria.grestauranteapp.listeners.LoginListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PedidosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PedidosProdutoListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PerfilListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.ProdutosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.RegistoListener;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.AutenticacaoJsonParser;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.PedidoJsonParser;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.PedidosProdutoJsonParser;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.PerfilJsonParser;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.ProdutoJsonParser;

public class SingletonGestorRestaurante {
    private static SingletonGestorRestaurante instance = null;
    private ArrayList<Produto> produtos;
    private ArrayList<Pedido> pedidos;
    private ArrayList<PedidoProduto> pedidoProdutos;
    private Perfil perfil;
    private ProdutoBDHelper produtosBD;
    private static RequestQueue volleyQueue = null;
    private static final String BaseUrl = "http://";
    private static final String mUrlAPIAuth = "/GestorRestauranteAPI/API/web/v1/auth";
    private static final String mUrlAPIProdutos = "/GestorRestauranteAPI/API/web/v1/produto";
    private static final String mUrlAPIPedidos = "/GestorRestauranteAPI/API/web/v1/pedido";
    private static final String mUrlAPIPedidosProduto = "/GestorRestauranteAPI/API/web/v1/pedidoproduto";
    private static final String mUrlAPIPerfil = "/GestorRestauranteAPI/API/web/v1/perfil";
    private ProdutosListener produtosListener;
    private LoginListener loginListener;
    private PerfilListener perfilListener;
    private PedidosListener pedidosListener;
    private RegistoListener registoListener;
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
        pedidoProdutos = new ArrayList<>();
        produtosBD = new ProdutoBDHelper(context);
    }

    public Produto getProduto(int id){
        for (Produto p : produtos){
            if(p.getId() == id)
                return p;
        }
        return null;
    }

    // # PEDIDOS

    public Pedido getPedido(int id) {
        for (Pedido p : pedidos)
            if (p.getId() == id)
                return p;
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

    /************** Listeners ******************************/

    public void setPedidosListener(PedidosListener pedidosListener) {
        this.pedidosListener = pedidosListener;
    }

    public void setProdutosListener(ProdutosListener produtosListener) {
        this.produtosListener = produtosListener;
    }

    public void setLoginListener(LoginListener loginListener) { this.loginListener = loginListener; }

    public void setPerfilListener(PerfilListener perfilListener){
        this.perfilListener = perfilListener;
    }

    public void setPedidoProdutosListener(PedidosProdutoListener pedidoProdutosListener){
        this.pedidosProdutoListener = pedidoProdutosListener;
    }

    public void setRegistoListener(RegistoListener registoListener) {
        this.registoListener = registoListener;

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


    /************** Métodos de acesso à API ******************************/

    public void loginAPI(final String ip, final String username,final String password,final Context context) {

        if (!Generic.isConnectionInternet(context)) {

            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();

        } else {
            StringRequest req = new StringRequest(Request.Method.POST, BaseUrl + ip + mUrlAPIAuth + "/login", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Perfil perfil = AutenticacaoJsonParser.parserJsonLogin(response);

                    if (loginListener != null)
                        loginListener.onValidateLogin(perfil);
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
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    //#--->Produtos
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

            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, BaseUrl + ip + (id_categoria==0 ? mUrlAPIProdutos : mUrlAPIProdutos + "/categoria/" + id_categoria), null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.i("#-->",""+response);

                    produtos = ProdutoJsonParser.parserJsonProdutos(response);
                    Log.i("#-->",""+produtos);

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

    //#--->Perfil
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
            StringRequest req = new StringRequest(Request.Method.PUT, BaseUrl + ip + mUrlAPIPerfil + "/" + perfil.getId() + "/atualizar?access-token=" + token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Perfil p = PerfilJsonParser.parserJsonPerfil(response);

                    if (perfilListener != null) {
                        perfilListener.onUpdatePerfil();
                    } else {
                        Toast.makeText(context, "Impossivel atualizar", Toast.LENGTH_SHORT).show();
                        ;
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
            StringRequest req = new StringRequest(Request.Method.POST, BaseUrl + ip + mUrlAPIAuth + "/registar", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Perfil perfil = PerfilJsonParser.parserJsonPerfil(response);

                    if (registoListener != null)
                        registoListener.onRegistar(perfil);

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

    //#--->Pedidos
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
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            volleyQueue.add(req);

        }
    }

    public void adicionarPedidoAPI(final String ip,final String token, final Pedido pedido, final Context context) {
        if (!Generic.isConnectionInternet(context)) {

            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();

        } else {
            StringRequest req = new StringRequest(Request.Method.POST, BaseUrl + ip + mUrlAPIPedidos + "/criar?access-token=" + token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Pedido pedido = PedidoJsonParser.parserJsonPedido(response);

                    if (pedido != null) {
                        if (pedidosListener != null)
                            pedidosListener.onCreatePedido();
                    } else {
                        Toast.makeText(context, "Erro impossivel criar o pedido", Toast.LENGTH_SHORT).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "error.getMessage()", Toast.LENGTH_SHORT).show();
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
                    params.put("tipo", pedido.getTipo() + "");
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

            StringRequest req = new StringRequest(Request.Method.DELETE, BaseUrl + ip + mUrlAPIPedidos + "/" + pedido.getId() + "/apagar?access-token=" + token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    if (pedidosProdutoListener != null){
                        pedidosProdutoListener.onDetalhes();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "TESTE", Toast.LENGTH_SHORT).show();
                }
            });

            volleyQueue.add(req);

        }

    }

    //#--->Pedidos Produto
    public void getPedidosProdutoAPI(final String ip,final String token, final Context context, final int id_pedido) {
        if (!Generic.isConnectionInternet(context)) {
            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();

        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, BaseUrl + ip + mUrlAPIPedidosProduto + "/all/" + id_pedido + "?access-token=" + token, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    pedidoProdutos = PedidosProdutoJsonParser.parserJsonPedidosProduto(response);

                    if (pedidosProdutoListener != null){
                        pedidosListener.onCreatePedido();
                        pedidosProdutoListener.onRefreshListaPedidosProduto(pedidoProdutos);

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "TESTE", Toast.LENGTH_SHORT).show();
                }
            });

            volleyQueue.add(req);

        }
    }

    public void adicionarPedidoProdutoAPI(final String ip , final String token ,final PedidoProduto pedidoProduto, final Context context) {

        if (!Generic.isConnectionInternet(context)) {
            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();

        } else {

            StringRequest req = new StringRequest(Request.Method.POST, BaseUrl + ip + mUrlAPIPedidosProduto + "/criar?access-token=" + token,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    PedidoProduto pedidoProduto=PedidosProdutoJsonParser.parserJsonPedidoProduto(response);

                    //O pedido produto irá sempre ser diferente de null contudo caso não seja decidimos implementar esta condição
                    if(pedidoProduto!=null){
                        if(pedidosProdutoListener!=null)
                            pedidosProdutoListener.onCriar();

                    }else{
                        //Apartida nunca entra mas caso entre temos esta segurança
                        Toast.makeText(context, "Erro impossivel adicionar o produto", Toast.LENGTH_SHORT).show();
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
                    params.put("estado", pedidoProduto.getEstado()+"");
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
            StringRequest req = new StringRequest(Request.Method.PUT, BaseUrl + ip + mUrlAPIPedidosProduto + "/" + pedidoProduto.getId() + "/atualizar?access-token=" + token, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    PedidoProduto pedidoProduto = PedidosProdutoJsonParser.parserJsonPedidoProduto(response);

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

            StringRequest req = new StringRequest(Request.Method.DELETE, BaseUrl + ip + mUrlAPIPedidosProduto + "/" + pedidoProduto.getId() + "/apagar?access-token=" + token, new Response.Listener<String>() {
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
