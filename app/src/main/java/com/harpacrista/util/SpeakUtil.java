package com.harpacrista.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import com.harpacrista.R;

import java.util.Locale;

/**
 * Created by Diogo Silva on 03/04/2016.
 */
public class SpeakUtil {

    public static final int REQ_CODE_SPEECH_INPUT = 0;

    public static void promptSpeechInput(Activity activity) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, activity.getString(R.string.speech_prompt));
        try {
            activity.startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(activity, activity.getString(R.string.speech_not_supported), Toast.LENGTH_SHORT).show();
        }
    }

}