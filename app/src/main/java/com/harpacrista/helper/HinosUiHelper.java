package com.harpacrista.helper;

import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.harpacrista.R;
import com.harpacrista.view.GeneralSwipeRefreshLayout;

/**
 * Created by Diogo Silva on 03/04/2016.
 */
public class HinosUiHelper {

    public final View view;
    public ListView hinos;
    public SearchView search;
    public TextView mensagem;
    public GeneralSwipeRefreshLayout swipeLayout;
    public View progress;

    public HinosUiHelper(View view){
        this.view = view;
        init();
    }

    private void init() {
        this.hinos = (ListView) view.findViewById(R.id.hinos_list);
        this.search = (SearchView) view.findViewById(R.id.hinos_search);
        this.mensagem = (TextView) view.findViewById(R.id.hinos_mensagem);
        this.progress = view.findViewById(R.id.hinos_progress);
        this.swipeLayout = (GeneralSwipeRefreshLayout) view.findViewById(R.id.fragment_hinos_swipe);
        this.swipeLayout.setListView(hinos);
    }
}