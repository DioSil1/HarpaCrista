package com.harpacrista.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SearchView;

import com.harpacrista.DetalheHinoActivity;
import com.harpacrista.R;
import com.harpacrista.adapter.HinoAdapter;
import com.harpacrista.app.App;
import com.harpacrista.callback.Callback;
import com.harpacrista.entity.Hino;
import com.harpacrista.enums.StatusEnum;
import com.harpacrista.helper.HinosUiHelper;
import com.harpacrista.task.BuscarHinosTask;
import com.harpacrista.util.NavegacaoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diogo Silva on 03/04/2016.
 */
public class HinoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    public static final int POSITION = 0;
    public static final String NAME_TAB = "hinos";

    private View view;
    private App app;
    private HinosUiHelper uiHelper;
    private HinoAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hino_fragment, container, false);
        init();
        verificarAtualizar();
        return view;
    }

    private void verificarAtualizar() {
        if(app.hinos.isEmpty()){
            verificaStatus(StatusEnum.INICIO);
        }else{
            setList(app.hinos);
        }
    }

    private void init() {
        app = (App) getActivity().getApplication();
        uiHelper = new HinosUiHelper(view);
        uiHelper.swipeLayout.setOnRefreshListener(this);
        uiHelper.swipeLayout.setColorSchemeResources(R.color.colorPrimary);
        uiHelper.search.setOnQueryTextListener(configurarOnQueryTextListener());
        uiHelper.hinos.setOnItemClickListener(configurarOnItemClickListener());

    }




    public void verificaStatus(StatusEnum status){
        if(status == StatusEnum.INICIO){
            verificaStatusInicio();
        }else if(status == StatusEnum.EXECUTANDO){
            verificaStatusExecutando();
        }else if(status == StatusEnum.EXECUTADO){
            verificaStatusExecutado();
        }
    }

    private void verificaStatusInicio() {
        BuscarHinosTask buscarHinosTask = new BuscarHinosTask(onBuscarHinosCallback());
        app.registraTask(buscarHinosTask);
        buscarHinosTask.execute();
        verificaStatus(StatusEnum.EXECUTANDO);
    }

    private Callback onBuscarHinosCallback() {
        return new Callback() {
            @Override
            public void onReturn(Exception error, Object... args) {
                if(isAdded()){
                    if(error == null){
                        List<Hino> result = (List<Hino>) args[0];
                        app.hinos = result;
                        app.copyHinos = new ArrayList<>(app.hinos);

                        setList(result);
                        verificaStatus(StatusEnum.EXECUTADO);
                    }else{
                        verificaStatus(StatusEnum.INICIO);
                    }
                }
            }
        };
    }

    private void verificaStatusExecutando() {
        if(app.hinos.isEmpty()){
            uiHelper.progress.setVisibility(View.VISIBLE);
        }else{
            uiHelper.swipeLayout.setRefreshing(true);
        }
    }

    private void verificaStatusExecutado() {
        uiHelper.swipeLayout.setRefreshing(false);
        uiHelper.progress.setVisibility(View.GONE);
    }

    private SearchView.OnQueryTextListener configurarOnQueryTextListener() {
        return new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(listAdapter != null){
                    listAdapter.getFilter().filter(newText);
                }
                return false;
            }
        };
    }

    private AdapterView.OnItemClickListener configurarOnItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                app.selectedHino = (Hino) adapterView.getItemAtPosition(position);
                NavegacaoUtil.navegar(getActivity(), DetalheHinoActivity.class);
            }
        };
    }

    private View.OnClickListener configurarOnFavoritoClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView image = (ImageView) view;
                Hino item = (Hino) image.getTag();
                item.setFavorito(!item.isFavorito());
                if(item.isFavorito()){
                    app.exibirToast(R.string.hinos_favorito_com_sucesso);
                    image.setImageResource(R.drawable.heart);
                }else{
                    app.exibirToast(R.string.hinos_removido_favorito_com_sucesso);
                    image.setImageResource(R.drawable.heart_outline);
                }
                item.save();
                if(app.favoritosFragment != null){
                    app.favoritosFragment.verificaStatus(StatusEnum.INICIO);
                }
            }
        };
    }

    private void setList(List<Hino> itens){
        listAdapter = new HinoAdapter(getActivity(), R.layout.new_item_hino, itens, configurarOnFavoritoClickListener());
        uiHelper.hinos.setAdapter(listAdapter);
    }

    @Override
    public void onRefresh() {
        verificaStatus(StatusEnum.INICIO);
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    getActivity().finish();
                    return true;
                }
                return false;
            }
        });
    }

}
