package com.harpacrista.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harpacrista.MainActivity;
import com.harpacrista.R;
import com.harpacrista.app.App;
import com.harpacrista.helper.ConfiguracoesUiHelper;
import com.harpacrista.pojo.Cor;

import java.util.List;

/**
 * Created by Diogo Silva on 03/04/2016.
 */
public class ConfigFragment extends Fragment {

    public static final int POSITION = 2;
    public static final String NAME_TAB = "configurações";

    private View view;
    private ConfiguracoesUiHelper ui;
    private App app;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_config, container, false);
        init();
        loadValues();
        return view;
    }

    private void init() {
        app = (App) getActivity().getApplication();
        ui = new ConfiguracoesUiHelper(view);
        /*ui.maisMain.setOnClickListener(onMaisAplicativosClickListener());*/
        ui.sobreMain.setOnClickListener(onSobreClickListener());
        loadColor();

    }

    private void loadColor() {
        ui.cores.setTextColor(getResources().getColor(app.colorConfig.getColor()));
        ui.mais.setTextColor(getResources().getColor(app.colorConfig.getColor()));
        ui.sobre.setTextColor(getResources().getColor(app.colorConfig.getColor()));
    }

    private void loadValues() {
        List<Cor> cores = app.getCores();
        for (Cor cor : cores) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.new_item_cor, null);
            view.findViewById(R.id.item_cor).setBackgroundColor(getResources().getColor(cor.getColor()));
            view.setTag(cor);
            view.setOnClickListener(onCorClickListener());
            ui.coresMain.addView(view);
        }
    }

    private View.OnClickListener onCorClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                app.colorConfig = (Cor) view.getTag();
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    getActivity().getWindow().setStatusBarColor(getResources().getColor(app.colorConfig.getColorDark()));
                }
                MainActivity activity = (MainActivity) getActivity();
                int color = getResources().getColor(app.colorConfig.getColor());
                activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
                activity.tabLayout.setBackgroundColor(color);
                activity.menu.setMenuButtonColorNormal(color);
                activity.menu.setMenuButtonColorPressed(getResources().getColor(app.colorConfig.getColorDark()));
                loadColor();

            }
        };
    }

    private View.OnClickListener onSobreClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.configuracoes_sobre);
                builder.setMessage(R.string.alert_sobre);
                builder.create().show();
            }
        };
    }

   /* private View.OnClickListener onMaisAplicativosClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavegacaoUtil.redirect(getActivity(), Constants.MORE_APPS);
            }
        };
    }*/

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
