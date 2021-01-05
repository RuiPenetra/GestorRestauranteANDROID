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
import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Produto;

public class ListaPedidoAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater infalter;
    private ArrayList<Pedido> pedidos;

    public ListaPedidoAdaptador(Context context, ArrayList<Pedido> pedidos) {
        this.context = context;
        this.pedidos = pedidos;
    }

    @Override
    public int getCount() {
        return pedidos.size();
    }

    @Override
    public Object getItem(int position) {
        return pedidos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pedidos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(infalter==null)
            infalter=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null)
            convertView= infalter.inflate(R.layout.item_lista_pedidos,null);

        ViewHolderLista viewHolder= (ViewHolderLista)convertView.getTag();

        if (viewHolder==null){
            viewHolder=new ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.update(pedidos.get(position));

        return convertView;
    }


    private class ViewHolderLista{
        private TextView tvMesa, tvData, tvEstado;
//        private ImageView imgCapa;

        public  ViewHolderLista(View view){
            tvMesa=view.findViewById(R.id.tvMesa);
            tvData=view.findViewById(R.id.tvData);
            tvEstado=view.findViewById(R.id.tvEstado);
//            imgCapa=view.findViewById(R.id.imgCapa);
        }

        public void update(Pedido pedido){
            tvMesa.setText("Nº "+pedido.getId_mesa());
            tvData.setText(pedido.getData());
            switch (pedido.getEstado()){
                case 0:
                    tvEstado.setText(" Em processo ");
                    tvEstado.setBackgroundResource(R.drawable.badge_processo);
                    break;
                case 1:
                    tvEstado.setText(" Em progresso ");
                    tvEstado.setBackgroundResource(R.drawable.badge_progresso);
                    break;
                case 2:
                    tvEstado.setText(" Concluido ");
                    tvEstado.setBackgroundResource(R.drawable.badge_concluido);
                    break;
            }
        }
    }
}
