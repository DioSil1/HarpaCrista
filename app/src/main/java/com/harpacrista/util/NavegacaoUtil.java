package com.harpacrista.util;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Diogo Silva on 03/04/2016.
 */
public class NavegacaoUtil {

    public static void navegar(Activity activity, Class<?> contextoDestino){
        Intent i = new Intent(activity, contextoDestino);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(i);
    }

    public static void navegarComExtra(Activity activity, Class clazz, Map<String, Serializable> extras){
        Intent intent = new Intent(activity, clazz);
        for (Entry<String, Serializable> current : extras.entrySet()) {
            intent.putExtra(current.getKey(), current.getValue());
        }
        activity.startActivity(intent);
    }

    public static void navegarComResult(Activity activity, Class<?> contextoDestino, int requestCode){
        Intent i = new Intent(activity, contextoDestino);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivityForResult(i, requestCode);
    }

    public static void navegarComResultComExtra(Activity activity, Class<?> contextoDestino, int requestCode, Map<String, Serializable> extras ){
        Intent i = new Intent(activity, contextoDestino);
        for (Entry<String, Serializable> current : extras.entrySet()) {
            i.putExtra(current.getKey(), current.getValue());
        }
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivityForResult(i, requestCode);
    }

    public static  void redirect(Activity activity, String path) {
        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
        activity.startActivity(myIntent);
    }
}