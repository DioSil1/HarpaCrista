package com.harpacrista;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.harpacrista.adapter.PageAdapter;
import com.harpacrista.app.App;
import com.harpacrista.util.SpeakUtil;
import com.harpacrista.view.FloatingActionButton;
import com.harpacrista.view.FloatingActionMenu;
import com.harpacrista.view.SlidingTabLayout;

import java.util.List;

public class MainActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    public App app;
    public SlidingTabLayout tabLayout;
    public FloatingActionMenu menu;
    public FloatingActionButton fab1,fab2,fab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        tabLayout.setDistributeEvenly(true);
        tabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });
        tabLayout.setViewPager(viewPager);
    }

    private void init() {
        app = (App) getApplication();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        menu = (FloatingActionMenu) findViewById(R.id.menu1);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        buttons();
        updateFragments();
        loadActionBarColor();

    }

    private void loadActionBarColor(){
        toolbar.setBackgroundColor(getResources().getColor(app.colorConfig.getColor()));
        tabLayout.setBackgroundColor(getResources().getColor(app.colorConfig.getColor()));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
           getWindow().setStatusBarColor(getResources().getColor(app.colorConfig.getColorDark()));
        }
        menu.setMenuButtonColorNormal(getResources().getColor(app.colorConfig.getColor()));
        menu.setMenuButtonColorPressed(getResources().getColor(app.colorConfig.getColorDark()));
    }

    private void updateFragments(){
        try {
            viewPager.setAdapter(new PageAdapter(this, getSupportFragmentManager(), app));
        }catch (IllegalStateException e){}
    }

    private void buttons(){
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = null;
                it = new Intent(Intent.ACTION_VIEW);
                it.setData(Uri.parse("https://plus.google.com/u/0/117469097355620160778"));
                startActivity(it);

            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = null;
                it = new Intent(Intent.ACTION_VIEW);
                it.setData(Uri.parse("https://twitter.com/diogosilva9898"));
                startActivity(it);

            }
        });

        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = null;
                it = new Intent(Intent.ACTION_VIEW);
                it.setData(Uri.parse("https://facebook.com/diogosilvam"));
                startActivity(it);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_sobre:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage(R.string.alert_sobre);
                dialog.setTitle(R.string.configuracoes_sobre);
                dialog.show();
                break;

            case R.id.action_mic:
                SpeakUtil.promptSpeechInput(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SpeakUtil.REQ_CODE_SPEECH_INPUT: {
                if (resultCode == Activity.RESULT_OK && null != data) {
                    List<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if(result != null && !result.isEmpty()){
                        String sNumber = result.get(0).replaceAll("[^0-9]", "");
                        if(sNumber != null && !sNumber.isEmpty()){
                            app.startDetailFromSpeaker(this, sNumber);
                        }else{
                            Toast.makeText(this, getString(R.string.speech_max_min), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            }
        }
    }

}
