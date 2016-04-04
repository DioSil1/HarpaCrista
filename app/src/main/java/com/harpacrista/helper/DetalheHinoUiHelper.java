package com.harpacrista.helper;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.harpacrista.R;

/**
 * Created by Diogo Silva on 03/04/2016.
 */
public class DetalheHinoUiHelper {

    public final View view;
    public Toolbar toolbar;
    public TextView paginacao;
    public TextView diminuirFonte;
    public TextView aumentarFonte;
    public TextView voltar;
    public TextView proximo;
    public TextView hino;
    public TextView autor;
    public ScrollView scrollView;

    public DetalheHinoUiHelper(View view){
        this.view = view;
        init();
    }

    private void init() {
        this.toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        this.paginacao = (TextView) view.findViewById(R.id.detalhe_hino_paginacao);
        this.diminuirFonte = (TextView) view.findViewById(R.id.detalhe_hino_diminuir_fonte);
        this.aumentarFonte = (TextView) view.findViewById(R.id.detalhe_hino_aumentar_fonte);
        this.voltar = (TextView) view.findViewById(R.id.detalhe_hino_anterior);
        this.proximo = (TextView) view.findViewById(R.id.detalhe_hino_proximo);
        this.hino = (TextView) view.findViewById(R.id.detalhe_hino_main);
        this.autor = (TextView) view.findViewById(R.id.detalhe_hino_autor);
        this.scrollView = (ScrollView) view.findViewById(R.id.detalhe_hino_scroll);
        this.voltar.setText("<");
        this.proximo.setText(">");
    }
}