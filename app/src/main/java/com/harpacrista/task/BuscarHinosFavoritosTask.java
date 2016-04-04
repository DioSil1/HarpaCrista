package com.harpacrista.task;

import android.os.AsyncTask;

import com.harpacrista.callback.Callback;
import com.harpacrista.entity.Hino;

import java.util.List;

/**
 * Created by Diogo Silva on 03/04/2016.
 */
public class BuscarHinosFavoritosTask extends AsyncTask<Void, Void, List<Hino>> {

    private final Callback callback;

    public BuscarHinosFavoritosTask(Callback callback){
        this.callback = callback;
    }

    @Override
    protected List<Hino> doInBackground(Void... params) {
        return Hino.buscarHinosFavoritos();
    }

    @Override
    protected void onPostExecute(List<Hino> result) {
        super.onPostExecute(result);
        callback.onReturn(null, result);
    }
}