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
        private TextView tvTipo, tvData, tvEstado;
        private ImageView imgvTipoPedido,imgvTipo;

        public  ViewHolderLista(View view){
            tvTipo=view.findViewById(R.id.tvTipoPedido);
            tvData=view.findViewById(R.id.tvData);
            tvEstado=view.findViewById(R.id.tvEstado);
            imgvTipoPedido=view.findViewById(R.id.imgCapa);
            imgvTipo=view.findViewById(R.id.imgvTipoPedido);
        }

        public void update(Pedido pedido){
            tvData.setText(pedido.getData());

            if(pedido.getTipo()!=0){
                imgvTipo.setImageResource(R.drawable.ic_perfil);
                imgvTipoPedido.setImageResource(R.drawable.takeaway);
                tvTipo.setText(pedido.getNome_pedido());
            }else{
                imgvTipo.setImageResource(R.drawable.img_table);
                imgvTipoPedido.setImageResource(R.drawable.restaurante);
                tvTipo.setText("Nº "+pedido.getId_mesa());
            }

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
