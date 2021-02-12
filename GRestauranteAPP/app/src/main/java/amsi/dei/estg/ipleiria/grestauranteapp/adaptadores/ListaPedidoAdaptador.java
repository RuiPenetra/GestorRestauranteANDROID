package amsi.dei.estg.ipleiria.grestauranteapp.adaptadores;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

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
            if(pedidos.get(position).getTipo()==0){
                convertView= infalter.inflate(R.layout.item_lista_pedidos_restaurante,null);
            }else{
                convertView= infalter.inflate(R.layout.item_lista_pedidos_takeaway,null);
            }

        ViewHolderLista viewHolder= (ViewHolderLista)convertView.getTag();

        if (viewHolder==null){
            viewHolder=new ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.update(pedidos.get(position));

        return convertView;
    }


    private class ViewHolderLista{
        private TextView tv_rest_Mesa, tv_rest_Estado,tv_rest_n_pedido;
        private TextView tv_take_Nomepedido, tv_takeData, tv_takeEstado;
        private ImageView imgvRestPedido,imgvRestTipo;
        private ProgressBar pb_itemTakeaway,pb_itemRestaurante;

        public  ViewHolderLista(View view){
            tv_rest_Mesa=view.findViewById(R.id.tvRestMesa);
            tv_rest_Estado=view.findViewById(R.id.tvRestEstado);
            tv_rest_n_pedido=view.findViewById(R.id.tv_rest_n_pedido);
            imgvRestPedido=view.findViewById(R.id.imgRestPedido);
            pb_itemRestaurante=view.findViewById(R.id.pb_itemRestaurante);

            tv_takeData=view.findViewById(R.id.tvTakeData);
            tv_takeEstado=view.findViewById(R.id.tvTakeEstado);
            tv_take_Nomepedido=view.findViewById(R.id.tvTakeNomePedido);
            pb_itemTakeaway=view.findViewById(R.id.pb_itemTakeaway);

        }

        public void update(Pedido pedido){
            //tv_rest_Data.setText(pedido.getData());
            if(pedido.getTipo()!=1){
                tv_rest_n_pedido.setText("Pedido nº: " +pedido.getId());
                imgvRestPedido.setImageResource(R.drawable.restaurante);
                tv_rest_Mesa.setText("Nº "+pedido.getId_mesa());

                pb_itemRestaurante.setIndeterminate(false);
                switch (pedido.getEstado()){
                    case 1:
                        tv_rest_Estado.setText(" Pedido esta a ser processado..");
                        pb_itemRestaurante.setProgressDrawable(context.getDrawable(R.drawable.pb_processo));
                        pb_itemRestaurante.setProgress(30);
                        break;
                    case 2:
                        tv_rest_Estado.setText(" Pedido em preparação..");
                        pb_itemRestaurante.setProgressDrawable(context.getDrawable(R.drawable.pb_preparacao));
                        pb_itemRestaurante.setProgress(70);
                        break;
                    case 3:
                        tv_rest_Estado.setText(" Pedido concluido ");
                        pb_itemRestaurante.setProgressDrawable(context.getDrawable(R.drawable.pb_concluido));
                        pb_itemRestaurante.setProgress(100);
                        break;
                }
            }else{
                tv_take_Nomepedido.setText(pedido.getNome_pedido());
                tv_takeData.setText(pedido.getData());

                switch (pedido.getEstado()){
                    case 1:
                        tv_takeEstado.setText(" Pedido esta a ser processado..");
                        pb_itemTakeaway.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.processo), android.graphics.PorterDuff.Mode.SRC_IN);
                        break;
                    case 2:
                        tv_takeEstado.setText(" Pedido em preparação..");
                        pb_itemTakeaway.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.preparacao), android.graphics.PorterDuff.Mode.SRC_IN);
                        break;
                    case 3:
                        tv_takeEstado.setText(" Pedido concluido ");
                        pb_itemTakeaway.setIndeterminate(false);
                        pb_itemTakeaway.setProgress(100);
                        pb_itemTakeaway.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.concluido), android.graphics.PorterDuff.Mode.SRC_IN);

                        break;
                }
            }
        }
    }
}
