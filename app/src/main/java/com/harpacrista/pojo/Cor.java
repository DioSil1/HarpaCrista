package com.harpacrista.pojo;

import com.harpacrista.app.App;

import java.util.Random;

/**
 * Created by Diogo Silva on 03/04/2016.
 */
public class Cor {

    private static final int TOTAL = 20;
    private int color;
    private int colorDark;

    public Cor(int color, int colorDark){
        this.color = color;
        this.colorDark = colorDark;

    }

    public static Cor getRandomColor(App app){
        return app.getCores().get(new Random().nextInt(TOTAL));
    }

    public int getColor() {
        return color;
    }

    public int getColorDark() {
        return colorDark;
    }
}