package com.harpacrista.helper;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.harpacrista.R;

/**
 * Created by Diogo Silva on 03/04/2016.
 */
public class ConfiguracoesUiHelper {

    public TextView cores;
    public LinearLayout coresMain;
    public TextView mais;
    public LinearLayout maisMain;
    public TextView sobre;
    public LinearLayout sobreMain;

    public ConfiguracoesUiHelper(View view){
        this.cores = (TextView) view.findViewById(R.id.fragment_configuracoes_cores);
        this.coresMain = (LinearLayout) view.findViewById(R.id.fragment_configuracoes_cores_main);
        this.mais = (TextView) view.findViewById(R.id.fragment_configuracoes_mais);
        this.maisMain = (LinearLayout) view.findViewById(R.id.fragment_configuracoes_mais_main);
        this.sobre = (TextView) view.findViewById(R.id.fragment_configuracoes_sobre);
        this.sobreMain = (LinearLayout) view.findViewById(R.id.fragment_configuracoes_sobre_main);
    }
}