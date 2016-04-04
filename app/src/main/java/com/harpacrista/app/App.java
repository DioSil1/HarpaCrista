package com.harpacrista.app;

import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import com.codeslap.persistence.DatabaseSpec;
import com.codeslap.persistence.PersistenceConfig;
import com.harpacrista.DetalheHinoActivity;
import com.harpacrista.R;
import com.harpacrista.db.Database;
import com.harpacrista.entity.Hino;
import com.harpacrista.fragment.FavoritosFragment;
import com.harpacrista.fragment.HinoFragment;
import com.harpacrista.pojo.Cor;
import com.harpacrista.singleton.SingletonAdapter;
import com.harpacrista.util.NavegacaoUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Diogo Silva on 03/04/2016.
 */
public class App extends Application {

    public FavoritosFragment favoritosFragment;
    public HinoFragment hinosFragment;
    public List<Hino> hinos;
    public Hino selectedHino;
    public List<Hino> copyHinos;
    public List<Hino> favoritos;
    private List<AsyncTask<?, ?, ?>> tasks;
    public Cor coloDark;
    public Cor colorConfig;



    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        initDB();
        hinos = new ArrayList<>();
        copyHinos = new ArrayList<>();
        favoritos = new ArrayList<>();
        colorConfig = Cor.getRandomColor(this);
        tasks = new ArrayList<>();
    }


    public static List<Cor> getCores(){
        List<Cor> colors = new ArrayList<>();
        colors = new ArrayList<>();
        colors.add(new Cor(R.color.default_color, R.color.default_color_dark));
        colors.add(new Cor(R.color.red, R.color.red_dark));
        colors.add(new Cor(R.color.pink, R.color.pink_dark));
        colors.add(new Cor(R.color.purple, R.color.purple_dark));
        colors.add(new Cor(R.color.deep_purple, R.color.deep_purple_dark));
        colors.add(new Cor(R.color.indigo, R.color.indigo_dark));
        colors.add(new Cor(R.color.blue, R.color.blue_dark));
        colors.add(new Cor(R.color.light_blue, R.color.light_blue_dark));
        colors.add(new Cor(R.color.cyan, R.color.cyan_dark));
        colors.add(new Cor(R.color.teal, R.color.teal_dark));
        colors.add(new Cor(R.color.green, R.color.green_dark));
        colors.add(new Cor(R.color.light_green, R.color.light_green_dark));
        colors.add(new Cor(R.color.lime, R.color.lime_dark));
        colors.add(new Cor(R.color.yellow, R.color.yellow_dark));
        colors.add(new Cor(R.color.amber, R.color.amber_dark));
        colors.add(new Cor(R.color.orange, R.color.orange_dark));
        colors.add(new Cor(R.color.deep_orange, R.color.deep_orange_dark));
        colors.add(new Cor(R.color.brow, R.color.brow_dark));
        colors.add(new Cor(R.color.grey, R.color.grey_dark));
        colors.add(new Cor(R.color.blue_gray, R.color.blue_gray_dark));
        return colors;
    }



    private void initDB() {
        Database db = new Database(getApplicationContext());
        db.getWritableDatabase();
        DatabaseSpec database = PersistenceConfig.registerSpec(Database.DATABASE_SPEC, Database.DATABASE_VERSION);
        database.match(Hino.class);
        SingletonAdapter.getInstance(getApplicationContext());
    }

    public void registraTask(AsyncTask task) {
        tasks.add(task);
    }

    public void desregistraTask(AsyncTask task) {
        if (task != null) {
            tasks.remove(task);
        }
    }

    public void exibirToast(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        for (AsyncTask task : tasks) {
            desregistraTask(task);
        }
    }


    public void startDetailFromSpeaker(Activity activity, String sNumber) {
        Integer number = Integer.parseInt(sNumber);
        if(number > 0 && number <= 640){
            selectedHino = copyHinos.get(number - 1);
            NavegacaoUtil.navegar(activity, DetalheHinoActivity.class);
        }else{
            Toast.makeText(this, getString(R.string.speech_max_min), Toast.LENGTH_SHORT).show();
        }
    }

}
