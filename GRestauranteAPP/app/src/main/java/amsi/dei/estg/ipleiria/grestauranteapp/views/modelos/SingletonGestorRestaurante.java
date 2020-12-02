package amsi.dei.estg.ipleiria.grestauranteapp.views.modelos;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SingletonGestorRestaurante {
    private static SingletonGestorRestaurante instance = null;
    private ArrayList<Produto> produtos;
    private ArrayList<Pedido> pedidos;
    private ArrayList<PedidoProduto> pedidoProdutos;


    public static synchronized SingletonGestorRestaurante getInstance(){
        if(instance == null){
            instance = new SingletonGestorRestaurante();
        }
        return instance;
    }


    // # PRODUTOS
    public ArrayList<Produto> getProdutos(){
        return new ArrayList<>(produtos);
    }

    public Produto getProduto(int id){
        for(Produto p:produtos)
            if (p.getId()==id)
                return p;
            return null;
    }

    // # PEDIDOS
    public ArrayList<Pedido> getPedidos(){
        return new ArrayList<>(pedidos);
    }

    public Pedido getPedido(int id){
        for(Pedido p:pedidos)
            if (p.getId()==id)
                return p;
            return null;
    }

    // # PEDIDO PRODUTO


}
