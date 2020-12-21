package amsi.dei.estg.ipleiria.grestauranteapp.modelo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.listeners.LoginListener;
import amsi.dei.estg.ipleiria.grestauranteapp.listeners.ProdutosListener;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.Generic;
import amsi.dei.estg.ipleiria.grestauranteapp.utils.ProdutoJsonParser;

public class SingletonGestorRestaurante {
    private static final int ADICIONAR_BD = 1;
    private static final int EDITAR_BD =2 ;
    private static final int REMOVER_BD =3 ;
    private static SingletonGestorRestaurante instance = null;
    private ArrayList<Produto> produtos;
    private ArrayList<Produto> auxProdutos;
    private ArrayList<Pedido> pedidos;
    private ArrayList<PedidoProduto> pedidoProdutos;
    private ProdutoBDHelper produtosBD;
    private static RequestQueue volleyQueue = null;
    private static final String mUrlAPIProdutos= "http://192.168.1.160/GestorRestauranteAPI/API/web/v1/produto";
    private static final String mUrlAPILogin= "http://gestorrestaurante.epizy.com/API/web/v1/user";
    private ProdutosListener produtosListener;
    private LoginListener loginListener;

    public static synchronized SingletonGestorRestaurante getInstance(Context context){
        if(instance == null){
            instance = new SingletonGestorRestaurante(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private SingletonGestorRestaurante(Context context) {
        produtos = new ArrayList<>();
        produtosBD = new ProdutoBDHelper(context);
    }

    public Produto getProduto(int id){
        for (Produto p : produtos){
            if(p.getId() == id)
                return p;
        }
        return null;
    }

   public void setProdutosListener(ProdutosListener produtosListener){
        this.produtosListener = produtosListener;
   }

    public void setLoginListener(LoginListener loginListener){
        this.loginListener = loginListener;
    }

    // # PEDIDOS

    public Pedido getPedido(int id){
        for(Pedido p:pedidos)
            if (p.getId()==id)
                return p;
        return null;
    }

    //TODO: setPedidosListener()

    // # PEDIDO PRODUTO
    //TODO: getPedidoProdutoListener()
    //TODO: setPedidoProdutosListener()


    /************** Métodos de acesso à BD ******************************/
    // # PRODUTOS
    public ArrayList<Produto> getProdutosBD(){
        produtos =produtosBD.getAllProdutosBD();
        return produtos;
    }

    public void adicionarProdutoBD(Produto produto){
        Produto p= produtosBD.adicionarProdutoBD(produto);
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

    public void getProdutosCategoriaAPI(final Context context, final int id_categoria){
        if(!Generic.isConnectionInternet(context)){
            Toast.makeText(context, "Não existe ligação à internet",Toast.LENGTH_SHORT).show();

            if(produtosListener!=null) {
                produtosListener.onRefreshListaPordutos(produtosBD.getProdutosCategoriaBD(id_categoria));
            }
        }else{
            JsonArrayRequest req= new JsonArrayRequest(Request.Method.GET, mUrlAPIProdutos, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    produtos = ProdutoJsonParser.parserJsonProdutos(response);
                    adicionarProdutosBD(produtos);

                    if(produtos!=null)
                        auxProdutos=new ArrayList<Produto>();
                        for (Produto p:produtos) {
                            if(p.getCategoria()==id_categoria)
                                auxProdutos.add(p);

                        }
                        produtosListener.onRefreshListaPordutos(auxProdutos);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("key",error.getMessage());
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            volleyQueue.add(req);

        }
    }








}
