package amsi.dei.estg.ipleiria.grestauranteapp.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Carrinho;
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Pedido;

public class ListaItemsCarrinhoAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater infalter;
    private ArrayList<Carrinho> itemsCarrinho;

    public ListaItemsCarrinhoAdaptador(Context context, ArrayList<Carrinho> itemsCarrinho) {
        this.context = context;
        this.itemsCarrinho = itemsCarrinho;
    }

    @Override
    public int getCount() {
        return itemsCarrinho.size();
    }

    @Override
    public Object getItem(int position) {
        return itemsCarrinho.get(position);
    }

    @Override
    public long getItemId(int position) {
        return itemsCarrinho.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(infalter==null)
            infalter=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null)
            convertView= infalter.inflate(R.layout.item_lista_carrinho,null);

        ListaItemsCarrinhoAdaptador.ViewHolderLista viewHolder= (ListaItemsCarrinhoAdaptador.ViewHolderLista)convertView.getTag();

        if (viewHolder==null){
            viewHolder=new ListaItemsCarrinhoAdaptador.ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.update(itemsCarrinho.get(position));

        return convertView;
    }

    private class ViewHolderLista{
        private TextView tvNomeProduto,tvQuantidade,tvPreco;
        private ImageView imgvCategoria;

        public  ViewHolderLista(View view){
            tvNomeProduto=view.findViewById(R.id.itemNomeProduto);
            tvQuantidade=view.findViewById(R.id.itemQuantidade);
            tvPreco=view.findViewById(R.id.itemPreco);
            imgvCategoria=view.findViewById(R.id.itemCategoria);
        }

        public void update(Carrinho itemCarrinho){
            tvNomeProduto.setText(itemCarrinho.getNome_produto());
            tvQuantidade.setText("Qtn:"+itemCarrinho.getQuantidade());
            tvPreco.setText(itemCarrinho.getPreco()+" €");

            switch (itemCarrinho.getId_categoria()){
                case 1:
                    imgvCategoria.setImageResource(R.drawable.aperitivo);
                    break;
                case 2:
                    imgvCategoria.setImageResource(R.drawable.sopa);
                    break;
                case 3:
                    imgvCategoria.setImageResource(R.drawable.bife);
                    break;
                case 4:
                    imgvCategoria.setImageResource(R.drawable.peixe);
                    break;
                case 5:
                    imgvCategoria.setImageResource(R.drawable.sobremesa);
                    break;
                case 6:
                    imgvCategoria.setImageResource(R.drawable.bebidas);
                    break;

                default:
                    imgvCategoria.setImageResource(R.drawable.food);
                    break;
            }
        }
    }

}
