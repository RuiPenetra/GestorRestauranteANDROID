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
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PedidosAtivosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PedidosConcluidosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PedidosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.PerfilListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.ProdutosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.PedidoJsonParser;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.PerfilJsonParser;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.ProdutoJsonParser;
import amsi.dei.estg.ipleiria.grestauranteapp.vistas.LoginActivity;

public class SingletonGestorRestaurante {
    private static final int ADICIONAR_BD = 1;
    private static final int EDITAR_BD = 2;
    private static final int REMOVER_BD = 3;
    private static SingletonGestorRestaurante instance = null;
    private ArrayList<Produto> produtos;
    private ArrayList<Produto> auxProdutos;
    private ArrayList<Pedido> auxPedidosAtivos;
    private ArrayList<Pedido> auxPedidosConcluidos;
    private ArrayList<Pedido> pedidosAtivos;
    private ArrayList<Pedido> pedidosConcluidos;
    private ArrayList<Pedido> pedidos;
    private Perfil perfil;
    private ArrayList<PedidoProduto> pedidoProdutos;
    private ProdutoBDHelper produtosBD;
    private static RequestQueue volleyQueue = null;
    private static final String mUrlAPIProdutos = "http://192.168.0.105/GestorRestauranteAPI/API/web/v1/produto";
    private static final String mUrlAPILogin = "http://192.168.0.105/GestorRestauranteAPI/API/web/v1/auth/login";
    private static final String mUrlAPIPedidos = "http://192.168.0.105/GestorRestauranteAPI/API/web/v1/pedido?access-token=Y8DQTQWyZ2euhwRysit5OaVBs0ITBsdu";
    private static final String mUrlAPIPerfil = "http://192.168.0.105/GestorRestauranteAPI/API/web/v1/perfil?access-token=Y8DQTQWyZ2euhwRysit5OaVBs0ITBsdu";
    private static final String mUrlAPIupdatePerfil = "http://192.168.0.105/GestorRestauranteAPI/API/web/v1/perfil?access-token=Y8DQTQWyZ2euhwRysit5OaVBs0ITBsdu";
    private ProdutosListener produtosListener;
    private LoginListener loginListener;
    private PerfilListener perfilListener;
    private PedidosListener pedidosListener;
    private PedidosConcluidosListener pedidosConcluidosListener;
    private PedidosAtivosListener pedidosAtivosListener;


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
        pedidosConcluidos = new ArrayList<>();
        pedidosAtivos = new ArrayList<>();
        produtosBD = new ProdutoBDHelper(context);
    }

    public void loginAPI(final String username, final String password, final Context context) {
        StringRequest req = new StringRequest(Request.Method.POST, mUrlAPILogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String token = ProdutoJsonParser.parserJsonLogin(response);
                if (loginListener != null)
                    loginListener.onValidateLogin(token, username);
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

    public void setLoginListener(LoginListener loginListener) { this.loginListener = loginListener; }


    public Produto getProduto(int id){
        for (Produto p : produtos){
            if(p.getId() == id)
                return p;
        }
        return null;
    }

    public void setProdutosListener(ProdutosListener produtosListener) {
        this.produtosListener = produtosListener;
    }


    public void setPerfilListener(PerfilListener perfilListener) {
        this.perfilListener = perfilListener;
    }
    // # PEDIDOS

    public Pedido getPedido(int id) {
        for (Pedido p : pedidos)
            if (p.getId() == id)
                return p;
        return null;
    }
    public Perfil getPerfil() {
        return perfil;
    }

    //TODO: setPedidosListener()
    public void setPedidosListener(PedidosListener pedidosListener) {
        this.pedidosListener = pedidosListener;
    }

    public void setPedidosAtivosListener(PedidosAtivosListener pedidosAtivosListener) {
        this.pedidosAtivosListener = pedidosAtivosListener;
    }

    public void setPedidosConcluidosListener(PedidosConcluidosListener pedidosConcluidosListener) {
        this.pedidosConcluidosListener = pedidosConcluidosListener;
    }

    // # PEDIDO PRODUTO
    //TODO: setPedidoProdutosListener()


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

/*
    // # PRODUTOS
    public void getAllProdutosAPI(final Context context){
        if(!Generic.isConnectionInternet(context)){
            Toast.makeText(context, "Não existe ligação à internet",Toast.LENGTH_SHORT).show();

            if(produtosListener!=null)
                produtosListener.onRefreshListaPordutos(produtosBD.getAllProdutosBD());
        }else{
            JsonArrayRequest req= new JsonArrayRequest(Request.Method.GET, mUrlAPIProdutos, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    produtos = ProdutoJsonParser.parserJsonProdutos(response);
                    adicionarProdutosBD(produtos);

                    if(produtos!=null)
                        produtosListener.onRefreshListaPordutos(produtos);

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
*/
    public void getProdutosCategoriaAPI(final Context context, final int id_categoria) {
        if (!Generic.isConnectionInternet(context)) {
            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();

            if (produtosListener != null) {
                produtosListener.onRefreshListaPordutos(produtosBD.getProdutosCategoriaBD(id_categoria));
            }
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIProdutos, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    produtos = ProdutoJsonParser.parserJsonProdutos(response);
                    adicionarProdutosBD(produtos);

                    if (produtos != null)
                        auxProdutos = new ArrayList<Produto>();
                    for (Produto p : produtos) {
                        if (p.getCategoria() == id_categoria)
                            auxProdutos.add(p);

                    }
                    produtosListener.onRefreshListaPordutos(auxProdutos);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("key", error.getMessage());
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            volleyQueue.add(req);

        }
    }

    public void getPerfilAPI(final Context context) {
        if (!Generic.isConnectionInternet(context)) {
            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();

         /*   if(perfilListener!=null) {
                produtosListener.onRefreshListaPordutos(produtosBD.getProdutosCategoriaBD(id_categoria));
            }*/
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIPerfil,null,new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {

                    perfil = PerfilJsonParser.parserJsonPerfil(response);

//                    adicionarProdutosBD(produtos);

                        perfilListener.onRefreshPerfil(perfil);
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

    public void updatePerfilAPI(final Perfil perfil, final Context context) {
        StringRequest req = new StringRequest(Request.Method.PUT, mUrlAPIupdatePerfil + perfil.getId() + "/update?access-token=evZ5KAZTPTI29WWT62uDdUs5V0qGUhHL", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                PerfilJsonParser.parserJsonPerfilUpdate(response);

                if(perfilListener!=null){
                    perfilListener.onRefreshPerfilUpdate();
                }else{
                    Toast.makeText(context, "ERRRRROOOO", Toast.LENGTH_SHORT).show();;
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
                params.put("genero", perfil.getGenero());
                params.put("username", perfil.getUsername());
                params.put("email", perfil.getEmail());

                if(perfil.getNovaPassword()!="")
                    params.put("nova_password", perfil.getNovaPassword());

                return params;
            }
        };
        volleyQueue.add(req);
    }

    public void getPedidosAPI(final Context context) {
        if (!Generic.isConnectionInternet(context)) {
            Toast.makeText(context, "Não existe ligação à internet", Toast.LENGTH_SHORT).show();
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIPedidos, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    pedidos = PedidoJsonParser.parserJsonPedidos(response);

                    for (Pedido p: pedidos) {
                        if(p.getEstado()==2){
                            pedidosConcluidos.add(p);
                        }else{
                            pedidosAtivos.add(p);
                        }
                    }

                    pedidosAtivosListener.onRefreshPedidosAtivos(pedidosAtivos);
                    pedidosConcluidosListener.onRefreshPedidos(pedidosConcluidos);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("key", error.getMessage());
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            volleyQueue.add(req);

        }
    }
}

