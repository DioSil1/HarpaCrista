package com.harpacrista.callback;

/**
 * Created by Diogo Silva on 03/04/2016.
 */
public interface Callback {
    public void onReturn(Exception e, Object... args);
}