package amsi.dei.estg.ipleiria.grestauranteapp.vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import amsi.dei.estg.ipleiria.grestauranteapp.R;
import amsi.dei.estg.ipleiria.grestauranteapp.adaptadores.PageAdapter;

public class PedidoFragment extends Fragment {

    private TabLayout tabPedidos;
    private ViewPager viewPager;
    private TabItem tabAtivos,tabConcluidos;
    private PageAdapter pagerAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pedidos,container,false);

        tabPedidos=view.findViewById(R.id.tabPedidos);
        tabAtivos=view.findViewById(R.id.tab_item_ativo);
        tabConcluidos=view.findViewById(R.id.tab_item_concluido);
        viewPager=view.findViewById(R.id.viewpager);

        pagerAdapter= new PageAdapter(getChildFragmentManager(),tabPedidos.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        tabPedidos.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0){
                    pagerAdapter.notifyDataSetChanged();
                }else{
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabPedidos));
        return view;
    }

}
