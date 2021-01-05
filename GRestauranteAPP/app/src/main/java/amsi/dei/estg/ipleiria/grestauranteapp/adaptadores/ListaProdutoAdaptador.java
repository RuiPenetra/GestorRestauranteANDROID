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
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Produto;

public class ListaProdutoAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater infalter;
    private ArrayList<Produto> produtos;

    public ListaProdutoAdaptador(Context context, ArrayList<Produto> produtos) {
        this.context = context;
        this.produtos = produtos;
    }

    @Override
    public int getCount() {
        return produtos.size();
    }

    @Override
    public Object getItem(int position) {
        return produtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return produtos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(infalter==null)
            infalter=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null)
            convertView= infalter.inflate(R.layout.item_lista_produtos,null);

        ViewHolderLista viewHolder= (ViewHolderLista)convertView.getTag();

        if (viewHolder==null){
            viewHolder=new ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.update(produtos.get(position));

        return convertView;
    }


    private class ViewHolderLista{
        private TextView tvNome, tvCategoria, tvPreco;
        private ImageView imgCapa;

        public  ViewHolderLista(View view){
            tvNome=view.findViewById(R.id.tvNome);
            tvCategoria=view.findViewById(R.id.tvCategoria);
            tvPreco=view.findViewById(R.id.tvPreco);
            imgCapa=view.findViewById(R.id.imgCapa);
        }

        public void update(Produto produto){
            tvNome.setText(produto.getNome());
            tvPreco.setText(String.valueOf(produto.getPreco()));

            switch (produto.getCategoria()){
                case 1:
                    tvCategoria.setText("Entrada");
                    imgCapa.setImageResource(R.drawable.entradas);
                    break;
                case 2:
                    tvCategoria.setText("Sopa");
                    imgCapa.setImageResource(R.drawable.sopa);
                    break;
                case 3:
                    tvCategoria.setText("Carne");
                    imgCapa.setImageResource(R.drawable.bife);
                    break;
                case 4:
                    tvCategoria.setText("Peixe");
                    imgCapa.setImageResource(R.drawable.peixe);
                    break;
                case 5:
                    tvCategoria.setText("Sobremesa");
                    imgCapa.setImageResource(R.drawable.sobremesa);
                    break;
                case 6:
                    tvCategoria.setText("Bebida");
                    imgCapa.setImageResource(R.drawable.bebidas);
                    break;

                default:
                    tvCategoria.setText("Outros");
                    imgCapa.setImageResource(R.drawable.food);
                    break;
            }
        }
    }
}
