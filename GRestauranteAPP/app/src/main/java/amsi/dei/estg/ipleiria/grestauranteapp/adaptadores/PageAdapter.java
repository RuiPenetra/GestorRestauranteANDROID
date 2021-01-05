package amsi.dei.estg.ipleiria.grestauranteapp.adaptadores;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import amsi.dei.estg.ipleiria.grestauranteapp.vistas.TabListaPedidosAtivos;
import amsi.dei.estg.ipleiria.grestauranteapp.vistas.TabListaPedidosConcluidos;

public class PageAdapter extends FragmentPagerAdapter {

    private int numTabs;

    public PageAdapter( FragmentManager fm, int numTabs) {
        super(fm);
        this.numTabs=numTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0:
                return new TabListaPedidosAtivos();
            case 1:
                return new TabListaPedidosConcluidos();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_UNCHANGED;
    }
}
