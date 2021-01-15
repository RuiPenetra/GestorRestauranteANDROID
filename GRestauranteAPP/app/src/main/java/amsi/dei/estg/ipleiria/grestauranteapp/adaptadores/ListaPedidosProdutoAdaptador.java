package amsi.dei.estg.ipleiria.grestauranteapp.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Pedido;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.PedidoProduto;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Produto;

public class ListaPedidosProdutoAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater infalter;
    private ArrayList<PedidoProduto> pedidoProdutos;
    private ArrayList<Produto> produtos;

    public ListaPedidosProdutoAdaptador(Context context, ArrayList<PedidoProduto> pedidoProdutos, ArrayList<Produto> produtos) {
        this.context = context;
        this.pedidoProdutos = pedidoProdutos;
        this.produtos = produtos;
    }

    @Override
    public int getCount() {
        return pedidoProdutos.size();
    }

    @Override
    public Object getItem(int position) {
        return pedidoProdutos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pedidoProdutos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(infalter==null)
            infalter=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null)
            convertView= infalter.inflate(R.layout.item_lista_pedidosproduto,null);

        ViewHolderLista viewHolder= (ViewHolderLista)convertView.getTag();

        if (viewHolder==null){
            viewHolder=new ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.update(pedidoProdutos.get(position),produtos);

        return convertView;
    }


    public Produto getProduto(ArrayList<Produto> produtos,int id_produto){

        for (Produto p : produtos){
            if (p.getId() == id_produto)
                return p;
        }
        return null;
    }
    private class ViewHolderLista{
        private TextView tvNomeProduto, tvQuantidade, tvPreco,tvEstado;
        private ImageView imgCategoriaProduto;

        public  ViewHolderLista(View view){
            tvNomeProduto=view.findViewById(R.id.tvProdutoNome);
            tvQuantidade=view.findViewById(R.id.tvQuantidade);
            tvPreco=view.findViewById(R.id.tvPrecoTotal);
            tvEstado=view.findViewById(R.id.tvEstado);
            imgCategoriaProduto=view.findViewById(R.id.imgProduto);
        }

        public void update(PedidoProduto pedidoProduto,ArrayList<Produto> produtos){

            Produto auxProduto;

            auxProduto= getProduto(produtos,pedidoProduto.getId_produto());

            tvNomeProduto.setText(auxProduto.getNome());
            tvQuantidade.setText(String.valueOf(pedidoProduto.getQuantidade()));
            tvPreco.setText(String.valueOf(pedidoProduto.getPreco()));


            switch (pedidoProduto.getEstado()){
                case 0:
                    tvEstado.setText(" Em Processo ");
                    tvEstado.setBackgroundResource(R.drawable.badge_processo);
                    break;
                case 1:
                    tvEstado.setText(" Em Progresso ");
                    tvEstado.setBackgroundResource(R.drawable.badge_progresso);
                    break;
                case 2:
                    tvEstado.setText(" Concluido ");
                    tvEstado.setBackgroundResource(R.drawable.badge_concluido);
                    break;
            }

            switch (auxProduto.getCategoria()){
                case 1:
                    imgCategoriaProduto.setImageResource(R.drawable.entradas);
                    break;
                case 2:
                    imgCategoriaProduto.setImageResource(R.drawable.sopa);
                    break;
                case 3:
                    imgCategoriaProduto.setImageResource(R.drawable.bife);
                    break;
                case 4:
                    imgCategoriaProduto.setImageResource(R.drawable.peixe);
                    break;
                case 5:
                    imgCategoriaProduto.setImageResource(R.drawable.sobremesa);
                    break;
                case 6:
                    imgCategoriaProduto.setImageResource(R.drawable.bebidas);
                    break;
                default:
                    imgCategoriaProduto.setImageResource(R.drawable.food);
                    break;
            }
        }
    }
}
