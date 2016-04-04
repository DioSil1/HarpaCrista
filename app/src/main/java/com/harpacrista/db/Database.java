package com.harpacrista.db;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Created by Diogo Silva on 03/04/2016.
 */
public class Database extends SQLiteAssetHelper {

    public static final String DATABASE_SPEC = "harpa.spec";
    public static final String DATABASE_NAME = "harpa.db";
    public static final int DATABASE_VERSION = 1;


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}