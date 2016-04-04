package com.harpacrista.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.harpacrista.R;
import com.harpacrista.pojo.Cor;

/**
 * Created by Diogo Silva on 03/04/2016.
 */

public class SessaoUtil {

    private final static String PREFERENCES = "map";
    private final static String COLOR = "color";

    public static void adicionarValores(Context context, String chave, String valor) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, 0);
        settings.edit().putString(chave, valor).commit();
    }

    public static String recuperarValores(Context context, String chave) {
        return context.getSharedPreferences(PREFERENCES, 0).getString(chave, null);
    }


    public static void saveColor(Context context, int color){
        SharedPreferences settings = context.getSharedPreferences(PREFERENCES, 0);
        settings.edit().putInt(COLOR, color).commit();
    }
}