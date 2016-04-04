package com.harpacrista;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.harpacrista.app.App;
import com.harpacrista.util.NavegacaoUtil;

public class SplashScreenActivity extends Activity {

    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();
    }

    private void init() {
        app = (App) getApplication();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                NavegacaoUtil.navegar(SplashScreenActivity.this, MainActivity.class);
                finish();
            }
        }, 2000);
    }
}