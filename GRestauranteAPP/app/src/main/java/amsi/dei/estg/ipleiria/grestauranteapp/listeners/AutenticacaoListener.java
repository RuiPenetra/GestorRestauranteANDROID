package amsi.dei.estg.ipleiria.grestauranteapp.listeners;

import amsi.dei.estg.ipleiria.grestauranteapp.modelo.Perfil;

public interface AutenticacaoListener {
    void onValidarLogin(Perfil perfil);
    void onValidarRegisto();
}
