package com.harpacrista;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.provider.SyncStateContract;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.harpacrista.app.App;
import com.harpacrista.entity.Hino;
import com.harpacrista.helper.DetalheHinoUiHelper;
import com.harpacrista.pojo.Cor;
import com.harpacrista.util.SessaoUtil;
import com.harpacrista.util.SpeakUtil;

import java.util.ArrayList;
import java.util.List;


public class DetalheHinoActivity extends ActionBarActivity implements View.OnTouchListener{

    private static final String SIZE_HINO = "size_hino";
    private static final String SIZE_AUTOR = "size_autor";
    private App app;
    private DetalheHinoUiHelper uiHelper;
    private float lastX;
    private float sizeHino;
    private float sizeAutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_hino);
        init();
    }

    private void init() {
        app = (App) getApplication();
        uiHelper = new DetalheHinoUiHelper(getWindow().getDecorView().findViewById(android.R.id.content));
        loadActionBarColor();
        setSupportActionBar(uiHelper.toolbar);
        uiHelper.hino.setOnTouchListener(this);
        uiHelper.voltar.setOnClickListener(configurarOnVoltarClickListener());
        uiHelper.proximo.setOnClickListener(configurarOnProximoClickListener());
        uiHelper.aumentarFonte.setOnClickListener(configurarOnAumentarFonteClickListener());
        uiHelper.diminuirFonte.setOnClickListener(configurarOnDiminuirClickListener());
        uiHelper.hino.setOnTouchListener(this);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        verificarTitle();
        verificarPaginacao();
        carregarHinoAtual();
        carregarFonteHino();
        loadColor();
    }

    private void loadColor() {
        uiHelper.aumentarFonte.setTextColor(getResources().getColor(app.colorConfig.getColor()));
        uiHelper.diminuirFonte.setTextColor(getResources().getColor(app.colorConfig.getColor()));
        uiHelper.voltar.setTextColor(getResources().getColor(app.colorConfig.getColor()));
        uiHelper.proximo.setTextColor(getResources().getColor(app.colorConfig.getColor()));
    }

    private void loadActionBarColor(){
        uiHelper.toolbar.setBackgroundColor(getResources().getColor(app.colorConfig.getColor()));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(app.colorConfig.getColorDark()));
        }
    }




    private void carregarHinoAtual(){
        uiHelper.hino.setText(Html.fromHtml(app.selectedHino.getHino()));
        uiHelper.autor.setText("Autor: " + app.selectedHino.getAutor());
        uiHelper.scrollView.scrollTo(0,0);
        verificarPaginacao();
        verificarTitle();
        verificaBotaoVoltarEAvancar();
    }

    private void verificarTitle() {
        StringBuilder title = new StringBuilder();
        title.append(app.selectedHino.getId());
        title.append("- ");
        title.append(app.selectedHino.getNome());
        getSupportActionBar().setTitle(title.toString());
    }

    private void verificarPaginacao(){
        StringBuilder sb = new StringBuilder();
        sb.append(app.selectedHino.getId());
        sb.append(" de ");
        sb.append(app.copyHinos.size());
        uiHelper.paginacao.setText(sb.toString());
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                lastX = motionEvent.getX();
                break;
            }
            case MotionEvent.ACTION_UP: {
                float currentX = motionEvent.getX();
                if (lastX < currentX - 100){
                    voltar();
                }
                if (lastX > currentX + 100){
                    proximo();
                }
                break;
            }
        }
        return true;
    }

    private View.OnClickListener configurarOnProximoClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proximo();
            }
        };
    }

    private View.OnClickListener configurarOnVoltarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltar();
            }
        };
    }

    private View.OnClickListener configurarOnAumentarFonteClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sizeHino = uiHelper.hino.getTextSize() + 4;
                sizeAutor = uiHelper.autor.getTextSize() + 4;
                uiHelper.hino.setTextSize(TypedValue.COMPLEX_UNIT_PX,sizeHino);
                uiHelper.autor.setTextSize(TypedValue.COMPLEX_UNIT_PX,sizeAutor);
                saveFontSize();
            }
        };
    }

    private View.OnClickListener configurarOnDiminuirClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sizeHino = uiHelper.hino.getTextSize() - 4;
                sizeAutor = uiHelper.autor.getTextSize() - 4;
                uiHelper.hino.setTextSize(TypedValue.COMPLEX_UNIT_PX,sizeHino);
                uiHelper.autor.setTextSize(TypedValue.COMPLEX_UNIT_PX,sizeAutor);
                saveFontSize();
            }
        };
    }

    private void voltar() {
        if(app.copyHinos.indexOf(app.selectedHino) != 0){
            int indexAnterior = app.copyHinos.indexOf(app.selectedHino) - 1;
            app.selectedHino = app.copyHinos.get(indexAnterior);
            carregarHinoAtual();

        }
    }

    private void proximo() {
        if(app.copyHinos.indexOf(app.selectedHino) != app.copyHinos.size() - 1) {
            int indexProximo = app.copyHinos.indexOf(app.selectedHino) + 1;
            app.selectedHino = app.copyHinos.get(indexProximo);
            carregarHinoAtual();

        }
    }

    private void verificaBotaoVoltarEAvancar(){
        if(app.copyHinos.indexOf(app.selectedHino) == 0){
            uiHelper.voltar.setAlpha(0.3f);
        }else{
            uiHelper.voltar.setAlpha(1f);
        }
        if(app.copyHinos.indexOf(app.selectedHino) == app.copyHinos.size() - 1){
            uiHelper.proximo.setAlpha(0.3f);
        }else{
            uiHelper.proximo.setAlpha(1f);
        }
    }

    private void saveFontSize(){
        SessaoUtil.adicionarValores(this, SIZE_HINO, String.valueOf(sizeHino));
        SessaoUtil.adicionarValores(this, SIZE_AUTOR, String.valueOf(sizeAutor));
    }

    private void carregarFonteHino() {
        String sSizeHino = SessaoUtil.recuperarValores(this, SIZE_HINO);
        String sSizeAutor = SessaoUtil.recuperarValores(this, SIZE_AUTOR);
        if(sSizeHino != null && sSizeAutor != null){
            sizeHino = Float.valueOf(sSizeHino);
            sizeAutor = Float.valueOf(sSizeAutor);
            uiHelper.hino.setTextSize(TypedValue.COMPLEX_UNIT_PX,sizeHino);
            uiHelper.autor.setTextSize(TypedValue.COMPLEX_UNIT_PX,sizeAutor);
        }
    }

    private void compartilhar() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, gerarHinoParaCompartilhar());
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getString(R.string.detalhe_hino_compartilhar_com)));
    }

    private String gerarHinoParaCompartilhar() {
        StringBuilder sb = new StringBuilder();
        sb.append(app.selectedHino.getId());
        sb.append("- ");
        sb.append(app.selectedHino.getNome());
        sb.append("\n\n");
        sb.append(Html.fromHtml(app.selectedHino.getHino()));
        sb.append("\n");
        sb.append("Autor: ");
        sb.append(app.selectedHino.getAutor());
        sb.append("\n");
        sb.append("via: Harpa Cristã");
        return sb.toString();
    }

    private void onClickFavoritar(MenuItem menuItem) {
        Hino item = app.selectedHino;
        item.setFavorito(!item.isFavorito());
        if(item.isFavorito()){
            app.exibirToast(R.string.hinos_favorito_com_sucesso);
            menuItem.setChecked(true);
        }else{
            app.exibirToast(R.string.hinos_removido_favorito_com_sucesso);
            menuItem.setChecked(false);
        }
        app.favoritos = new ArrayList<>();
        item.save();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detalhe_hino, menu);
        if(menu != null){
            menu.findItem(R.id.menu_detalhe_hino_favoritar).setChecked(app.selectedHino.isFavorito());
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_detalhe_hino_compartilhar:
                compartilhar();
                break;
            case R.id.menu_detalhe_hino_favoritar:
                onClickFavoritar(menuItem);
                break;

            case R.id.menu_detalhe_speak_now:
                SpeakUtil.promptSpeechInput(this);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
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
                            Integer number = Integer.parseInt(sNumber);
                            if(number > 0 && number <= 640){
                                app.selectedHino = app.copyHinos.get(number - 1);
                                carregarHinoAtual();

                            }else{
                                Toast.makeText(this, getString(R.string.speech_max_min), Toast.LENGTH_SHORT).show();
                            }
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