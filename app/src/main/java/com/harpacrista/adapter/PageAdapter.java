package com.harpacrista.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.harpacrista.app.App;
import com.harpacrista.fragment.ConfigFragment;
import com.harpacrista.fragment.FavoritosFragment;
import com.harpacrista.fragment.HinoFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Diogo Silva on 03/04/2016.
 */
public class PageAdapter extends FragmentStatePagerAdapter {

    private static final int TOTAL_TAB = 3;
    private final Context context;
    private final App app;
    public Map<Integer, Fragment> mapFragment;


    public PageAdapter (Context context, FragmentManager fm, App app) {
        super(fm);
        this.context = context;
        this.app = app;
        mapFragment = new HashMap<>();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case HinoFragment.POSITION:
                fragment = Fragment.instantiate(context, HinoFragment.class.getName());
                app.hinosFragment = (HinoFragment) fragment;
                break;
            case FavoritosFragment.POSITION:
                fragment = Fragment.instantiate(context, FavoritosFragment.class.getName());
                app.favoritosFragment = (FavoritosFragment) fragment;
                break;
            case ConfigFragment.POSITION:
                fragment = Fragment.instantiate(context, ConfigFragment.class.getName());
                break;
        }
        mapFragment.put(position, fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return TOTAL_TAB;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case HinoFragment.POSITION:
                return HinoFragment.NAME_TAB.toUpperCase();
            case FavoritosFragment.POSITION:
                return FavoritosFragment.NAME_TAB.toUpperCase();
            case ConfigFragment.POSITION:
                return ConfigFragment.NAME_TAB.toUpperCase();
        }
        return null;
    }

}
